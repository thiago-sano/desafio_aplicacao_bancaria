package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public class Transaction implements Deposit, Transfer, Withdraw{
    private double amount;
    private Account sender;
    private Account receiver;
    private String receiverId;
    private TransactionType transactionType;

    public Transaction(){

    }
    public Transaction(double amount, String receiverId) {
        this.amount = amount;
        this.receiverId = receiverId;
    }

    public Transaction(double amount, Account sender, Account receiver) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
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
    public void deposit(double amount, Account account) {
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(double amount, Account account) {
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void transfer(double amount, Account sender, Account receiver) {
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
    }
}
