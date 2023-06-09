package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    private DaoCliente daoCliente;

    public void inicializarComponentes() {
        campoKm = findViewById(R.id.idQuilometragem);
        botaoEnviarKm = findViewById(R.id.idEnviar);
    }

    public void updateKm(View view) {
        String txtKmString = campoKm.getText().toString();
        int txtkm = Integer.parseInt(txtKmString);

        // Obtenha o CPF do usuário logado
        String cpfUsuario = getIntent().getStringExtra("cpf");

        ModelCliente cliente = new ModelCliente();
        cliente.setCliCPF(cpfUsuario);  // Defina o CPF do usuário logado
        cliente.setCliKm(txtkm);  // Defina a nova quilometragem

        boolean resultado = daoCliente.updateKm(cliente);

        if (resultado) {
            Toast.makeText(this, "Quilometragem atualizada com sucesso", Toast.LENGTH_SHORT).show();

        }if (txtkm % 10000 == 0){
            exibirNotificacao("O carro atingiu 10.000Km", "Por favor levar na revisão!!!");
        }
        else {
            Toast.makeText(this, "Erro ao atualizar a quilometragem", Toast.LENGTH_SHORT).show();
        }

    }


    private void exibirNotificacao(String titulo, String conteudo) {
        // Cria um canal de notificação
        criarCanalNotificacao();

        // Cria a notificação
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "seu_canal_id")
                .setSmallIcon(R.drawable.logo_hot_wheels)
                .setContentTitle(titulo)
                .setContentText(conteudo)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // A permissão VIBRATE não foi concedida pelo usuário
            // Você pode solicitar a permissão aqui ou tratar o caso de permissão negada de outra forma
            return;
        }
        String txtKmString = campoKm.getText().toString();
        int txtkm = Integer.parseInt(txtKmString);

            // Obtém o NotificationManagerCompat
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            // Exibe a notificação
            int notificationId = 1; // ID único para a notificação
            notificationManager.notify(notificationId, builder.build());
        }


    private void criarCanalNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "seu_canal_id";
            CharSequence channelName = "Seu Canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_logado);
        inicializarComponentes();

        daoCliente = new DaoCliente(this);

        botaoEnviarKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateKm(v);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Lidar com a situação em que a permissão não está concedida
            // Solicitar permissão ao usuário ou realizar alguma ação alternativa
            // ...
            return;
        }
    }
}
