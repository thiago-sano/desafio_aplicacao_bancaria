package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public class Transaction implements Deposit, Transfer, Withdraw{


    @Override
    public double deposit(double amount, Account account) {
        return account.getBalance() + amount;
    }

    @Override
    public double transfer(double amount, Account sender, Account receiver) {
        return 0;
    }

    @Override
    public double withdraw(double amount, Account account) {
        return account.getBalance() - amount;
    }
}
