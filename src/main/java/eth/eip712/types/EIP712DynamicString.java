package eth.eip712.types;

import cryptography.hash.Keccak256;
import eth.EthUtils;

public class EIP712DynamicString extends EIP712Type<String> {
    public EIP712DynamicString(String value) {
        super("string", value);
    }

    @Override
    public byte[] _encodeValue(String value) {
        return Keccak256.digest(EthUtils.asciiBytesFromString(value));
    }
}
