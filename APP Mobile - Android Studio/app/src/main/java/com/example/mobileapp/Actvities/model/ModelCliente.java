package com.example.mobileapp.Actvities.model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ModelCliente implements Serializable {
    //declaração de atributos da class cliente
    private String cliModelo;
    private String cliPlaca;
    private int  cliKm;
    private String cliNome;
    private String cliCPF;
    private String cliCNH;
    private String cliCelular;
    private byte[] cliFotoUm;
    private byte[] cliFotoDois;
    private byte[] cliFotoTres;
    private byte[] cliFotoQuatro;

    //metodo construtor
    public ModelCliente() {
    }
    //get e set
    public String getCliModelo() {return cliModelo;}
    public void setCliModelo(String cliModelo) {this.cliModelo = cliModelo;}

    public String getCliPlaca() {return cliPlaca;}
    public void setCliPlaca(String cliPlaca) {this.cliPlaca = cliPlaca;}

    public int getClikm() {return cliKm;}

    public void setCliKm(int cliKm) {this.cliKm = cliKm;}

    public String getCliNome() {
        return cliNome;
    }

    public void setCliNome(String cliNome) {
        this.cliNome = cliNome;
    }

    public String getCliCPF() {return cliCPF;}

    public void setCliCPF(String cliCPF) {this.cliCPF = cliCPF;}

    public String getCliCNH() {return cliCNH;}
    public void setCliCNH(String cliCNH) {this.cliCNH = cliCNH;}

    public String getCliCelular() {
        return cliCelular;
    }

    public void setCliCelular(String cliCelular) {
        this.cliCelular = cliCelular;
    }

    public byte[] getCliFotoUm() {
        return cliFotoUm;
    }

    public void setCliFotoUm(byte[] cliFotoUm) {
        this.cliFotoUm = cliFotoUm;
    }

    public byte[] getCliFotoDois() {
        return cliFotoDois;
    }

    public void setCliFotoDois(byte[] cliFotoDois) {
        this.cliFotoDois = cliFotoDois;
    }

    public byte[] getCliFotoTres() {
        return cliFotoTres;
    }

    public void setCliFotoTres(byte[] cliFotoTres) {
        this.cliFotoTres = cliFotoTres;
    }

    public byte[] getCliFotoQuatro() {
        return cliFotoQuatro;
    }

    public void setCliFotoQuatro(byte[] cliFotoQuatro) {
        this.cliFotoQuatro = cliFotoQuatro;
    }

    @Override
    public String toString() {
        return "ModelCliente{" +
                ", cliModelo='" + cliModelo + '\'' +
                ", cliPlaca='" + cliPlaca + '\'' +
                ", cliKm='" + cliKm + '\'' +
                ", cliNome='" + cliNome + '\'' +
                ", cliCPF='" + cliCPF + '\'' +
                ", cliCNH='" + cliCNH + '\'' +
                ", cliCelular='" + cliCelular + '\'' +
                ", cliFotoUm='" + cliFotoUm + '\'' +
                ", cliFotoDois='" + cliFotoDois + '\'' +
                ", cliFotoTres='" + cliFotoTres + '\'' +
                ", cliFotoQuatro='" + cliFotoQuatro + '\'' +
                '}';
    }
}
