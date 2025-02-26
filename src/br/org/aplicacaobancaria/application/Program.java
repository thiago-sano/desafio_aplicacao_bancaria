package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.*;
import br.org.aplicacaobancaria.domain.services.transaction.Transaction;
import br.org.aplicacaobancaria.domain.services.transaction.TransactionType;
import br.org.aplicacaobancaria.domain.user.Business;
import br.org.aplicacaobancaria.domain.user.Client;
import br.org.aplicacaobancaria.domain.user.Personal;

import java.io.IOException;
import java.util.List;

import static java.lang.System.exit;

public class Program {
    public static void main(String[] args) throws IOException {

        BankingSystem bankingSystem = new BankingSystem();
        // Cadastro hardcode
        Client client1 = new Personal("Alex Carlos", "alexcarlos@gmail.com", "34865498778");
        Account account1 = new CheckingAccount("3926", "0045678-8", client1);
        bankingSystem.registerClient(client1);
        bankingSystem.registerAccount(account1);

        Client client2 = new Personal("Bryan Ferreira", "bryanferreira@outlook.com", "46782215486");
        Account account2 = new SavingsAccount("4096", "0129875-1", client2);
        bankingSystem.registerClient(client2);
        bankingSystem.registerAccount(account2);

        Client client3 = new Personal("Caio Azevedo", "caioazevedo@hotmail.com", "12485822212");
        Account account3 = new PayrollAccount("4096", "0129875-1", client3);
        bankingSystem.registerClient(client3);
        bankingSystem.registerAccount(account3);

        Client client4 = new Business("Midia LTDA", "contato@midia.com", "01685012000165", 32568.00);
        Account account4 = new CheckingAccount("4096", "0129875-1", client4);
        bankingSystem.registerClient(client4);
        bankingSystem.registerAccount(account4);

        int option;
        while (true){
            option = UI.printMainMenu();
            switch (option){
                case 1:{
                    System.out.println("\nMENU PRINCIPAL / CADASTRO DE CLIENTE");
                    System.out.println("-> CADASTRO DE CLIENTE\n");
                    bankingSystem.registerClient(UI.printRegisterClient());
                    System.out.println();
                    break;
                }
                case 2:{
                    System.out.println("\nMENU PRINCIPAL / LISTAR CLIENTES");
                    System.out.println("-> LISTAR CLIENTES\n");
                    for (Client client : bankingSystem.listClients()){
                        System.out.println(client);
                    }
                    System.out.println();
                    break;
                }
                case 3:{
                    System.out.println("\nMENU PRINCIPAL / CADASTRO DE CONTA");
                    System.out.println("-> CADASTRO DE CONTA\n");
                    Client client = UI.printRegisterClient();
                    bankingSystem.registerClient(client);
                    Account account = UI.printRegisterAccount(client);
                    bankingSystem.registerAccount(account);
                    break;
                }
                case 4:{
                    System.out.println("\nMENU PRINCIPAL / LISTAR CONTAS");
                    System.out.println("-> LISTAR CONTAS\n");
                    for (Account account : bankingSystem.listAccounts()){
                        System.out.println(account);
                    }
                    System.out.println();
                    break;
                }
                case 5:{
                    // "LOGAR" EM CONTA ESPECIFICA
                    option = 0;
                    System.out.println("\nMENU PRINCIPAL / ENTRAR EM CONTA");
                    System.out.println("-> LOGIN\n");
                    String userId = UI.printIdScanner();
                    Account account = bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), userId);
                    if(account != null){
                        while(option !=5){
                            option = UI.printAccountMenu(account);
                            switch (option){
                                case 1:{
                                    double amount = UI.printAmount();
                                    bankingSystem.withdrawTransaction(amount, account, TransactionType.WITHDRAW);
                                    break;
                                }
                                case 2:{
                                    double amount = UI.printAmount();
                                    bankingSystem.depositTransaction(amount, account, TransactionType.DEPOSIT);
                                    break;
                                }
                                case 3:{
                                    // transferencia
                                    String userIdReceiver = UI.printIdScanner();
                                    double amount = UI.printAmount();
                                    bankingSystem.transferTransaction(amount, account, bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), userIdReceiver), TransactionType.TRANSFER);
                                    break;
                                }
                                case 4:{
                                    //extrato
                                    //List<Transaction> transactionsByAccount = bankingSystem.filterTransactionsList(bankingSystem.listTransactions(), account);
                                    bankingSystem.transactionsStatement(bankingSystem.listTransactions(), account);
                                    break;
                                }
                                case 5:{
                                    System.out.println("DESLOGADO COM SUCESSO\n");
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("Conta inexistente");
                    }
                    break;
                }
                case 6:{
                    System.out.println("\nOBRIGADO PELA PREFERENCIA!");
                    exit(0);
                }
                default:{
                    System.out.println("\nESCOLHA UMA OPCAO VALIDA\n");
                    break;
                }
            }
        }
    }
}
