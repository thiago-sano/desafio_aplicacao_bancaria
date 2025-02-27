package br.org.aplicacaobancaria.domain.user;

public enum ClientType {
    PERSONAL(1, "PESSOA FISICA", 1),
    BUSINESS(2, "PESSOA JURIDICA", 2);

    private int id;
    private String name;
    private int accountType;

    ClientType(int id, String name, int accountType) {
        this.id = id;
        this.name = name;
        this.accountType = accountType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public int getAccountType() {
        return accountType;
    }
}
