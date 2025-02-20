package br.org.aplicacaobancaria.domain.user;

public enum ClientType {
    PERSONAL ("1 - PESSOA FISICA", "PESSOA FISICA"),
    BUSINESS ("2 - PESSOA JURIDICA", "PESSOA JURIDICA");

    private String menuName;
    private String name;

    ClientType(String menuName, String name) {
        this.menuName = menuName;
        this.name = name;
    }

    public String getMenuName() {
        return menuName.toUpperCase();
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getName() {
        return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name;
    }
}
