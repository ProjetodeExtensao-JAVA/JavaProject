package br.com.appcadastroclientes.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.appcadastroclientes.R;

public class SobreActivity extends AppCompatActivity {
    private TextView descricao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        descricao = (TextView)findViewById(R.id.idDescricaoApp);
        descricao.setText("Aplicativo desenvolvido para o projeto de extensão java na UNIRUY," +
                "aplicando banco de dados SQLite. Utilizando a IDE oficial do Google Android Studio." +
                "Com o objetivo de auxiliar uma locadora de veículos com o controle da frota."+
                "\n"+"Nome App: Alan locadora"+
                "\n"+"Modelo: Android"+
                "\n"+"Version: 1.0");
    }
}