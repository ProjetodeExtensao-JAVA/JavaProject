package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

public class usuario_logado extends AppCompatActivity {

    private EditText campoKm;

    private Button botaoEnviarKm;

    private DaoCliente daoCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logado);

        campoKm = (EditText)findViewById(R.id.idQuilometragem);
        botaoEnviarKm = (Button)findViewById(R.id.idEnviar);
        daoCliente = new DaoCliente(this);

        botaoEnviarKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtKmString = campoKm.getText().toString();
                int txtkm = Integer.parseInt(txtKmString);

                DaoCliente escreverCliente = new DaoCliente(getBaseContext());

                ModelCliente setCliente = new ModelCliente();

                setCliente.setCliKm(txtkm);
                boolean resultado = daoCliente.updateKm(setCliente);

            }
        });

    }
    private boolean updateKm(txtkm) {
        String cpf = ""; // Insira o CPF do cliente aqui

        return daoCliente.updateKm(txtkm);
    }
//    private boolean updateKm(int txtkm){
//        return  daoCliente.updateKm(txtkm);
//    }
}