package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.services.transaction.Transaction;
import br.org.aplicacaobancaria.domain.user.Client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BankingSystem {

    Set<Client> clients = new HashSet<>();
    List<Account> accounts = new ArrayList<>();
    Transaction transaction = new Transaction();

    public BankingSystem(){

    }

    public Client registerClient(Client client){
        clients.add(client);
        return client;
    }

    public Set<Client> listClients(){
        return clients;
    }

    public void registerAccount(Account account){
        accounts.add(account);
    }

    public List<Account> listAccounts(){
        return accounts;
    }

    public Account findAccountByUserId(List<Account> accounts, String userId) {
        for (Account account : accounts) {
            if (account.getClient().getId().equals(userId)) {
                return account;
            }
        }
        return null;
    }

    public void depositTransaction(double amount, Account account){
        account.setBalance(transaction.deposit(amount, account));
    }

    public void withdrawTransaction(double amount, Account account){
        account.setBalance(transaction.withdraw(amount, account));
    }
}
