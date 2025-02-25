package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public interface Withdraw {
    void withdraw(double amount, Account account, TransactionType transactionType);
}
