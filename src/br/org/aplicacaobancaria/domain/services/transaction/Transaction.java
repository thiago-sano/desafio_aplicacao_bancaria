package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;
import br.org.aplicacaobancaria.domain.bank.AccountType;
import br.org.aplicacaobancaria.domain.user.ClientType;

import java.time.LocalTime;

public class Transaction implements Deposit, Transfer, Withdraw {
    private double amount;
    private Account account;
    private Account sender;
    private Account receiver;
    private TransactionType transactionType;
    private String dateTimeNow;

    public Transaction() {

    }

    public Transaction(double amount, Account account, TransactionType transactionType, String dateTimeNow) {
        this.amount = amount;
        this.account = account;
        this.transactionType = transactionType;
        this.dateTimeNow = dateTimeNow;
    }

    public Transaction(double amount, Account sender, Account receiver, TransactionType transactionType, String dateTimeNow) {
        this.amount = amount;
        this.sender = sender;
        this.receiver = receiver;
        this.transactionType = transactionType;
        this.dateTimeNow = dateTimeNow;
    }

    public double getAmount() {
        return amount;
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

    public String getDateTimeNow() {
        return dateTimeNow;
    }

    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public boolean isWithinTimeRestriction(Account account) {
        LocalTime startTime = account.getStartTime();
        LocalTime endTime = account.getEndTime();
        LocalTime now = LocalTime.now();

        if (startTime.isBefore(endTime)) {
            return !now.isBefore(startTime) && !now.isAfter(endTime);
        } else {
            return !now.isBefore(startTime) || !now.isAfter(endTime);
        }
    }

    public boolean isBalancePreviewOk(double amount, Account account) {
        double balancePreview = account.getBalance() - amount;
        if (balancePreview >= 0) {
            return true;
        } else if ((balancePreview >= -account.getLimit()) & (account.getAccountType() == AccountType.CHECKING)) {
            return true;
        } else {
            System.out.println("CONTA SEM LIMITE DISPONÍVEL");
            return false;
        }
    }

    public boolean isTransactionOk(Transaction transaction) {
        switch (transaction.getTransactionType()) {
            case TransactionType.DEPOSIT -> {
                if (transaction.getAccount().getAccountType() == AccountType.PAYROLL) {
                    System.out.println("TRANSACÃO NÃO PERMITIDA PARA CONTA SALÁRIO");
                    return false;
                } else {
                    return true;
                }
            }
            case TransactionType.WITHDRAW -> {
                if (isWithinTimeRestriction(transaction.getAccount())) {
                    if (transaction.getAmount() <= 1000) {
                        if (isBalancePreviewOk(transaction.getAmount(), transaction.getAccount())) {
                            return true;
                        }
                    } else {
                        System.out.println("DENTRO DA RESTRICAO DE HORARIO (" + transaction.getAccount().getStartTime() + " ATE " + transaction.getAccount().getEndTime() + "). MOVIMENTACOES LIMITADAS A R$ 1.000,00");
                        return false;
                    }
                } else {
                    if (isBalancePreviewOk(transaction.getAmount(), transaction.getAccount())) {
                        return true;
                    }
                }
            }
            case TransactionType.TRANSFER -> {
                if (transaction.getReceiver().getAccountType() == AccountType.PAYROLL & transaction.sender.getClient().getClientType() != ClientType.BUSINESS) {
                    System.out.println("CONTA SALÁRIO PODE RECEBER APENAS DE CLIENTES PJ");
                    return false;
                } else if (transaction.getSender().getAccountType() == AccountType.PAYROLL & !transaction.getReceiver().getClient().getId().equals(transaction.getSender().getClient().getId())) {
                    System.out.println("CONTA SALÁRIO TRANSFERE APENAS PARA MESMA TITULARIDADE");
                    return false;
                } else if (transaction.getSender().getClient().getId().equals(transaction.getReceiver().getClient().getId())) {
                    System.out.println("CONTA DE ORIGEM E DESTINO SAO IGUAIS");
                    return false;
                } else {
                    if (isWithinTimeRestriction(transaction.getSender())) {
                        if (transaction.getAmount() <= 1000) {
                            if (isBalancePreviewOk(transaction.getAmount(), transaction.getSender())) {
                                return true;
                            }
                        } else {
                            System.out.println("DENTRO DA RESTRICAO DE HORARIO. MOVIMENTACOES LIMITADAS A R$ 1.000,00");
                            return false;
                        }
                    } else {
                        if (isBalancePreviewOk(transaction.getAmount(), transaction.getSender())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void deposit(double amount, Account account, TransactionType transactionType, String localDateTime) {
        account.setBalance(account.getBalance() + amount);
    }

    @Override
    public void withdraw(double amount, Account account, TransactionType transactionType, String localDateTime) {
        account.setBalance(account.getBalance() - amount);
    }

    @Override
    public void transfer(double amount, Account sender, Account receiver, TransactionType transactionType, String localDateTime) {
        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);
    }

    @Override
    public String toString() {
        return String.format("Data: %s | Tipo: %s | Valor: %,.2f", getDateTimeNow(), getTransactionType().getName(), getAmount());
    }
}
