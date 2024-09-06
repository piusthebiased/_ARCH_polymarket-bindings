package eth.eip712.types;

public class EIP712AtomicInt32 extends EIP712Type<Integer> {
    public EIP712AtomicInt32(Integer value) {
        super("int32", value);
    }

    @Override
    public byte[] _encodeValue(Integer i) {
        byte[] b32 = new byte[32];
        if(i < 0) b32[0] = (byte) 128;

        b32[28] = (byte) ((i & 0xFF000000) >> 24);
        b32[29] = (byte) ((i & 0x00FF0000) >> 16);
        b32[30] = (byte) ((i & 0x0000FF00) >> 8);
        b32[31] = (byte) ((i & 0x000000FF));

        return b32;
    }
}
