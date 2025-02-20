package br.org.aplicacaobancaria.domain.user;

public abstract class Client {
    private String name;
    private String email;
    private String id;
    private final ClientType clientType;

    public Client(String name, String email, String id, ClientType clientType) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setId(String id) {
        this.id = id;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
