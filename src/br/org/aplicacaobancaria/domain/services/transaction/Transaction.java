package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;
import br.org.aplicacaobancaria.domain.bank.AccountType;
import br.org.aplicacaobancaria.domain.user.ClientType;

import java.time.LocalTime;

public class Transaction implements Deposit, Transfer, Withdraw {
    private double amount;
    private Account sender;
    private Account receiver;
    private Account account;
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

    public Transaction(double amount, Account account, TransactionType transactionType) {
        this.amount = amount;
        this.account = account;
        this.transactionType = transactionType;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isWithinTimeRestriction() {
        LocalTime startTime = LocalTime.of(19, 0);
        LocalTime endTime = LocalTime.of(6, 0);
        LocalTime now = LocalTime.now();

        if (startTime.isBefore(endTime)) {
            // Horário de início e de encerramento no mesmo dia
            return !now.isBefore(startTime) && !now.isAfter(endTime);
        } else {
            // Horário de início e de encerramento em dias diferentes
            return !now.isBefore(startTime) || !now.isAfter(endTime);
        }
    }

    public boolean isBalancePreviewOk(double amount, Account account){

        double balancePreview = account.getBalance() - amount;
        if (balancePreview >= 0){
            return true;
        }

        else if ((balancePreview >= -account.getLimit()) & (account.getAccountType() == AccountType.CHECKING)){
            return true;
        }
        else{
            System.out.println("CONTA SEM LIMITE DISPONÍVEL");
            return false;
        }
    }

    @Override
    public void deposit(double amount, Account account, TransactionType transactionType) {
        if (account.getAccountType() == AccountType.PAYROLL) {
            System.out.println("TRANSACÃO NÃO PERMITIDA PARA CONTA SALÁRIO");
        } else {
            account.setBalance(account.getBalance() + amount);
        }
    }

    @Override
    public void withdraw(double amount, Account account, TransactionType transactionType) {
        if (isWithinTimeRestriction()) {
            if (amount <= 1000){
                if (isBalancePreviewOk(amount, account)){
                    account.setBalance(account.getBalance() - amount);
                }
            }
            else {
                System.out.println("DENTRO DA RESTRICAO DE HORARIO. MOVIMENTACOES LIMITADAS A R$ 1.000,00");
            }
        }
        else {
            System.out.println("FORA DA RESTRICAO DE HORARIO");
            if (isBalancePreviewOk(amount, account)){
                account.setBalance(account.getBalance() - amount);
            }
        }
    }

    @Override
    public void transfer(double amount, Account sender, Account receiver, TransactionType transactionType) {

        if (receiver.getAccountType() == AccountType.PAYROLL & sender.getClient().getClientType() != ClientType.BUSINESS) {
            System.out.println("CONTA SALÁRIO PODE RECEBER APENAS DE CLIENTES PJ");
        }
        else if (sender.getAccountType() == AccountType.PAYROLL & !receiver.getClient().getId().equals(sender.getClient().getId())) {
            System.out.println("CONTA SALÁRIO TRANSFERE APENAS PARA MESMA TITULARIDADE");
        }
        else {
            if (isWithinTimeRestriction()) {
                if (amount <= 1000){
                    if (isBalancePreviewOk(amount, sender)) {
                        sender.setBalance(sender.getBalance() - amount);
                        receiver.setBalance(receiver.getBalance() + amount);
                    }
                }
                else {
                    System.out.println("DENTRO DA RESTRICAO DE HORARIO. MOVIMENTACOES LIMITADAS A R$ 1.000,00");
                }
            }
            else {
                System.out.println("FORA DA RESTRICAO DE HORARIO");
                if (isBalancePreviewOk(amount, sender)) {
                    sender.setBalance(sender.getBalance() - amount);
                    receiver.setBalance(receiver.getBalance() + amount);
                }
            }
        }
    }

    @Override
    public String toString() {
            return String.format("Tipo: %s | Valor: %,.2f", getTransactionType().getName(), getAmount());
    }
}
