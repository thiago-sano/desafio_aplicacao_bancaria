package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

import java.time.LocalTime;

public abstract class Account {
    private final String branchNumber;
    private final String accountNumber;
    private Double balance;
    private AccountType accountType;
    private Client client;
    private Double limit;
    private LocalTime startTime;
    private LocalTime endTime;

    public Account(String branchNumber, String accountNumber, Client client) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.client = client;
        this.startTime = LocalTime.of(20, 0);
        this.endTime = LocalTime.of(6, 0);
    }

    public Account(String branchNumber, String accountNumber) {
        this.branchNumber = branchNumber;
        this.accountNumber = accountNumber;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getBranchNumber() {
        return branchNumber;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public double availableLimit() {
        if (getBalance() <= 0) {
            return getBalance() + getLimit();
        } else {
            return getBalance();
        }
    }

    @Override
    public String toString() {
        return String.format("Nome: %-20s | Tipo: %-10s | CPF/CNPJ : %-15s | Agencia: %-4s | Conta: %-7s | Tipo de conta: %-15s | Saldo: %.2f", getClient().getName(), getClient().getClientType().getName(), getClient().getId(), getBranchNumber(), getAccountNumber(), getAccountType().getName(), getBalance());
    }
}
