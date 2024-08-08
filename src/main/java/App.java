import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;
import eth.CryptoUtils;

public class App {
    public static void main(String[] args) {
        JniLoader.init();
        byte[] pri = HexUtils.hexStringToByteArray("aa804c0f4372cc9593c238dd5a0c5dd9151f69b6773bd64dc079a735313a8269");
        byte[] eth_pub = CryptoUtils.ethAddressFromPrivateKey(pri);

        System.out.println("0x" + HexUtils.bytesToHexString(eth_pub).toLowerCase());
    }
}