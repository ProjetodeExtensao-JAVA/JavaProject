package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

public class usuario_logado extends AppCompatActivity {

    private EditText campoKm;
    private Button botaoEnviarKm;
    private DaoCliente daoCliente;

    public void inicializarComponentes() {
        campoKm = findViewById(R.id.idQuilometragem);
        botaoEnviarKm = findViewById(R.id.idEnviar);
    }

    private void obterModeloPlaca(String cpf) {
        ModelCliente cliente = daoCliente.obterModeloPlaca(cpf);
        if (cliente != null) {
            // Exibe o modelo e a placa nos campos de texto correspondentes
            TextView modeloCarro = findViewById(R.id.modeloCarro);
            TextView placaCarro = findViewById(R.id.placaCarro);
            modeloCarro.setText(cliente.getCliModelo());
            placaCarro.setText(cliente.getCliPlaca());
        } else {
            // Não foi possível obter as informações do modelo e placa
            Toast.makeText(this, "Erro ao obter informações do carro", Toast.LENGTH_SHORT).show();
        }
    }

    private void calculoAviso(String cpf) {
        ModelCliente cliente = daoCliente.obterModeloPlaca(cpf);
        if (cliente != null) {
            int kmInicial = cliente.getClikm();
            String txtKmString = campoKm.getText().toString();
            int kmAtual = Integer.parseInt(txtKmString);

            int diferenca = kmAtual - kmInicial;

            if (diferenca >= 10000) {
                // Enviar notificação para o Admin
                enviarNotificacaoAdmin("Realizar revisão do carro");
                // Alterar a cor do layout
                alterarCorLayout();
            }
        } else {
            // Não foi possível obter as informações do cliente
            Toast.makeText(this, "Erro ao obter informações do cliente", Toast.LENGTH_SHORT).show();
        }
    }

    private void alterarCorLayout() {
        int novaCor = Color.GREEN; // Defina a cor desejada aqui
        // Altere o código conforme necessário para alterar a cor do layout
    }

    public void updateKm(View view) {
        String txtKmString = campoKm.getText().toString();
        int txtKm = Integer.parseInt(txtKmString);

        // Obtenha o CPF do usuário logado
        String cpfUsuario = getIntent().getStringExtra("cpf");

        ModelCliente cliente = new ModelCliente();
        cliente.setCliCPF(cpfUsuario);  // Define o CPF do usuário logado
        cliente.setCliKm(txtKm);  // Define a nova quilometragem

        boolean resultado = daoCliente.updateKm(cliente);

        if (resultado) {
            Toast.makeText(this, "Quilometragem atualizada com sucesso", Toast.LENGTH_SHORT).show();
            calculoAviso(cpfUsuario); // Chama o método para calcular o aviso
        } else {
            Toast.makeText(this, "Erro ao atualizar a quilometragem", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logado);
        inicializarComponentes();

        daoCliente = new DaoCliente(this);
        String cpfUsuario = getIntent().getStringExtra("cpf");

        // Chama o método para obter o modelo e a placa do usuário logado
        obterModeloPlaca(cpfUsuario);

        botaoEnviarKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKm(v);
            }
        });
    }
}
