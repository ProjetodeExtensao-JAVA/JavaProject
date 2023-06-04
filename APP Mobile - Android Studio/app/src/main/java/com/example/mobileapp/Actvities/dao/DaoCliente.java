package com.example.mobileapp.Actvities.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.mobileapp.Actvities.metodo.MetodoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.Actvities.provedor.SQLite;

public class DaoCliente implements MetodoCliente {
    //declaração de variaveis para conectar ao banco de dados sqlite
    SQLiteDatabase sqlEscrever;
    SQLiteDatabase sqlLeitura;
    //metodo construtor da classe DaoCliente
    public DaoCliente(Context context) {
        SQLite base = new SQLite(context);
        //inserir dados da tabela
        sqlEscrever = base.getWritableDatabase();
        //realizar leitura na base de dados
        sqlLeitura = base.getReadableDatabase();
    }
    //metodo dao cadastro de cliente
    @Override
    public boolean cadastroCliente(ModelCliente mCliente) {
        //Classe Instancia para inserir dados na base de dados
        ContentValues clientes = new ContentValues();
        clientes.put("cliNome", mCliente.getCliNome());
        clientes.put("cliCPF", mCliente.getCliCPF());
        clientes.put("cliPlaca", mCliente.getCliPlaca());
        clientes.put("cliCelular", mCliente.getCliCelular());
        clientes.put("cliModelo", mCliente.getCliModelo());
        clientes.put("cliQuilometragem", mCliente.getCliQuilometragem());
        //lanchar um try caso return o false lance uma excption para tratar o erro
        try {
            //gravar na tabela clientes
            //passando no paramentro o nome da tabela e os valores
            sqlEscrever.insert(SQLite.TABELA_CLIENTE, null, clientes);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //metodo dao excluir cliente
    @Override
    public boolean deleteCliente(ModelCliente mCliente) {
        try {
            String[] cpf = {mCliente.getCliCPF().toString()};
            sqlEscrever.delete(SQLite.TABELA_CLIENTE,"cliPlaca = ?", cpf);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    //metodo dao lista cliente
    @Override
    public List<ModelCliente> listarCliente() {
        //declarar variaveis para realizar a operação de listar
        //Vamos precisar:
        List<ModelCliente> lista = new ArrayList<>();
        //vamos para o camando sqlite e atributo a uma strindo
        String sqlSelect = "select *from "+ SQLite.TABELA_CLIENTE+";";
        Cursor cursor = sqlLeitura.rawQuery(sqlSelect, null);
        while (cursor.moveToNext()){
            ModelCliente dados = new ModelCliente();
            //declarar variaves para receber os atributos da classe cliente
            String txtNome,txtCPF,txtModelo, txtCelular, txtQuilometragem, txtPlaca;
            txtPlaca = cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca"));
            txtModelo = cursor.getString(cursor.getColumnIndexOrThrow("cliModelo"));
            txtQuilometragem = cursor.getString(cursor.getColumnIndexOrThrow("cliQuilometragem"));
            txtNome = cursor.getString(cursor.getColumnIndexOrThrow("cliNome"));
            txtCPF = cursor.getString(cursor.getColumnIndexOrThrow("cliCPF"));
            txtCelular = cursor.getString(cursor.getColumnIndexOrThrow("cliCelular"));


            dados.setCliPlaca(txtPlaca);
            dados.setCliModelo(txtModelo);
            dados.setCliQuilometragem(txtQuilometragem);
            dados.setCliNome(txtNome);
            dados.setCliCPF(txtCPF);
            dados.setCliCelular(txtCelular);

            lista.add(dados);
        }
        return lista;
    }
}
