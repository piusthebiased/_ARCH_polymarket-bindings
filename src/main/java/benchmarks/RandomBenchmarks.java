package benchmarks;

import clob.auth.eip712.ClobAuthDomainStruct;
import clob.auth.eip712.ClobAuthStruct;
import cryptography.keys.Key;
import cryptography.elliptic.NativeSecp256k1;
import cryptography.hash.Keccak256;
import cryptography.utils.HexUtils;
import cryptography.utils.JniLoader;
import eth.EthUtils;
import eth.eip712.EIP712Signature;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 3, time = 1) 		// Warmup Iteration = 3
@Measurement(iterations = 8, time = 1) 	// Iteration = 8
public class RandomBenchmarks {

    @State(Scope.Thread)
    public static class BenchmarkState {
        byte[] digest;
        Key key;

        byte[] bitstring1;
        byte[] bitstring2;
        byte[] bitstring3;

        long context;

        @Setup
        public void prepare() {
            // init program
            JniLoader.init();
            context = NativeSecp256k1.contextCreate();

            // load raw key
            byte[] raw = HexUtils.hexStringToByteArray("aa804c0f4372cc9593c238dd5a0c5dd9151f69b6773bd64dc079a735313a8269");
            this.key = new Key(raw);

            this.bitstring1 = raw.clone();
            this.bitstring2 = raw.clone();
            this.bitstring3 = raw.clone();

            // get signature
            byte[] header = EIP712Signature.HEADER;
            byte[] domain = ClobAuthDomainStruct.precomputedHashStruct();

            ClobAuthStruct auth = new ClobAuthStruct(key);
            byte[] encoding = EthUtils.concat(header, domain, auth.hashStruct());
            this.digest = Keccak256.digest(encoding);
        }
    }

    @Benchmark
    public void decimalPlaces(BenchmarkState state) {
        float x = (float) Math.random();
        String.valueOf(x).split("\\.")[1].length();
    }

    @Benchmark
    public void randomSpeed(BenchmarkState state) {
        float x = (float) Math.random();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(RandomBenchmarks.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
