package clob.orders.v3.types.config;

public class ContractConfiguration {
    public String exchange;
    public String collateral;
    public String conditionalTokens;

    public ContractConfiguration(String exchange, String collateral, String conditionalTokens) {
        this.exchange = exchange;
        this.collateral = collateral;
        this.conditionalTokens = conditionalTokens;
    }

    public static ContractConfiguration getContractConfiguration(boolean negativeRisk) {
        ContractConfiguration config = new ContractConfiguration(
                "0x4bFb41d5B3570DeFd03C39a9A4D8dE6Bd8B8982E",
                "0x2791Bca1f2de4661ED88A30C99A7a9449Aa84174",
                "0x4D97DCd97eC945f40cF65F87097ACe5EA0476045"
        );

        if(negativeRisk) config = new ContractConfiguration(
                "0xd91E80cF2E7be2e162c6513ceD06f1dD0dA35296",
                "0x9c4e1703476e875070ee25b56a58b008cfb8fa78",
                "0x69308FB512518e39F9b16112fA8d994F4e2Bf8bB"
        );

        return config;
    }
}
