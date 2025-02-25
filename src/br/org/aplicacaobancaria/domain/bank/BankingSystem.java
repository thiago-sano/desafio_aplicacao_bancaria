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

    public void recordTransaction (Transaction transaction){
        transactions.add(transaction);
    }

    public List<Transaction> listTransactions(){
        return transactions;
    }

    public List<Transaction> filterTransactionsList(List<Transaction> transactions, Account account){
        // Comparar  clientId da lista com clientId da account "logada"
        return transactions.stream()
                .filter(transaction -> transaction.getAccount().getClient().getId().equals(account.getClient().getId()))
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

    public void depositTransaction(double amount, Account account, TransactionType transactionType){
        transaction.deposit(amount, account, transactionType);
        Transaction transactionRecord = new Transaction(amount, account, transactionType);
        recordTransaction(transactionRecord);
    }

    public void withdrawTransaction(double amount, Account account, TransactionType transactionType){
        transaction.withdraw(amount, account, transactionType);
        Transaction transactionRecord = new Transaction(amount, account, transactionType);
        recordTransaction(transactionRecord);
    }

    public void transferTransaction(Transaction transactionGenerated, Account sender, TransactionType transactionType){
        // com receiverId, retornar receiverAccount
        Account receiver = findAccountByUserId(listAccounts(), transactionGenerated.getReceiverId());
        transaction.transfer(transactionGenerated.getAmount(), sender, receiver, transactionType);

        Transaction transactionRecordSender = new Transaction(-transactionGenerated.getAmount(), sender, transactionType);
        Transaction transactionRecordReceiver = new Transaction(transactionGenerated.getAmount(), receiver, transactionType);
        recordTransaction(transactionRecordSender);
        recordTransaction(transactionRecordReceiver);
    }
}
