package com.example.mobileapp.Actvities.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        //passar String key para inserir nos campos na tabela clientes
        //key declarada ao cria a tabela cliente: campos da tabela clientes


        clientes.put("cliCPF", mCliente.getCliCPF());
        clientes.put("cliCNH", mCliente.getCliCNH());
        clientes.put("cliNome", mCliente.getCliNome());
        clientes.put("cliCelular", mCliente.getCliCelular());
        clientes.put("cliPlaca", mCliente.getCliPlaca());
        clientes.put("cliModelo", mCliente.getCliModelo());
        clientes.put("cliKm", mCliente.getClikm());

        //lanchar um try caso return o false lance uma excption para tratar o erro
        try {
            //gravar na tabela clientes
            //passando no paramentro o nome da tabela e os valores
            sqlEscrever.insert(SQLite.TABELA_CLIENTE, null, clientes);
            //sqlEscrever.insert(SQLite.TABELA_CARRO,null, carros);
            //retorna verdadeiro caso a inserir seja realizado com sucesso
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //metodo dao excluir cliente
    @Override
    public boolean deleteCliente(ModelCliente mCliente) {
        try {
            String[] cpf = {mCliente.getCliCPF().toString()};
            sqlEscrever.delete(SQLite.TABELA_CLIENTE, "cliCPF = ?", cpf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //metodo dao lista cliente
    @Override
    public List<ModelCliente> listarCliente() {
        //declarar variaveis para realizar a operação de listar
        //Vamos precisar:
        List<ModelCliente> list = new ArrayList<>();
        //vamos para o camando sqlite e atributo a uma strindo
        String sqlSelect = "select *from " + SQLite.TABELA_CLIENTE + ";";
        //String sqlSelect2 = "select *from "+ SQLite.TABELA_CARRO+";";
        Cursor cursor = sqlLeitura.rawQuery(sqlSelect, null);
        while (cursor.moveToNext()) {
            ModelCliente dados = new ModelCliente();
            //declarar variaves para receber os atributos da classe cliente
            String txtNome, txtCPF, txtCNH, txtCelular, txtModelo, txtPlaca;
            int txtKm;

            txtNome = cursor.getString(cursor.getColumnIndexOrThrow("cliNome"));
            txtCPF = cursor.getString(cursor.getColumnIndexOrThrow("cliCPF"));
            txtCNH = cursor.getString(cursor.getColumnIndexOrThrow("cliCNH"));
            txtCelular = cursor.getString(cursor.getColumnIndexOrThrow("cliCelular"));

            txtModelo = cursor.getString(cursor.getColumnIndexOrThrow("cliModelo"));
            txtPlaca = cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca"));
            txtKm = cursor.getInt(cursor.getColumnIndexOrThrow("cliKm"));

            dados.setCliNome(txtNome);
            dados.setCliCPF(txtCPF);
            dados.setCliCNH(txtCNH);
            dados.setCliCelular(txtCelular);

            dados.setCliModelo(txtModelo);
            dados.setCliPlaca(txtPlaca);
            dados.setCliKm(txtKm);

            list.add(dados);
        }
        return list;
    }

    public boolean checkLogin(String cpf, String cnh) {
        String query = "SELECT * FROM " + SQLite.TABELA_CLIENTE + " WHERE cliCPF = ? AND cliCNH = ?";
        Cursor cursor = sqlLeitura.rawQuery(query, new String[]{cpf, cnh});

        boolean loginSuccessful = cursor.getCount() > 0;

        cursor.close();
        return loginSuccessful;
    }
    public boolean updateKm(ModelCliente mCliente) {
        ContentValues valores = new ContentValues();
        String[] whereArgs = {mCliente.getCliCPF()};

        valores.put("cliKm", mCliente.getClikm());

        try {
            sqlEscrever.update(SQLite.TABELA_CLIENTE, valores, "cliCPF = ?", whereArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public ModelCliente obterModeloPlaca(String cpf) {
        String query = "SELECT cliModelo, cliPlaca FROM " + SQLite.TABELA_CLIENTE + " WHERE cliCPF = ?";
        String[] selectionArgs = { cpf };
        Cursor cursor = sqlLeitura.rawQuery(query, selectionArgs);

        ModelCliente cliente = null;
        if (cursor.moveToFirst()) {
            cliente = new ModelCliente();
            cliente.setCliModelo(cursor.getString(cursor.getColumnIndexOrThrow("cliModelo")));
            cliente.setCliPlaca(cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca")));
        }

        cursor.close();
        return cliente;
    }
    public ModelCliente calculoAviso(String placa){
        String query = "SELECT cliKmInicial FROM " + SQLite.TABELA_CLIENTE + " WHERE cliPlaca = ?";
        String[] selectionArgs = {placa};
        Cursor cursor = sqlLeitura.rawQuery(query, selectionArgs);

        ModelCliente cliente = null;
        if (cursor.moveToFirst()) {
            cliente = new ModelCliente();
            cliente.setCliKm(cursor.getInt(cursor.getColumnIndexOrThrow("cliKmInicial")));
        }
        cursor.close();
        return cliente;
    }

}

