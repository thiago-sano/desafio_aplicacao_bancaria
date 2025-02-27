package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.*;
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

        System.out.println();
        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - CADASTRO DE CONTA");
        System.out.println("2 - LISTAR CONTAS");
        System.out.println("3 - ENTRAR EM CONTA");
        System.out.println("4 - SAIR");
        System.out.print("DIGITE UMA OPCAO: ");
        option = readMenuOption();
        return option;
    }

    public static Client printRegisterClient(){
        Scanner sc = new Scanner(System.in);

        System.out.print("NOME DO CLIENTE: ");
        String clientName = sc.nextLine();
        System.out.print("EMAIL DO CLIENTE: ");
        String clientEmail = sc.next();

        while (true){
            System.out.println();
            System.out.println("TIPO DE CLIENTE:");
            for(ClientType ct : ClientType.values()){
                System.out.printf("%d - %s%n", ct.getId(), ct.getName());
            }
            System.out.print("INFORME O NUMERO: ");
            int option = readMenuOption();
            System.out.println();
            switch (option){
                case 1:{
                    System.out.print("CPF (SOMENTE NUMEROS): ");
                    String clientId = sc.next();
                    return new Personal(clientName, clientEmail, clientId);
                }
                case 2:{
                    System.out.print("CNPJ (SOMENTE NUMEROS): ");
                    String clientId = sc.next();
                    System.out.print("CAPITAL DECLARADO: ");
                    Double clientStatedCapital = sc.nextDouble();
                    return new Business(clientName, clientEmail,clientId, clientStatedCapital);
                }
                default:{
                    System.out.println("ERRO: OPCAO INVALIDA");
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
        System.out.println();
        while (true){
            System.out.println("TIPO DE CONTA: ");
            if (client instanceof Personal){
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
                        System.out.println("ERRO: OPCAO INVALIDA");
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
                        System.out.println("ERRO: OPCAO INVALIDA");
                        break;
                    }
                }
                System.out.println();
            }
        }
    }

    public static String printIdScanner(){
        Scanner sc = new Scanner(System.in);
        System.out.print("CPF/CNPJ: ");
        return sc.next();
    }

    public static int printAccountMenu(Account account){
        int option;

        System.out.println("\nMENU PRINCIPAL CONTA");
        System.out.printf("Olá, %s. Tipo de conta: %s. Saldo atual: %,.2f. Limite disponivel: %,.2f\n", account.getClient().getName(), account.getAccountType().getName(), account.getBalance(), account.availableLimit());
        System.out.println("1 - SAQUE");
        System.out.println("2 - DEPOSITO");
        System.out.println("3 - TRANSFERENCIA");
        System.out.println("4 - EXTRATO");
        System.out.println("5 - SAIR");
        System.out.print("DIGITE UMA OPCAO: ");
        option = readMenuOption();
        return option;
    }

    public static Double printAmount(){
        Scanner sc = new Scanner(System.in);
        System.out.print("VALOR: ");
        return sc.nextDouble();
    }

    public static int readMenuOption(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
