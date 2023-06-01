package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.mobileapp.R;

public class MainActivity extends AppCompatActivity {
    //Classe Java Principal do App
    //declaração de camponentes do tipo linearLayout
    private LinearLayout linearCadastro;
    private LinearLayout linearListar;
    private LinearLayout linearAlterar;
    private void inicializarComponentes(){
        linearCadastro = (LinearLayout)findViewById(R.id.idLinearLayout01);
        linearListar = (LinearLayout)findViewById(R.id.idLinearLayout02);
        linearAlterar = (LinearLayout)findViewById(R.id.idLinearLayout03);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //iniciar componentes
        inicializarComponentes();
        //evento de clique adicionar
        linearCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ClientesActivity.class));
            }
        });
        //evento de clique encaminha para Lista
        linearListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListarClienteActivity.class));
            }
        });
        linearAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListarClienteActivity.class));
            }
        });
    }
}