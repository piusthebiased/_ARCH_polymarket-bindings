# Clob Authentication
This package is complete and in specification to https://docs.polymarket.com/#authentication. Here is a list of the following implementations.
1. L1 Private Key Authentication ✅
2. L2 API Key Authentication ✅
3. `POST {clob-endpoint}/auth/api-key` ✅
4. `GET {clob-endpoint}/auth/derive-api-key` ✅
4. `GET {clob-endpoint}/auth/api-keys` ✅
5. `DELETE {clob-endpoint}/auth/api-key` ✅

When programming, the suitable endpoint to fetch API keys is `GET {clob-endpoint}/auth/derive-api-key`.