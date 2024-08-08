package clob.headers;

public class AuthenticationHeaders {
    private String polyAddress;
    private String polySignature;

    public HeaderSet createL1Headers() {
        HeaderSet L1 = new HeaderSet();
        L1.addHeader("POLY_TIMESTAMP", getTimestamp());
        L1.addHeader("POLY_NONCE", 0);

        return L1;
    }


    public static long getTimestamp() {
        return System.currentTimeMillis()/1000;
    }
}
