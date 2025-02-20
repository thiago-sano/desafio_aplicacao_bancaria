package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.Account;
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
        System.out.println("5 - SAIR");
        System.out.print("Digite uma opcao: ");
        option = readMenuOption();
        return option;
    }

    public static Client printRegisterClient(){
        Scanner sc = new Scanner(System.in);

        System.out.print("NOME DO CLIENTE: ");
        String clientName = sc.next().toUpperCase();
        System.out.print("EMAIL DO CLIENTE: ");
        String clientEmail = sc.next().toUpperCase();

        while (true){
            // print com numeracao e utilizacao da classe enum
            System.out.println();
            for(ClientType ct : ClientType.values()){
                System.out.println(ct.getMenuName());
            }
            System.out.print("INFORME O NUMERO: ");
            int option = readMenuOption();
            switch (option){
                case 1:{
                    System.out.print("CPF (somente numeros): ");
                    String clientId = sc.next();
                    return new Personal(clientName, clientEmail, clientId, ClientType.PERSONAL);
                }
                case 2:{
                    System.out.print("CNPJ (somente numeros): ");
                    String clientId = sc.next();
                    System.out.print("CAPITAL DECLARADO: ");
                    Double clientStatedCapital = sc.nextDouble();
                    return new Business(clientName, clientEmail,clientId, ClientType.BUSINESS, clientStatedCapital);
                }
                default:{
                    System.out.println("-> OPCAO INVALIDA");
                    break;
                }
            }
        }
    }

//    public static Account printRegisterAccount(){
//        Scanner sc = new Scanner(System.in);
//
//        System.out.print("NUMERO DA AGENCIA: ");
//        String branchNumber = sc.next();
//        System.out.print("NUMERO DA CONTA: ");
//        String accountNumber = sc.next();
//        Double balance = 0.0;
//        //client
//
//        //AccountType
//
//
//    }


    public static int readMenuOption(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
