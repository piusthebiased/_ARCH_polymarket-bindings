package eth.eip712.types;

public class EIP712AtomicBoolean extends EIP712Type<Boolean>{
    // given 20 byte array.
    public EIP712AtomicBoolean(Boolean value) {
        super("Boolean", value); // 160bits / (1 bit / 8 byte)
    }

    @Override
    public byte[] _encodeValue(Boolean bool) {
        byte[] b32 = new byte[32];
        if(bool.equals(true)) b32[31] = 0x01;
        return b32;
    }
}
