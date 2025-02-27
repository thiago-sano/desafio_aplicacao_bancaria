package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public class CheckingAccount extends Account {
    private Double limit;
    private final AccountType accountType;

    public CheckingAccount(String branchNumber, String accountNumber, Client client) {
        super(branchNumber, accountNumber, client);
        this.limit = 100.0;
        this.accountType = AccountType.CHECKING;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }
}
