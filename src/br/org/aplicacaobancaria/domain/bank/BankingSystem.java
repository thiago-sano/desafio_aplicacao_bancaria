package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.services.transaction.Transaction;
import br.org.aplicacaobancaria.domain.services.transaction.TransactionType;
import br.org.aplicacaobancaria.domain.user.Client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BankingSystem {

    Set<Client> clients = new HashSet<>();
    List<Account> accounts = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();

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

    public void registerTransaction (Transaction transaction){
        transactions.add(transaction);
    }

    public List<Transaction> listTransactions(){
        return transactions;
    }

    public List<Transaction> filterTransactionsList(List<Transaction> transactions, Account account){
        return transactions.stream()
                .filter(transaction -> account.getClient().getId().equals(account.getClient().getId()))
                .collect(Collectors.toList());
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
        Transaction transaction1 = new Transaction(amount, account, TransactionType.DEPOSIT);
        transaction1.deposit(amount, account);
        registerTransaction(transaction1);
    }

    public void withdrawTransaction(double amount, Account account){
        transaction.withdraw(amount, account);
    }

    public void transferTransaction(Transaction transaction, Account sender){
        // com receiverId, retornar receiverAccount
        Account receiver = findAccountByUserId(listAccounts(), transaction.getReceiverId());
        transaction.transfer(transaction.getAmount(), sender, receiver);
    }
}
