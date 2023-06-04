package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mobileapp.R;
import com.example.mobileapp.Actvities.model.ModelCliente;

public class ClienteDetalhesActivity extends AppCompatActivity {
    //Classe java p/ exibir detalhes do cliente
    private TextView txtCliNome, txtCliCelular, txtCliCPF, txtCliEndereco, txtCliModelo, txtCliPlaca, txtCliQuilometragem;
    private ModelCliente detalhesClientes = new ModelCliente();
    /*Componentes Inicialização*/
    public void inicializarComponentes(){
        txtCliNome = (TextView)findViewById(R.id.idNome);
        txtCliCelular = (TextView)findViewById(R.id.idCelular);
        txtCliCPF = (TextView)findViewById(R.id.idCPF);
        txtCliPlaca = (TextView)findViewById(R.id.idPlaca);
        txtCliQuilometragem = (TextView)findViewById(R.id.idQuilometragem);
        txtCliModelo = (TextView)findViewById(R.id.idModelo);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalhes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inicializar Componentes
        inicializarComponentes();
        detalhesClientes = (ModelCliente)getIntent().getSerializableExtra("keyDetalhes");
        if (detalhesClientes!=null) {
            txtCliNome.setText("Nome: " + detalhesClientes.getCliNome());
            txtCliCelular.setText("Celular: " + detalhesClientes.getCliCelular());
            txtCliCPF.setText("Endereço: " + detalhesClientes.getCliCPF());
        }else{
            Toast.makeText(ClienteDetalhesActivity.this,
                    "Vazio",Toast.LENGTH_LONG).show();
        }

    }
}