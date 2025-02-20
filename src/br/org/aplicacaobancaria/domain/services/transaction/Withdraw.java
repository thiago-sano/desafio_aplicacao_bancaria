package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public interface Withdraw {
    double withdraw(double amount, Account account);
}
