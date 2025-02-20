package br.org.aplicacaobancaria.domain.user;

public enum ClientType {
    PERSONAL ("Pessoa Fisica", "B2C"),
    BUSINESS ("Pessoa Juridica", "B2B");

    private String nome;
    private String sigla;

    ClientType(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla.toUpperCase();
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
}
