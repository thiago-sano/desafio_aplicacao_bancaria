package br.org.aplicacaobancaria.domain.user;

public abstract class Client {
    private String name;
    private String email;
    private String id;
    private ClientType clientType;

    public Client(String name, String email, String id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
