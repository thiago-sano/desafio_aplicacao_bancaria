package br.org.aplicacaobancaria.application;

import br.org.aplicacaobancaria.domain.bank.BankingSystem;

import static java.lang.System.exit;

public class Program {
    public static void main(String[] args) {

        BankingSystem bankingSystem = new BankingSystem();

        int option;
        while (true){
            option = UI.printMainMenu();
            switch (option){
                case 1:{
                    System.out.println("\nMENU PRINCIPAL / CADASTRO DE CLIENTE");
                    System.out.println("-> CADASTRO DE CLIENTE\n");
                    bankingSystem.registerClient(UI.printRegisterClient());
                    break;
                }
                case 2:{
                    System.out.println("\nMENU PRINCIPAL / LISTAR CLIENTES");
                    System.out.println("-> LISTAR CLIENTES\n");
                    bankingSystem.listClients();
                    break;
                }
                case 3:{
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
