package br.org.aplicacaobancaria.domain.bank;

public enum AccountType {
    SAVINGS("conta poupanca"),
    CHECKING("Conta corrente"),
    PAYROLL("Conta de pagamento");

    private String nome;

    AccountType(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
