package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

public class usuario_logado extends AppCompatActivity {

    private EditText campoKm;

    private Button botaoEnviarKm;

    public void inicializarComponentes(){
        campoKm = findViewById(R.id.idQuilometragem);
        botaoEnviarKm = findViewById(R.id.idEnviar);
    }
    public void updateKm(View view) {
        String txtKmString = campoKm.getText().toString();
        int txtkm = Integer.parseInt(txtKmString);

        DaoCliente daoCliente = new DaoCliente(getBaseContext());

        // Obtenha o CPF do usuário logado
        String cpfUsuario = getIntent().getStringExtra("cpf");

        ModelCliente cliente = new ModelCliente();
        cliente.setCliCPF(cpfUsuario);  // Defina o CPF do usuário logado
        cliente.setCliKm(txtkm);  // Defina a nova quilometragem

        boolean resultado = daoCliente.updateKm(cliente);

        if (resultado) {
            Toast.makeText(this, "Quilometragem atualizada com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao atualizar a quilometragem", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logado);
        inicializarComponentes();
    }

}