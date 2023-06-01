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
        ContentValues carros = new ContentValues();
        //passar String key para inserir nos campos na tabela clientes
        //key declarada ao cria a tabela cliente: campos da tabela clientes
        carros.put("cliModelo", mCliente.getCliModelo());
        carros.put("cliPlaca", mCliente.getCliPlaca());
        carros.put("cliCor", mCliente.getCliCor());
        carros.put("cliQuilometragem", mCliente.getCliQuilometragem());

        clientes.put("cliNome", mCliente.getCliNome());
        clientes.put("cliCPF", mCliente.getCliCPF());
        clientes.put("cliCNH", mCliente.getCliCNH());
        clientes.put("cliCelular", mCliente.getCliCelular());
        clientes.put("cliEndereco", mCliente.getCliEndereco());
        //lanchar um try caso return o false lance uma excption para tratar o erro
        try {
            //gravar na tabela clientes
            //passando no paramentro o nome da tabela e os valores
            sqlEscrever.insert(SQLite.TABELA_CLIENTE, null, clientes);
            sqlEscrever.insert(SQLite.TABELA_CARRO,null, carros);
            //retorna verdadeiro caso a inserir seja realizado com sucesso
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
            String[] placa  = {mCliente.getCliPlaca().toString()};
            sqlEscrever.delete(SQLite.TABELA_CLIENTE,"cliCPF = ?", cpf);
            sqlEscrever.delete(SQLite.TABELA_CARRO,"cliPlaca = ?", placa);
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
        List<ModelCliente> list = new ArrayList<>();
        //vamos para o camando sqlite e atributo a uma strindo
        String sqlSelect = "select *from "+ SQLite.TABELA_CLIENTE + SQLite.TABELA_CARRO+";";
        Cursor cursor = sqlLeitura.rawQuery(sqlSelect, null);

        //iniciar um while para percorrer os campos da tabela
        while (cursor.moveToNext()){
            ModelCliente dados = new ModelCliente();

            //declarar variaves para receber os atributos da classe cliente
            String txtModelo, txtPlaca, txtCor, txtQuilometragem,
                    txtNome,txtCPF,txtCNH, txtCelular, txtEndereco;
            txtModelo = cursor.getString(cursor.getColumnIndexOrThrow("cliModelo"));
            txtPlaca = cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca"));
            txtCor = cursor.getString(cursor.getColumnIndexOrThrow("cliCor"));
            txtQuilometragem = cursor.getString(cursor.getColumnIndexOrThrow("cliQuilometragem"));

            txtNome = cursor.getString(cursor.getColumnIndexOrThrow("cliNome"));
            txtCPF = cursor.getString(cursor.getColumnIndexOrThrow("cliCPF"));
            txtCNH = cursor.getString(cursor.getColumnIndexOrThrow("cliCNH"));
            txtCelular = cursor.getString(cursor.getColumnIndexOrThrow("cliCelular"));
            txtEndereco = cursor.getString(cursor.getColumnIndexOrThrow("cliEndereco"));

            dados.setCliModelo(txtModelo);
            dados.setCliPlaca(txtPlaca);
            dados.setCliCor(txtCor);
            dados.setCliQuilometragem(txtQuilometragem);

            dados.setCliNome(txtNome);
            dados.setCliCPF(txtCPF);
            dados.setCliCNH(txtCNH);
            dados.setCliCelular(txtCelular);
            dados.setCliEndereco(txtEndereco);

            list.add(dados);
        }
        return list;
    }
}
