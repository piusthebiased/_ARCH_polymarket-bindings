# Polymarket-Bindings
The following repository is an archived set of bindings that fit to spec unto Polymarket's API, but it's faster.

## Key features
1. Native `secp256k1` bindings (from Edward Stock's [implementation](https://github.com/edwardstock/native-secp256k1-java))
2. Native `keccak256` hashing. Roughly 100x faster than the official Polymarket python library.
3. Calls to the Polymarket REST APIs
4. Object serialization
