package eth.eip712.types;

import cryptography.utils.HexUtils;
import eth.EthUtils;

public class EIP712AtomicAddress extends EIP712Type<byte[]>{
    // given 20 byte array.
    public EIP712AtomicAddress(byte[] add) {
        super("address", add); // 160bits / (1 bit / 8 byte)
        if(add.length != 20) throw new InvalidValueException("Ethereum addresses are of length 20 (u160), length " + add.length +" is incompatible");
    }

    @Override
    public byte[] _encodeValue(byte[] add) {
        return EthUtils.concat(new byte[12], add);
    }

    // converters
    public static EIP712AtomicAddress fromByte(String add) {
        String raw = EthUtils.removeZX(add);
        return new EIP712AtomicAddress(HexUtils.hexStringToByteArray(raw.toLowerCase()));
    }
}
