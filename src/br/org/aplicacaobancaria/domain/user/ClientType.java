package br.org.aplicacaobancaria.domain.user;

public enum ClientType {
    PERSONAL ("PESSOA FISICA", "B2C"),
    BUSINESS ("PESSOA JURIDICA", "B2B");

    private String name;
    private String abbreviation;

    ClientType(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setNome(String nome) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation.toUpperCase();
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}
