package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.user.Business;
import br.org.aplicacaobancaria.domain.user.Client;
import br.org.aplicacaobancaria.domain.user.Personal;

import java.util.Scanner;

public class UI {

    public UI(){
    }

    public static int printMainMenu() {
        clearScreen();
        int option;
        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - CADASTRO DE CLIENTE");
        System.out.println("2 - LISTAR CLIENTES");
        System.out.println("3 - SAIR");
        System.out.print("Digite uma opcao: ");
        option = readMenuOption();
        return option;
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static Client printRegisterClient(){
        Scanner sc = new Scanner(System.in);

        clearScreen();
        System.out.print("NOME DO CLIENTE: ");
        String clientName = sc.next();
        System.out.print("EMAIL DO CLIENTE: ");
        String clientEmail = sc.next();

        while (true){
            System.out.println("1 - PESSOA FISICA");
            System.out.println("2 - PESSOA JURIDICA");
            System.out.print("INFORME O TIPO DE CONTA (1/2): ");
            int option = readMenuOption();
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

    public static int readMenuOption(){
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}
