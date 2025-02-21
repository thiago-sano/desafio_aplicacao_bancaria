package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;
import br.org.aplicacaobancaria.domain.bank.AccountType;
import br.org.aplicacaobancaria.domain.user.ClientType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Deposit, Transfer, Withdraw {
    private double amount;
    private Account sender;
    private Account receiver;
    private String receiverId;
    private TransactionType transactionType;


    public Transaction() {

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

    public boolean timeRestriction(LocalTime now, double amount) {
        LocalTime beginTime = LocalTime.parse("10:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime endTime = LocalTime.parse("13:00:00", DateTimeFormatter.ofPattern("HH:mm:ss"));

        if (beginTime.isBefore(now) & endTime.isAfter(now) & (amount > 1000.0)) {
            System.out.printf("Limite reduzido a R$1.000,00 entre %tT:%tM e %tT:%tM", beginTime, beginTime, endTime, endTime);
            return false;
        } else {
            System.out.println("SEM RESTRIÇÃO DE HORÁRIO");
            return true;
        }
    }

    @Override
    public void deposit(double amount, Account account) {
        if (account.getAccountType() == AccountType.PAYROLL) {
            System.out.println("TRANSACÃO NÃO PERMITIDA PARA CONTA SALÁRIO");
        } else {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Override
    public void withdraw(double amount, Account account) {
        if (timeRestriction(LocalTime.now(), amount)) {
            account.setBalance(account.getBalance() - amount);
        }
    }

    @Override
    public void transfer(double amount, Account sender, Account receiver) {
        if (timeRestriction(LocalTime.now(), amount)) {
            if (receiver.getAccountType() == AccountType.PAYROLL & sender.getClient().getClientType() != ClientType.BUSINESS) {
                System.out.println("CONTA SALÁRIO PODE RECEBER APENAS DE CLIENTES PJ");
            } else if (sender.getAccountType() == AccountType.PAYROLL & !receiver.getClient().getId().equals(sender.getClient().getId())) {
                System.out.println("CONTA SALÁRIO TRANSFERE APENAS PARA MESMA TITULARIDADE");
            } else {
                sender.setBalance(sender.getBalance() - amount);
                receiver.setBalance(receiver.getBalance() + amount);
            }
        }
    }
}
