package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.*;
import br.org.aplicacaobancaria.domain.services.transaction.Transaction;
import br.org.aplicacaobancaria.domain.user.Business;
import br.org.aplicacaobancaria.domain.user.Client;
import br.org.aplicacaobancaria.domain.user.ClientType;
import br.org.aplicacaobancaria.domain.user.Personal;

import java.util.Scanner;

public class UI {

    public UI(){
    }

    public static int printMainMenu() {
        int option;

        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - CADASTRO DE CLIENTE");
        System.out.println("2 - LISTAR CLIENTES");
        System.out.println("3 - CADASTRO DE CONTA");
        System.out.println("4 - LISTAR CONTAS");
        System.out.println("5 - ENTRAR EM CONTA");
        System.out.println("6 - SAIR");
        System.out.print("Digite uma opcao: ");
        option = readMenuOption();
        return option;
    }

    public static Client printRegisterClient(){
        Scanner sc = new Scanner(System.in);

        System.out.print("NOME DO CLIENTE: ");
        String clientName = sc.next();
        System.out.print("EMAIL DO CLIENTE: ");
        String clientEmail = sc.next();

        while (true){
            System.out.println();
            for(ClientType ct : ClientType.values()){
                System.out.printf("%d - %s%n", ct.getId(), ct.getName());
            }
            System.out.print("INFORME O NUMERO: ");
            int option = readMenuOption();
            System.out.println();
            switch (option){
                case 1:{
                    System.out.print("CPF (somente numeros): ");
                    String clientId = sc.next();
                    return new Personal(clientName, clientEmail, clientId);
                }
                case 2:{
                    System.out.print("CNPJ (somente numeros): ");
                    String clientId = sc.next();
                    System.out.print("CAPITAL DECLARADO: ");
                    Double clientStatedCapital = sc.nextDouble();
                    return new Business(clientName, clientEmail,clientId, clientStatedCapital);
                }
                default:{
                    System.out.println("-> OPCAO INVALIDA");
                    break;
                }
            }
        }
    }

    public static Account printRegisterAccount(Client client){
        Scanner sc = new Scanner(System.in);

        System.out.print("NUMERO DA AGENCIA: ");
        String branchNumber = sc.next();
        System.out.print("NUMERO DA CONTA: ");
        String accountNumber = sc.next();
        while (true){
            System.out.println();
            if (client.getClientType().getAccountType() == 1){
                for(AccountType at : AccountType.values()){
                        System.out.printf("%d - %s%n", at.getId(), at.getName());
                }
                System.out.print("INFORME O NUMERO: ");
                int option = readMenuOption();
                System.out.println();
                switch (option){
                    case 1:{ // CONTA CORRENTE
                        return new CheckingAccount(branchNumber, accountNumber, client);
                    }
                    case 2:{ // CONTA POUPANCA
                        return new SavingsAccount(branchNumber, accountNumber, client);
                    }
                    case 3: { // CONTA SALARIO
                        return new PayrollAccount(branchNumber, accountNumber, client);
                    }
                    default:{
                        System.out.println("-> OPCAO INVALIDA");
                        break;
                    }
                }
            }
            else{
                AccountType at = AccountType.valueOf("CHECKING");
                System.out.printf("%d - %s%n", at.getId(), at.getName());
                System.out.print("INFORME O NUMERO: ");
                int option = readMenuOption();
                System.out.println();
                switch (option){
                    case 1:{ // CONTA CORRENTE
                        return new CheckingAccount(branchNumber, accountNumber, client);
                    }
                    default:{
                        System.out.println("-> OPCAO INVALIDA");
                        break;
                    }
                }
                System.out.println();
            }

        }
    }

    public static String printLoginAccount(){
        Scanner sc = new Scanner(System.in);

        System.out.print("CPF/CNPJ: ");
        return sc.next();
    }

    public static int printAccountMenu(Account account){
        int option;

        System.out.println();
        System.out.printf("Olá, %s. Tipo de conta: %s. Saldo atual: %,.2f\n", account.getClient().getName(), account.getAccountType().getName(), account.getBalance());
        System.out.println("1 - SAQUE");
        System.out.println("2 - DEPOSITO");
        System.out.println("3 - TRANSFERENCIA");
        System.out.println("4 - SAIR");
        System.out.print("Digite uma opcao: ");
        option = readMenuOption();
        System.out.println();
        return option;
    }

    public static Double printDepositTransaction(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Valor a ser depositado: ");
        return sc.nextDouble();
    }

    public static Double printWithdrawTransaction(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Valor a ser sacado: ");
        return sc.nextDouble();
    }

    public static Transaction printTransferTransaction(){
        Scanner sc = new Scanner(System.in);

        System.out.print("CPF/CNPJ de destino: ");
        String receiverId = sc.next();
        System.out.print("Valor a ser transferido: ");
        double amount = sc.nextDouble();

        return new Transaction(amount, receiverId);
    }

    public static int readMenuOption(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
