package clob.headers;

import clob.auth.APIKeyCredentials;
import clob.auth.AuthenticationCredentials;
import clob.auth.eip712.ClobAuthDomainStruct;
import clob.auth.eip712.ClobAuthStruct;
import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;
import cryptography.keys.KeyPair;
import cryptography.sign.HMAC_SHA256;
import cryptography.utils.HexUtils;
import eth.EthUtils;
import eth.eip712.EIP712Signature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AuthenticationHeaders {
    public static HeaderSet createL1Headers(KeyPair pair) {
        // get EIP-712 header and domain
        byte[] header = EIP712Signature.HEADER;
        byte[] domain = ClobAuthDomainStruct.precomputedHashStruct();

        // encode EIP-712 signature
        ClobAuthStruct auth = new ClobAuthStruct(pair.publicKey);
        byte[] encoding = EthUtils.concat(header, domain, auth.hashStruct());
        byte[] digest = Keccak256.digest(encoding);

        // sign byte[] digest
        long ctx = NativeSecp256k1.contextCreate();
        NativeSecp256k1.RecoverableSignature r_sig = NativeSecp256k1.signRecoverableSerialized(ctx, digest, pair.privateKey.key());

        // format ecdsa256k1 sig
        assert r_sig != null;
        byte[] signature = EthUtils.concat(r_sig.r, r_sig.s, r_sig.v);
        NativeSecp256k1.contextCleanup(ctx);

        String polygonAddress = "0x" + HexUtils.bytesToHexString(pair.publicKey.key());
        String polygonSignature = "0x" + HexUtils.bytesToHexString(signature).toLowerCase();

        HeaderSet L1 = new HeaderSet();
        L1.addHeader("POLY_ADDRESS", polygonAddress);
        L1.addHeader("POLY_SIGNATURE", polygonSignature);
        L1.addHeader("POLY_TIMESTAMP", auth.timestamp);
        L1.addHeader("POLY_NONCE", 0);

        return L1;
    }

    public static HeaderSet createL2Headers(AuthenticationCredentials credentials, RequestArgs args) throws NoSuchAlgorithmException, InvalidKeyException {
        String timestamp = String.valueOf(System.currentTimeMillis()/1000);
        String message = timestamp + args.method + args.requestPath;
        if(args.body != null) message += args.body.replace("'", "\"");

        // unbox struct
        KeyPair keys = credentials.keys;
        APIKeyCredentials apiCredentials = credentials.apiCredentials;

        HeaderSet L2 = new HeaderSet();
        L2.addHeader("POLY_ADDRESS", "0x" + HexUtils.bytesToHexString(keys.publicKey.key()));
        L2.addHeader("POLY_SIGNATURE", HMAC_SHA256.buildHMACSignature(apiCredentials.secret, message));
        L2.addHeader("POLY_TIMESTAMP", timestamp);
        L2.addHeader("POLY_API_KEY", apiCredentials.apiKey);
        L2.addHeader("POLY_PASSPHRASE", apiCredentials.passphrase);

        return L2;
    }
}
