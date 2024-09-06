package eth.eip712.types;

import com.sun.jdi.InvalidTypeException;
import eth.EthUtils;

public class EIP712AtomicBytes extends EIP712Type<byte[]>{

    public EIP712AtomicBytes(byte[] value) throws InvalidTypeException {
        super("bytes"+value.length, value);
        if(value.length > 32) throw new InvalidTypeException("The EIP-712 atomic type for bytes fall from bytes1 to bytes32");
    }

    @Override
    public byte[] _encodeValue(byte[] bytes) {
        return EthUtils.concat(bytes, new byte[32-bytes.length]);
    }
}
