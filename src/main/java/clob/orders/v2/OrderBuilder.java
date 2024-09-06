package clob.orders.v2;

import clob.auth.AuthenticationCredentials;
import clob.orders.v2.types.OrderAmount;
import clob.orders.v2.types.SignedOrder;
import clob.orders.v2.types.args.OrderArgs;
import clob.orders.v2.types.config.RoundingConfiguration;
import clob.orders.v2.types.eip712.OrderStruct;
import clob.orders.v2.types.eip712.PolymarketCTFDomainStruct;
import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;
import cryptography.keys.Key;
import cryptography.utils.HexUtils;
import eth.EthUtils;
import eth.eip712.EIP712Signature;

public class OrderBuilder {
    public static SignedOrder createSignedOrder(OrderArgs args, AuthenticationCredentials credentials) throws Exception {
        OrderAmount amounts = OrderUtils.getOrderAmounts(
                args.side, args.size, args.price,
                RoundingConfiguration.fromTickSize(args.options.tickSize)
        );

        OrderStruct order = new OrderStruct(
                OrderUtils.randomSalt(),
                credentials.keys.publicKey,
                credentials.keys.publicKey,
                new Key(HexUtils.hexStringToByteArray(EthUtils.removeZX(args.taker))),
                args.token_id,
                amounts.makerAmount,
                amounts.takerAmount,
                args.expiration,
                args.nonce,
                args.fee_rate_bps,
                args.side,
                credentials.signatureType
        );

        // EIP-712 Signature
        byte[] header = EIP712Signature.HEADER;
        byte[] domain = args.options.negativeRisk ? PolymarketCTFDomainStruct.precomputedHashStructNegativeRisk() : PolymarketCTFDomainStruct.precomputedHashStruct();
        byte[] encoding = EthUtils.concat(header, domain, order.hashStruct()); // signable bytes
        byte[] digest = Keccak256.digest(encoding);

        // Sign
        long ctx = NativeSecp256k1.contextCreate();
        NativeSecp256k1.RecoverableSignature r_sig = NativeSecp256k1.signRecoverableSerialized(ctx, digest, credentials.keys.privateKey.key());

        // format ecdsa256k1 sig
        assert r_sig != null;
        byte[] b_sig = EthUtils.concat(r_sig.r, r_sig.s, r_sig.v);
        NativeSecp256k1.contextCleanup(ctx);
        String signature = EthUtils.addZX(HexUtils.bytesToHexString(b_sig));
        //String signature = HMAC_SHA256.buildHMACSignature(credentials.apiCredentials.secret, digest);

        return new SignedOrder(order, signature);
    }
}
