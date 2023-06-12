package com.example.mobileapp.Actvities.telas;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class usuario_logado extends AppCompatActivity {

    private EditText campoKm;
    private Button botaoEnviarKm;
    private Button btnCarregarFoto;
    private DaoCliente daoCliente;
    private ImageView receberFoto;
    private List<Uri> listaUrisSelecionadas = new ArrayList<>();
    private ActivityResultLauncher<Intent> someActivityResultLauncher;

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
            // Lidar com a situação em que a permissão não está concedida
            // Solicitar permissão ao usuário ou realizar alguma ação alternativa
            // ...
            return;
        }

        receberFoto = findViewById(R.id.receberFoto);
        btnCarregarFoto = findViewById(R.id.btnCarregarFoto);

        btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    String[] permissao = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    requestPermissions(permissao, 1001);
                } else {
                    escolherImagens();
                }
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            if (data.getClipData() != null) {
                                int count = data.getClipData().getItemCount();
                                for (int i = 0; i < count; i++) {
                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                    listaUrisSelecionadas.add(imageUri);
                                }
                                exibirImagensSelecionadas();
                            } else if (data.getData() != null) {
                                Uri imageUri = data.getData();
                                listaUrisSelecionadas.add(imageUri);
                                exibirImagensSelecionadas();
                            }
                        }
                    }
                }
        );
    }

    public void inicializarComponentes() {
        campoKm = findViewById(R.id.idQuilometragem);
        botaoEnviarKm = findViewById(R.id.idEnviar);
    }

    private void obterModeloPlaca(String cpf) {
        ModelCliente cliente = daoCliente.obterModeloPlaca(cpf);
        if (cliente != null) {
            // Exiba o modelo e a placa nos campos de texto correspondentes
            TextView modeloCarro = findViewById(R.id.modeloCarro);
            TextView placaCarro = findViewById(R.id.placaCarro);
            modeloCarro.setText(cliente.getCliModelo());
            placaCarro.setText(cliente.getCliPlaca());
        } else {
            // Não foi possível obter as informações do modelo e placa
            Toast.makeText(this, "Erro ao obter informações do carro", Toast.LENGTH_SHORT).show();
        }
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
        } else {
            Toast.makeText(this, "Erro ao atualizar a quilometragem", Toast.LENGTH_SHORT).show();
        }

        if (txtkm % 10000 == 0) {
            exibirNotificacao("O carro atingiu 10.000Km", "Por favor levar na revisão!!!");
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

    private void escolherImagens() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        someActivityResultLauncher.launch(intent);
    }

    private void exibirImagensSelecionadas() {
        if (!listaUrisSelecionadas.isEmpty()) {
            LinearLayout linearLayout = findViewById(R.id.linearLayoutImagens); // ID do LinearLayout que irá conter as imagens

            linearLayout.removeAllViews(); // Remove as visualizações anteriores se houver

            for (Uri imageUri : listaUrisSelecionadas) {
                ImageView imageView = new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                imageView.setImageURI(imageUri);

                linearLayout.addView(imageView); // Adiciona a ImageView ao LinearLayout
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    escolherImagens();
                } else {
                    Toast.makeText(this, "Permissão negada!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
