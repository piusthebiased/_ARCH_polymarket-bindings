package clob.headers;

import clob.eip712.ClobAuthDomainStruct;
import clob.eip712.ClobAuthStruct;
import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;
import cryptography.utils.HexUtils;
import eth.Address;
import eth.EthUtils;
import eth.eip712.EIP712Signature;

public class AuthenticationHeaders {
    public static HeaderSet createL1Headers(Address address) {
        // get signature
        byte[] header = EIP712Signature.HEADER;
        byte[] domain = ClobAuthDomainStruct.precomputedHashStruct();

        ClobAuthStruct auth = new ClobAuthStruct(address);
        byte[] encoding = EthUtils.concat(header, domain, auth.hashStruct());
        byte[] digest = Keccak256.digest(encoding);

        long ctx = NativeSecp256k1.contextCreate();
        NativeSecp256k1.RecoverableSignature r_sig = NativeSecp256k1.signRecoverableSerialized(ctx, digest, address.address());

        assert r_sig != null;
        byte[] signature = EthUtils.concat(r_sig.r, r_sig.s, r_sig.v);
        NativeSecp256k1.contextCleanup(ctx);

        String polygonAddress = "0x" + HexUtils.bytesToHexString(Address.ethAddressFromPrivateKey(address.address()).address());
        String polygonSignature = "0x" + HexUtils.bytesToHexString(signature).toLowerCase();

        HeaderSet L1 = new HeaderSet();
        L1.addHeader("POLY_ADDRESS", polygonAddress);
        L1.addHeader("POLY_SIGNATURE", polygonSignature);
        L1.addHeader("POLY_TIMESTAMP", auth.timestamp);
        L1.addHeader("POLY_NONCE", 0);

        return L1;
    }
}
