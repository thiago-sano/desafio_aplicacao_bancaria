package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public class CheckingAccount extends Account{
    private Double limit;
    private Double availableLimit;
    private final AccountType accountType;

    public CheckingAccount(String accountNumber, Double balance, AccountType accountType, Client client) {
        super(accountNumber, balance, accountType, client);
        this.availableLimit = 100.0;
        this.limit = availableLimit;
        this.accountType = AccountType.CHECKING;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(Double availableLimit) {
        this.availableLimit = availableLimit;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }
}
