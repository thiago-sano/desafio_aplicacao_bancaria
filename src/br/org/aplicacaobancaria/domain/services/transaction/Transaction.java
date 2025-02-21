package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public class Transaction implements Deposit, Transfer, Withdraw{
    private double amount;
    private Account sender;
    private Account receiver;
    private TransactionType transactionType;

    public Transaction(){

    }
    public Transaction(double amount, Account receiver) {
        this.amount = amount;
        this.receiver = receiver;
    }

    public Transaction(double amount, Account sender, Account receiver) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public double deposit(double amount, Account account) {
        return account.getBalance() + amount;
    }

    @Override
    public double withdraw(double amount, Account account) {
        return account.getBalance() - amount;
    }

    @Override
    public double transfer(double amount, Account sender, Account receiver) {
        return 0;
    }
}
