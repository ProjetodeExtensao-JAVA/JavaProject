package br.com.appcadastroclientes.model;

import java.io.Serializable;

public class ModelCliente implements Serializable {
    //declaração de atributos da class cliente
    private String cliModelo;
    private String cliPlaca;
    private String cliCor;
    private String cliQuilometragem;
    private String cliNome;
    private String cliCPF;
    private String cliCNH;
    private String cliCelular;
    private String cliEndereco;
    //metodo construtor
    public ModelCliente() {
    }
    //get e set
    public String getCliModelo() {return cliModelo;}
    public void setCliModelo(String cliModelo) {this.cliModelo = cliModelo;}

    public String getCliPlaca() {return cliPlaca;}
    public void setCliPlaca(String cliPlaca) {this.cliPlaca = cliPlaca;}

    public String getCliCor() {return cliCor;}

    public void setCliCor(String cliCor) {this.cliCor = cliCor;}

    public String getCliQuilometragem() {return cliQuilometragem;}

    public void setCliQuilometragem(String cliQuilometragem) {this.cliQuilometragem = cliQuilometragem;}

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

    public String getCliEndereco() {
        return cliEndereco;
    }

    public void setCliEndereco(String cliEndereco) {
        this.cliEndereco = cliEndereco;
    }

    @Override
    public String toString() {
        return "ModelCliente{" +
                ", cliModelo='" + cliModelo + '\'' +
                ", cliPlaca='" + cliPlaca + '\'' +
                ", cliCor='" + cliCor + '\'' +
                ", cliQuilometragem='" + cliQuilometragem + '\'' +
                ", cliNome='" + cliNome + '\'' +
                ", cliCPF='" + cliCPF + '\'' +
                ", cliCNH='" + cliCNH + '\'' +
                ", cliCelular='" + cliCelular + '\'' +
                ", cliEndereco='" + cliEndereco + '\'' +
                '}';
    }
}
