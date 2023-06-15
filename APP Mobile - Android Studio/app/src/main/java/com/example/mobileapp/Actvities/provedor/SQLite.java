package com.example.mobileapp.Actvities.provedor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper {
    // Declaração de variáveis estáticas
    public static final String DATABASE = "usuario.db";
    // Declaração de variáveis estáticas tabela
    public static final String TABELA_CLIENTE = "tb_usuario2";
    public static final String TABELA_FOTOS = "tb_fotos";

    // Método construtor da classe SQLite
    public SQLite(Context context) {
        super(context, DATABASE, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando tabela cliente
        String sqlCliente = "CREATE TABLE IF NOT EXISTS " + TABELA_CLIENTE +
                "(cliCPF TEXT PRIMARY KEY," +
                "cliCNH TEXT NOT NULL," +
                "cliNome TEXT NOT NULL," +
                "cliCelular TEXT NOT NULL," +
                "cliPlaca TEXT NOT NULL," +
                "cliModelo TEXT NOT NULL," +
                "cliKm INT NOT NULL)";

        // Criando tabela de fotos
        String sqlFotos = "CREATE TABLE IF NOT EXISTS " + TABELA_FOTOS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "cliCPF TEXT," +
                "cliFotoUm blob NOT NULL," +
                "cliFotoDois blob NOT NULL," +
                "cliFotoTres blob NOT NULL," +
                "cliFotoQuatro blob NOT NULL," +
                "FOREIGN KEY(cliCPF) REFERENCES " + TABELA_CLIENTE + "(cliCPF))";

        try {
            // Executar comando SQL da tabela cliente
            db.execSQL(sqlCliente);

            // Executar comando SQL da tabela de fotos
            db.execSQL(sqlFotos);
        } catch (Exception erro) {
            erro.printStackTrace();
            Log.i("Erro", "Banco de dados: ");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_FOTOS); // Drop the existing table
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_CLIENTE); // Drop the existing table
        onCreate(db); // Create the tables again
    }
}