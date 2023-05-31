package br.com.appcadastroclientes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.appcadastroclientes.metodo.MetodoCliente;
import br.com.appcadastroclientes.model.ModelCliente;
import br.com.appcadastroclientes.provedor.SQLite;

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
            txtModelo = cursor.getString(cursor.getColumnIndex("cliModelo"));
            txtPlaca = cursor.getString(cursor.getColumnIndex("cliPlaca"));
            txtCor = cursor.getString(cursor.getColumnIndex("cliCor"));
            txtQuilometragem = cursor.getString(cursor.getColumnIndex("cliQuilometragem"));

            txtNome = cursor.getString(cursor.getColumnIndex("cliNome"));
            txtCPF = cursor.getString(cursor.getColumnIndex("cliCPF"));
            txtCNH = cursor.getString(cursor.getColumnIndex("cliCNH"));
            txtCelular = cursor.getString(cursor.getColumnIndex("cliCelular"));
            txtEndereco = cursor.getString(cursor.getColumnIndex("cliEndereco"));

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
