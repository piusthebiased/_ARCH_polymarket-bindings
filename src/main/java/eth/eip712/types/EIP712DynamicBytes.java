package eth.eip712.types;

import cryptography.hash.Keccak256;

public class EIP712DynamicBytes extends EIP712Type<byte[]>{

    public EIP712DynamicBytes(byte[] value) {
        super("bytes", value);
    }

    @Override
    public byte[] _encodeValue(byte[] bytes) {
        return Keccak256.digest(bytes);
    }
}
