package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public class PayrollAccount extends Account {
    private final AccountType accountType;

    public PayrollAccount(String branchNumber, String accountNumber, Client client) {
        super(branchNumber, accountNumber, client);
        this.accountType = AccountType.PAYROLL;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }
}
