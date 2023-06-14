

package com.example.mobileapp.Actvities.telas;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Display;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    public class usuario_logado extends AppCompatActivity {
        private EditText campoKm;
        private Button botaoEnviarKm;
        private Button btnCarregarFoto;
        private DaoCliente daoCliente;
        private List<Uri> listaUrisSelecionadas = new ArrayList<>();
        private ActivityResultLauncher<Intent> someActivityResultLauncher;

        private ImageView imagemSuperior1;
        private ImageView imagemSuperior2;
        private ImageView imagemInferior1;
        private ImageView imagemInferior2;

        private byte[] imagemSuperior1Bytes;
        private byte[] imagemSuperior2Bytes;
        private byte[] imagemInferior1Bytes;
        private byte[] imagemInferior2Bytes;

        private static final int PERMISSION_REQUEST_CODE = 1001;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_usuario_logado);
            inicializarComponentes();
            limparCampos();

            daoCliente = new DaoCliente(this);
            String cpfUsuario = getIntent().getStringExtra("cpf");

            // Chama o método para obter o modelo e a placa do usuário logado
            obterModeloPlaca(cpfUsuario);

            botaoEnviarKm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateKm(v);
                    inserirFotosBanco(cpfUsuario);
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
                // Lidar com a situação em que a permissão não está concedida
                // Solicitar permissão ao usuário ou realizar alguma ação alternativa
                // ...
                return;
            }

            btnCarregarFoto = findViewById(R.id.btnCarregarFoto);

            btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(usuario_logado.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        String[] permissao = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissao, PERMISSION_REQUEST_CODE);
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
                                    int count = Math.min(data.getClipData().getItemCount(), 4); // Limite para 4 fotos
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

            imagemSuperior1 = findViewById(R.id.imagemSuperior1);
            imagemSuperior2 = findViewById(R.id.imagemSuperior2);
            imagemInferior1 = findViewById(R.id.imagemInferior1);
            imagemInferior2 = findViewById(R.id.imagemInferior2);
        }

        public void limparCampos() {
            imagemSuperior1.setImageDrawable(null);
            imagemSuperior2.setImageDrawable(null);
            imagemInferior1.setImageDrawable(null);
            imagemInferior2.setImageDrawable(null);
            campoKm.setText("0");
        }

        private void inserirFotosBanco(String cpfCliente) {
            if (imagemSuperior1Bytes != null && imagemSuperior2Bytes != null &&
                    imagemInferior1Bytes != null && imagemInferior2Bytes != null) {

                byte[] foto1 = imagemSuperior1Bytes;
                byte[] foto2 = imagemSuperior2Bytes;
                byte[] foto3 = imagemInferior1Bytes;
                byte[] foto4 = imagemInferior2Bytes;

                daoCliente.inserirFotosBanco(cpfCliente, foto1, foto2, foto3, foto4);

                Toast.makeText(this, "Fotos inseridas no banco de dados", Toast.LENGTH_SHORT).show();

                // Limpar as variáveis de armazenamento das imagens
                imagemSuperior1Bytes = null;
                imagemSuperior2Bytes = null;
                imagemInferior1Bytes = null;
                imagemInferior2Bytes = null;
            } else {
                Toast.makeText(this, "Selecione as 4 fotos antes de inserir no banco de dados", Toast.LENGTH_SHORT).show();
            }
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
            int txtKm = Integer.parseInt(txtKmString);

            // Obtenha o CPF do usuário logado
            String cpfUsuario = getIntent().getStringExtra("cpf");

            ModelCliente cliente = new ModelCliente();
            cliente.setCliCPF(cpfUsuario);  // Defina o CPF do usuário logado
            cliente.setCliKm(txtKm);  // Defina a nova quilometragem

            boolean resultado = daoCliente.updateKm(cliente);

            if (resultado) {
                Toast.makeText(this, "Quilometragem atualizada com sucesso", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao atualizar a quilometragem", Toast.LENGTH_SHORT).show();
            }

            if (txtKm % 10000 == 0) {
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

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.VIBRATE) != PackageManager.PERMISSION_GRANTED) {
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
                LinearLayout linearLayoutTop = findViewById(R.id.linearLayoutImagensTop);
                LinearLayout linearLayoutBottom = findViewById(R.id.linearLayoutImagensBottom);

                linearLayoutTop.removeAllViews();
                linearLayoutBottom.removeAllViews();

                int maxImages = Math.min(listaUrisSelecionadas.size(), 4);

                for (int i = 0; i < maxImages; i++) {
                    Uri imageUri = listaUrisSelecionadas.get(i);

                    try {
                        Bitmap bitmap = getResizedBitmap(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri), 500, 500);
                        ImageView imageView = new ImageView(this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        imageView.setImageBitmap(bitmap);

                        // Converter o Bitmap em byte[]
                        byte[] imageBytes = getBytesFromBitmap(bitmap);

                        if (i < 2) {
                            linearLayoutTop.addView(imageView);
                            // Armazenar o byte[] em variáveis
                            if (i == 0) {
                                imagemSuperior1Bytes = imageBytes;
                            } else if (i == 1) {
                                imagemSuperior2Bytes = imageBytes;
                            }
                        } else {
                            linearLayoutBottom.addView(imageView);
                            // Armazenar o byte[] em variáveis
                            if (i == 2) {
                                imagemInferior1Bytes = imageBytes;
                            } else if (i == 3) {
                                imagemInferior2Bytes = imageBytes;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            limparCampos();
        }

        private byte[] getBytesFromBitmap(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        }

        private Bitmap getResizedBitmap(Bitmap bitmap, int newWidth, int newHeight) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;
            // Cria uma matriz para manipulação da imagem
            Matrix matrix = new Matrix();
            // Define o fator de escala para a matriz
            matrix.postScale(scaleWidth, scaleHeight);
            // Cria uma nova imagem redimensionada
            return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == PERMISSION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    escolherImagens();
                } else {
                    Toast.makeText(this, "Permissão negada para acessar as imagens", Toast.LENGTH_SHORT).show();
                }
            }
        }

}
