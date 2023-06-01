package com.example.mobileapp.Actvities.telas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

import java.util.List;

//public class tela_login extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tela_login);
//
//        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        btnSubmit.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                TextView tLogin = (TextView) findViewById(R.id.inputLogin);
//                TextView tSenha = (TextView) findViewById(R.id.inputSenha);
//                String login = tLogin.getText().toString();
//                String senha = tSenha.getText().toString();
//                Intent x = new Intent(tela_login.this, usuario_logado.class);
//                Intent y = new Intent(tela_login.this, MainActivity.class);
//                if(login.equals("Toin") && senha.equals("123")){
//                    alert("Login realizado com sucesso!");
//                    startActivity(x);
//                }if(login.equals("admin") && senha.equals("admin")){
//                    alert("Login realizado com sucesso!");
//                    startActivity(y);
//                } else{
//                    alert("Login incorreto!");
//                }
//            }
//        });
//    }
public class tela_login extends AppCompatActivity {

    private EditText editTextCpf;
    private EditText editTextCNH;
    private Button buttonLogin;

    private DaoCliente daoCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        // Inicializar os componentes da interface
        editTextCpf = findViewById(R.id.inputLoginCPF);
        editTextCNH = findViewById(R.id.inputSenhaCNH);
        buttonLogin = findViewById(R.id.btnSubmitLogin);

        // Instanciar o DaoCliente
        daoCliente = new DaoCliente(this);

        // Configurar o clique do botão de login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCpf.getText().toString();
                String cnh = editTextCNH.getText().toString();

                // Verificar se o CPF e a placa estão preenchidos
//                if(editTextCpf.equals("admin") && editTextCNH.equals("admin")){
//                   startActivity(new  Intent  (tela_login.this, MainActivity.class));
//                }
                if (!TextUtils.isEmpty(cpf) && !TextUtils.isEmpty(cnh)) {
                    // Criar um ModelCliente para realizar a busca
                    ModelCliente cliente = new ModelCliente();
                    cliente.setCliCPF(cpf);
                    cliente.setCliPlaca(cnh);
                    startActivity(new  Intent  (tela_login.this, MainActivity.class));

                    // Realizar a busca no banco de dados
                    List<ModelCliente> listaClientes = daoCliente.listarCliente();

                    // Verificar se o cliente está na lista
                    boolean loginValido = false;
                    for (ModelCliente c : listaClientes) {
                        if (c.getCliCPF().equals(cpf) && c.getCliPlaca().equals(cnh)) {
                            loginValido = true;
                            break;
                        }
                    }

                    if (loginValido) {
                        // Login bem-sucedido
                        Toast.makeText(tela_login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
//                        startActivity(new  Intent  (tela_login.this, usuario_logado.class));

                    } else {
                        // Login inválido
                        Toast.makeText(tela_login.this, "Login inválido. CPF ou placa incorretos.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // CPF ou placa não preenchidos
                    Toast.makeText(tela_login.this, "Por favor, preencha o CPF e a placa.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

//    private void alert(String s){
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
//    }
//}