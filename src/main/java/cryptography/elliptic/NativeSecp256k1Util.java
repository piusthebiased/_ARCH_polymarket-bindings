package cryptography.elliptic;

public final class NativeSecp256k1Util {

    public static void assertEquals(int val, int val2, String message) throws AssertFailException {
        if (val != val2)
            throw new AssertFailException("FAIL: " + message);
    }

    public static void assertEquals(boolean val, boolean val2, String message) throws AssertFailException {
        if (val != val2)
            throw new AssertFailException("FAIL: " + message);
        else
            System.out.println("PASS: " + message);
    }

    public static void assertEquals(String val, String val2, String message) throws AssertFailException {
        if (!val.equals(val2))
            throw new AssertFailException("FAIL: " + message);
        else
            System.out.println("PASS: " + message);
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * @param expression a boolean expression
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * @param expression a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *         string using {@link String#valueOf(Object)}
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    public static class AssertFailException extends RuntimeException {
        public AssertFailException(String message) {
            super(message);
        }
    }
}

