package br.org.aplicacaobancaria.domain.user;

public class Business extends Client{
    private Double statedCapital;
    private final ClientType clientType;

    public Business(String name, String email, String id, Double statedCapital) {
        super(name, email, id);
        this.statedCapital = statedCapital;
        this.clientType = ClientType.BUSINESS;
    }

    public Double getStatedCapital() {
        return statedCapital;
    }

    public void setStatedCapital(Double statedCapital) {
        this.statedCapital = statedCapital;
    }

    public ClientType getClientType() {
        return clientType;
    }
}
