package br.org.aplicacaobancaria.domain.services.transaction;

import br.org.aplicacaobancaria.domain.bank.Account;

public interface Transfer {
    double transfer (double amount, Account sender, Account receiver);
}
