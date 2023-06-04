package com.example.mobileapp.Actvities.model;

import java.io.Serializable;

public class ModelCliente implements Serializable {
    //declaração de atributos da class cliente
    private String cliModelo;
    private String cliPlaca;
    private String cliQuilometragem;
    private String cliNome;
    private String cliCPF;
    private String cliCelular;
    //metodo construtor
    public ModelCliente() {
    }
    //get e set
    public String getCliModelo() {return cliModelo;}
    public void setCliModelo(String cliModelo) {this.cliModelo = cliModelo;}

    public String getCliPlaca() {return cliPlaca;}
    public void setCliPlaca(String cliPlaca) {this.cliPlaca = cliPlaca;}

    public String getCliQuilometragem() {return cliQuilometragem;}

    public void setCliQuilometragem(String cliQuilometragem) {this.cliQuilometragem = cliQuilometragem;}

    public String getCliNome() {return cliNome;}

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    public String getCliCPF() {return cliCPF;}

    public void setCliCPF(String cliCPF) {this.cliCPF = cliCPF;}

    public String getCliCelular() {
        return cliCelular;
    }

    public void setCliCelular(String cliCelular) {
        this.cliCelular = cliCelular;
    }

    @Override
    public String toString() {
        return "ModelCliente{" +
                ", cliModelo='" + cliModelo + '\'' +
                ", cliPlaca='" + cliPlaca + '\'' +
                ", cliQuilometragem='" + cliQuilometragem + '\'' +
                ", cliNome='" + cliNome + '\'' +
                ", cliCPF='" + cliCPF + '\'' +
                ", cliCelular='" + cliCelular + '\'' +
                '}';
    }
}
