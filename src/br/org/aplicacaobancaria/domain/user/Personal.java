package br.org.aplicacaobancaria.domain.user;

public class Personal extends Client{


    public Personal(String name, String email, String id, ClientType clientType) {
        super(name, email, id, clientType);
        clientType = ClientType.PERSONAL;
    }

    @Override
    public String toString() {
        return String.format("Nome: %-15s | Email: %-30s | CPF/CNPJ : %-15s | Tipo: %-10s", getName(), getEmail(), getId(), getClientType().getAbbreviation());
    }
}
