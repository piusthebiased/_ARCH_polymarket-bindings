package eth.eip712.types;

import cryptography.utils.HexUtils;

public class EIP712AtomicUInt256 extends EIP712Type<byte[]>{
    public EIP712AtomicUInt256(byte[] value) {
        super("uint256", value);
        if(value.length != 32) throw new InvalidValueException("uint256 must have byte array size of 32 (32 != "+value.length + ").");
    }

    @Override
    public byte[] _encodeValue(byte[] value){
        return value;
    }


    // conversions
    public static EIP712AtomicUInt256 fromInteger(int input) {
        if (input < 0) throw new InvalidValueException("input cannot be negative: " + input);
        byte[] b32 = new byte[32];

        b32[28] = (byte) ((input & 0xFF000000) >> 24);
        b32[29] = (byte) ((input & 0x00FF0000) >> 16);
        b32[30] = (byte) ((input & 0x0000FF00) >> 8);
        b32[31] = (byte) ((input & 0x000000FF));
        return new EIP712AtomicUInt256(b32);
    }

    public static EIP712AtomicUInt256 fromLong(long input) {
        byte[] b32 = new byte[32];

        b32[24] = (byte) ((input & 0xFF00000000000000L) >> 56);
        b32[25] = (byte) ((input & 0x00FF000000000000L) >> 48);
        b32[26] = (byte) ((input & 0x0000FF0000000000L) >> 40);
        b32[27] = (byte) ((input & 0x000000FF00000000L) >> 32);
        b32[28] = (byte) ((input & 0x00000000FF000000L) >> 24);
        b32[29] = (byte) ((input & 0x0000000000FF0000L) >> 16);
        b32[30] = (byte) ((input & 0x000000000000FF00L) >> 8);
        b32[31] = (byte) ((input & 0x00000000000000FFL));

        return new EIP712AtomicUInt256(b32);
    }
}
