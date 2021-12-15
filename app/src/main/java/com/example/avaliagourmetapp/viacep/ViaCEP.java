package com.example.avaliagourmetapp.viacep;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


//Classe java para obter um CEP no ViaCEP
public class ViaCEP {

    private String CEP;
    private String Logradouro;
    private String Bairro;
    private String Localidade;
    private String Uf;


    //Constrói uma nova classe
    public ViaCEP() {
        this.CEP = null;
        this.Logradouro = null;
        this.Bairro = null;
        this.Localidade = null;
        this.Uf = null;

    }


    //Constrói uma nova classe e busca um CEP no ViaCEP
    public ViaCEP(String cep) throws ViaCEPException, JSONException {
        this.buscar(cep);
    }


    //Busca um CEP no ViaCEP
    public final void buscar(String cep) throws ViaCEPException, JSONException {
        this.CEP = cep;

        // define a url
        String url = "http://viacep.com.br/ws/" + cep + "/json/";

        // define os dados
        JSONObject obj = new JSONObject(this.get(url));

        if (!obj.has("erro")) {
            this.CEP = obj.getString("cep");
            this.Logradouro = obj.getString("logradouro");
            this.Bairro = obj.getString("bairro");
            this.Localidade = obj.getString("localidade");
            this.Uf = obj.getString("uf");
        } else {
            throw new ViaCEPException("Não foi possível encontrar o CEP", cep);
        }
    }


    public String getCep() {
        return this.CEP;
    }

    public String getLogradouro() {
        return this.Logradouro;
    }


    public String getBairro() {
        return this.Bairro;
    }


    public String getLocalidade() {
        return this.Localidade;
    }

    public String getUf() {
        return this.Uf;
    }


    //Procedimento para obtem dados via GET
    public final String get(String urlParaLeitura) throws ViaCEPException {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlParaLeitura);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }

        } catch (MalformedURLException | ProtocolException ex) {
            throw new ViaCEPException(ex.getMessage());
        } catch (IOException ex) {
            throw new ViaCEPException(ex.getMessage());
        }

        return result.toString();
    }
}
