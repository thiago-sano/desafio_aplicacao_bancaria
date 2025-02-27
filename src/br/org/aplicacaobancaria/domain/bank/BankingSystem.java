package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.services.transaction.Transaction;
import br.org.aplicacaobancaria.domain.services.transaction.TransactionType;
import br.org.aplicacaobancaria.domain.user.Business;
import br.org.aplicacaobancaria.domain.user.Client;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BankingSystem {

    Set<Client> clients = new HashSet<>();
    List<Account> accounts = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();

    Transaction transaction = new Transaction();

    public BankingSystem() {

    }

    public Client registerClient(Client client) {
        clients.add(client);
        return client;
    }

    public Set<Client> listClients() {
        return clients;
    }

    public void registerAccount(Account account) {
        if (account.getClient() instanceof Business) {
            Business businessClient = (Business) account.getClient();
            account.setLimit(businessClient.getStatedCapital() * 0.65);
        } else {
            if (account instanceof CheckingAccount) {
                account.setLimit(100.0);
            } else {
                account.setLimit(0.0);
            }
        }
        accounts.add(account);
    }

    public List<Account> listAccounts() {
        return accounts;
    }

    public void recordTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public List<Transaction> listTransactions() {
        return transactions;
    }

    public List<Transaction> filterTransactionsList(List<Transaction> transactions, Account account) {
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

    public String dateTimeNow() {
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return ldt.format(dtf);
    }

    public void editTimeRestriction(Account account, LocalTime[] newTime) {
        account.setStartTime(newTime[0]);
        account.setEndTime(newTime[1]);
    }

    public void transactionsStatement(List<Transaction> transactions, Account account) throws IOException {
        List<Transaction> filteredTransactions = filterTransactionsList(transactions, account);
        Path path = Paths.get("..\\desafio_aplicacao_bancaria\\file");
        Path destinationFile = Paths.get("..\\desafio_aplicacao_bancaria\\file\\transaction-statement_" + account.getClient().getId() + ".csv");
        try {
            StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("DATA/HORA;TIPO TRANSACAO;VALOR");
            strBuilder.append(System.lineSeparator());
            for (Transaction transaction : filteredTransactions) {
                strBuilder.append(transaction.getDateTimeNow() + ";");
                strBuilder.append(transaction.getTransactionType().getName() + ";");
                strBuilder.append(String.format("%,.2f", transaction.getAmount()));
                strBuilder.append(System.lineSeparator());
            }
            try {
                Files.write(destinationFile, strBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            } catch (NoSuchFileException e) {
                Files.createDirectory(path);
                Files.write(destinationFile, strBuilder.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void withdrawTransaction(double amount, Account account, TransactionType transactionType) {
        Transaction withdrawTransaction = new Transaction(amount, account, transactionType, dateTimeNow());
        if (transaction.isTransactionOk(withdrawTransaction)) {
            transaction.withdraw(withdrawTransaction.getAmount(), withdrawTransaction.getAccount(), withdrawTransaction.getTransactionType(), withdrawTransaction.getDateTimeNow());
            Transaction transactionRecord = new Transaction(-withdrawTransaction.getAmount(), withdrawTransaction.getAccount(), withdrawTransaction.getTransactionType(), withdrawTransaction.getDateTimeNow());
            recordTransaction(transactionRecord);
        }
    }

    public void depositTransaction(double amount, Account account, TransactionType transactionType) {
        Transaction depositTransaction = new Transaction(amount, account, transactionType, dateTimeNow());
        if (transaction.isTransactionOk(depositTransaction)) {
            transaction.deposit(depositTransaction.getAmount(), depositTransaction.getAccount(), depositTransaction.getTransactionType(), depositTransaction.getDateTimeNow());
            recordTransaction(depositTransaction);
        }
    }

    public void transferTransaction(double amount, Account sender, Account receiver, TransactionType transactionType) {
        Transaction transferTransaction = new Transaction(amount, sender, receiver, transactionType, dateTimeNow());
        if (transaction.isTransactionOk(transferTransaction)) {
            transaction.transfer(transferTransaction.getAmount(), transferTransaction.getSender(), transferTransaction.getReceiver(), transferTransaction.getTransactionType(), transferTransaction.getDateTimeNow());
            Transaction transactionRecordSender = new Transaction(-transferTransaction.getAmount(), transferTransaction.getSender(), transferTransaction.getTransactionType(), transferTransaction.getDateTimeNow());
            Transaction transactionRecordReceiver = new Transaction(transferTransaction.getAmount(), transferTransaction.getReceiver(), transferTransaction.getTransactionType(), transferTransaction.getDateTimeNow());
            recordTransaction(transactionRecordSender);
            recordTransaction(transactionRecordReceiver);
        }
    }
}
