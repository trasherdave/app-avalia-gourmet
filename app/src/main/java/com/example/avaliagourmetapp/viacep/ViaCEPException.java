package com.example.avaliagourmetapp.viacep;


public class ViaCEPException extends Exception {
    private String CEP;


    //Gera uma nova exceção
    public ViaCEPException(String message) {
        super(message);

        this.CEP = "";
    }


    //Gera uma nova exceção e define o CEP que foi solicitado
    public ViaCEPException(String message, String cep) {
        super(message);

        this.CEP = cep;
    }


    //Define o CEP da exceção
    public void setCEP(String cep) {
        this.CEP = cep;
    }


    //Retorna o CEP da exceção
    public String getCEP() {
        return this.CEP;
    }


    //Retorna se tem algum CEP
    public boolean hasCEP() {
        return !this.CEP.isEmpty();
    }
}
