package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.*;
import br.org.aplicacaobancaria.domain.services.transaction.TransactionType;
import br.org.aplicacaobancaria.domain.user.Business;
import br.org.aplicacaobancaria.domain.user.Client;
import br.org.aplicacaobancaria.domain.user.Personal;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;

import static java.lang.System.exit;

public class Program {
    public static void main(String[] args) {

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
        Account account3 = new PayrollAccount("3926", "0129875-1", client3);
        bankingSystem.registerClient(client3);
        bankingSystem.registerAccount(account3);

        Client client4 = new Business("Midia LTDA", "contato@midia.com", "01685012000165", 32568.00);
        Account account4 = new CheckingAccount("4096", "3654812-2", client4);
        bankingSystem.registerClient(client4);
        bankingSystem.registerAccount(account4);

        int option;
        try {
            while (true) {
                option = UI.printMainMenu();
                switch (option) {
                    case 1: {
                        System.out.println("\nMENU PRINCIPAL / CADASTRO DE CONTA");
                        Client client = UI.printRegisterClient();
                        if (bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), client.getId()) == null) {
                            bankingSystem.registerClient(client);
                            Account account = UI.printRegisterAccount(client);
                            bankingSystem.registerAccount(account);
                        } else {
                            System.out.println("CPF/CNPJ ASSOCIADO A OUTRO CLIENTE");
                        }
                        break;
                    }
                    case 2: {
                        System.out.println("\nMENU PRINCIPAL / LISTAR CONTAS");
                        for (Account account : bankingSystem.listAccounts()) {
                            System.out.println(account);
                        }
                        break;
                    }
                    case 3: {
                        option = 0;
                        System.out.println("\nMENU PRINCIPAL / LOGIN CONTA");
                        String userId = UI.printIdScanner();
                        Account account = bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), userId);
                        if (account != null) {
                            while (option != 7) {
                                option = UI.printAccountMenu(account);
                                switch (option) {
                                    case 1: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / SAQUE");
                                        double amount = UI.printAmount();
                                        bankingSystem.withdrawTransaction(amount, account, TransactionType.WITHDRAW);
                                        break;
                                    }
                                    case 2: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / DEPOSITO");
                                        double amount = UI.printAmount();
                                        bankingSystem.depositTransaction(amount, account, TransactionType.DEPOSIT);
                                        break;
                                    }
                                    case 3: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / TRANSFERENCIA");
                                        String userIdReceiver = UI.printIdScanner();
                                        if (bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), userIdReceiver) != null) {
                                            double amount = UI.printAmount();
                                            bankingSystem.transferTransaction(amount, account, bankingSystem.findAccountByUserId(bankingSystem.listAccounts(), userIdReceiver), TransactionType.TRANSFER);
                                        } else {
                                            System.out.println("ERRO: CONTA INEXISTENTE");
                                        }
                                        break;
                                    }
                                    case 4: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / EXTRATO");
                                        bankingSystem.transactionsStatement(bankingSystem.listTransactions(), account);
                                        break;
                                    }
                                    case 5: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / ALTERACAO DE HORARIO");
                                        System.out.println("RESTRICAO ATUAL: " + account.getStartTime() + " ATE " + account.getEndTime());
                                        bankingSystem.editTimeRestriction(account, UI.newTime());
                                        break;
                                    }
                                    case 6: {
                                        System.out.println("\nMENU PRINCIPAL CONTA / ALTERACAO DE LIMITE");
                                        System.out.printf("LIMITE ATUAL: R$ %,.2f\n", account.getLimit());
                                        double amount = UI.printAmount();
                                        bankingSystem.editLimit(account, amount);
                                        break;
                                    }
                                    case 7: {
                                        System.out.println("\nDESLOGADO COM SUCESSO");
                                        break;
                                    }
                                }
                            }
                        } else {
                            System.out.println("ERRO: CONTA INEXISTENTE");
                        }
                        break;
                    }
                    case 4: {
                        System.out.println("\nOBRIGADO PELA PREFERENCIA!");
                        exit(0);
                    }
                    default: {
                        System.out.println("\nESCOLHA UMA OPCAO VALIDA");
                        break;
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("\nEntrada de dados invalida.");
            //e.printStackTrace();
        } catch (DateTimeParseException e) {
            System.out.println("\nEntrada de horario invalida.");
            //e.printStackTrace();
        } catch (IOException e) {
            System.out.println("\nInput/Output vazio.");
            //e.printStackTrace();
        }
    }
}
