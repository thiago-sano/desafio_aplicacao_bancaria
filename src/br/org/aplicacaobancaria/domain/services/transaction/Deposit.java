package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public interface Deposit {
    double deposit (double amount, Account account);
}
