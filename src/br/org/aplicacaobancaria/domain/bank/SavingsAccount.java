package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public class SavingsAccount extends Account {
    private final AccountType accountType;

    public SavingsAccount(String branchNumber, String accountNumber, Client client) {
        super(branchNumber, accountNumber, client);
        this.accountType = AccountType.SAVINGS;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }
}
