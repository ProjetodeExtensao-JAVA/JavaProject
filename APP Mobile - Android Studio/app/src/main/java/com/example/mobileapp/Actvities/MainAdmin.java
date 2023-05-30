package com.example.mobileapp.Actvities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.mobileapp.Actvities.DataBase.Connect;
import com.example.mobileapp.Fragment.CadastroFragment;
import com.example.mobileapp.Fragment.FotosFragment;
import com.example.mobileapp.Fragment.HomeFragment;
import com.example.mobileapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
//teste
public class MainAdmin extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    private SQLiteDatabase bancoDados;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_screen);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        loadFragment(new HomeFragment());
        criarBancoDados();
        Connect connect = new Connect();
        String nome = resultSet.getString("nome");
    }
    public void criarBancoDados(){
        try {
            //bancoDados = openOrCreateDatabase("/DataBase/database.db", MODE_PRIVATE, null);
            //bancoDados.execSQL("CREATE TABLE IF NOT EXISTS veiculos("+" id INTEGER PRIMARY KEY AUTOINCREMENT" +", modelo VARCHAR,placa VARCHAR, cor VARCHAR, quilometragem FLOAT)");
            //bancoDados.execSQL("CREATE TABLE IF NOT EXISTS motorista("+" id INTEGER PRIMARY KEY AUTOINCREMENT" +", nome VARCHAR,cnh VARCHAR, cpf VARCHAR, endereco TEXT)");
            //bancoDados.close();
            Connect connect = (Connect) DriverManager.getConnection("java/com/example/mobileapp/Actvities/DataBase/database.sqbpro");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.mihome:
                fragment = new HomeFragment();
                break;
            case R.id.mifotos:
                fragment = new FotosFragment();
                break;
            case R.id.micadastro:
                fragment = new CadastroFragment();
                break;
        }
        if (fragment != null){
            loadFragment(fragment);
        }
        return true;
    }
    void loadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, fragment).commit();
    }
}
