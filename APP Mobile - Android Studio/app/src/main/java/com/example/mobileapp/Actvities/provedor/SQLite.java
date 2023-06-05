package com.example.mobileapp.Actvities.provedor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {
    //declaração de variaveis static
    public  static final String DATABASE = "usuario.db";
    //declara de variavis static tabela
    public static final String TABELA_CLIENTE = "tb_usuario2";
    //METODO CONSTRUTOR DA CLASSE SQLITE;
    public SQLite(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //criando tabela cliente
        String sqlClinte = "create table if not exists " + TABELA_CLIENTE +
                "(cliCPF text primary key," +
                "cliCNH text not null," +
                "cliNome text not null," +
                "cliCelular text not null," +
                "cliPlaca text not null," +
                "cliModelo text not null," +
                "cliKm int not null)";
        try {
            //executar comando sqlite do cliente
            db.execSQL(sqlClinte);
        }catch (Exception erro){
            erro.printStackTrace();
            Log.i("Erro","Banco de dados: ");
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE); // Drop the existing table
        onCreate(db); // Create the table again
    }

}
