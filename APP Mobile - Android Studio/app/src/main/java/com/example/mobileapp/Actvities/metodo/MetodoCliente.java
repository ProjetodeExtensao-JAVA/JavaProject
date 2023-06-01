package com.example.mobileapp.Actvities.metodo;

import java.util.List;

import com.example.mobileapp.Actvities.model.ModelCliente;

public interface MetodoCliente {
    //metodos que serao implementados de forma automatica no DaoCliente
    //Assim que a classe for implmentada
    public boolean cadastroCliente(ModelCliente mCliente);
    public boolean deleteCliente(ModelCliente mCliente);
    public List<ModelCliente> listarCliente();
}
