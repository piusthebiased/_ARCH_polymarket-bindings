package eth.eip712;

public interface EIP712HashStruct {
    byte[] typeHash();

    byte[] encodeData();

    byte[] hashStruct();
}
