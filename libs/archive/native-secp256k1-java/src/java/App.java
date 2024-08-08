import cryptography.elliptic.NativeSecp256k1;

public class App {
    public static void main(String[] args) {
        NativeSecp256k1.init();
        String hex = "de32df21240e9239efc6f9445606ce617d5550f376784a15a2971eb2421e407b";
        byte[] pubkey = NativeSecp256k1.computePubkey(NativeSecp256k1.contextCreate(), hexStringToByteArray(hex), true);
        System.out.println(bytesToHexString(pubkey));
    }

    // helper methods
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHexString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }
}
