package cryptography.keys;

public class MalformedPrivateKeyException extends Exception {
    // Parameterless Constructor
    public MalformedPrivateKeyException() {}

    // Constructor that accepts a message
    public MalformedPrivateKeyException(String message) {
        super(message);
    }
}