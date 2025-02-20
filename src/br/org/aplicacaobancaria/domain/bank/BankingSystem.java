package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BankingSystem {

    Set<Client> clients = new HashSet<>();
    List<Account> accounts = new ArrayList<>();

    public BankingSystem(){

    }

    public Client registerClient(Client client){
        clients.add(client);
        return client;
    }

    public void listClients(){
        for (Client client : clients){
            System.out.println(client);
        }
        System.out.println();
    }

    public void registerAccount(Account account){
        accounts.add(account);
    }

    public void listAccounts(){
        for (Account account : accounts){
            System.out.println(account);
        }
        System.out.println();
    }
}
