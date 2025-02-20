package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public class PayrollAccount extends Account{
    private Double limit;
    private Double availableLimit;

    public PayrollAccount(String accountNumber, Double balance, AccountType accountType, Client client) {
        super(accountNumber, balance, accountType, client);
        this.limit = this.availableLimit = 0.0;
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
}
