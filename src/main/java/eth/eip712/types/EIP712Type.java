package eth.eip712.types;

public abstract class EIP712Type<T> {
    private String typeName;
    private T value;

    public EIP712Type(String typeName, T value){
        this.typeName = typeName;
        this.value = value;
    }

    public byte[] encodeValue() {
        return _encodeValue(this.value);
    }

    public byte[] encodeValue(T value) {
        return _encodeValue(value);
    }

    public byte[] _encodeValue(T value) {
        return new byte[0];
    }

    public boolean equals(EIP712Type<T> other) {
        return this.value.equals(other.getValue());
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
