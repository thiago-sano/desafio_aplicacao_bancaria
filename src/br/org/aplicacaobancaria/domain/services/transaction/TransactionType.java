package br.org.aplicacaobancaria.domain.services.transaction;

public enum TransactionType {
    WITHDRAW (1, "SAQUE"),
    DEPOSIT (2, "DEPOSITO"),
    TRANSFER (3, "TRANSFERENCIA");

    private int id;
    private String name;

    TransactionType(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
