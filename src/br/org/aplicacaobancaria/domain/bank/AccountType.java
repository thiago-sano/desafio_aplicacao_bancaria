package br.org.aplicacaobancaria.domain.bank;

public enum AccountType {
    CHECKING(1, "CONTA CORRENTE"),
    SAVINGS(2, "CONTA POUPANCA"),
    PAYROLL(3, "CONTA SALARIO");

    private int id;
    private String name;

    AccountType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return id;
    }
    public String getName() {
        return name.toUpperCase();
    }
}
