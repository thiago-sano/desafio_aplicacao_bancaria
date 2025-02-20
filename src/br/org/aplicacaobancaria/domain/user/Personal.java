package br.org.aplicacaobancaria.domain.user;

public class Personal extends Client{
    private final ClientType clientType;

    public Personal(String name, String email, String id) {
        super(name, email, id);
        clientType = ClientType.PERSONAL;
    }

    @Override
    public ClientType getClientType() {
        return clientType;
    }

    @Override
    public String toString() {
        return String.format("Nome: %-15s | Email: %-30s | CPF/CNPJ : %-15s | Tipo: %-10s", getName(), getEmail(), getId(), getClientType().getName());
    }
}
