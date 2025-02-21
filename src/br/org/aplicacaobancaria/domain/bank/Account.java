package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

public abstract class Account {
    private String branchNumber;
    private String accountNumber;
    private Double balance;
    private AccountType accountType;
    private Client client;

    public Account(String branchNumber, String accountNumber, Client client) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.balance = balance = 0.0;
        this.client = client;
    }

    public Account(String branchNumber, String accountNumber) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(String branchNumber) {
        this.branchNumber = branchNumber;
    }

    @Override
    public String toString() {
        return String.format("Nome: %-20s | Tipo: %-10s | CPF/CNPJ : %-15s | Agencia: %-4s | Conta: %-7s | Tipo de conta: %-15s | Saldo: %.2f", getClient().getName(), getClient().getClientType().getName(), getClient().getId(), getBranchNumber(), getAccountNumber(), getAccountType().getName(), getBalance());
    }
}
