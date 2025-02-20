package br.org.aplicacaobancaria.domain.bank;

import br.org.aplicacaobancaria.domain.user.Client;

import java.util.HashSet;
import java.util.Set;

public class BankingSystem {

    Set<Client> clients = new HashSet<>();

    public BankingSystem(){

    }

    public void registerClient(Client client){
        clients.add(client);
    }

    public void listClients(){
        for (Client client : clients){
            System.out.println(client);
        }
    }
}
