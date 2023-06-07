package com.example.mobileapp.Actvities.telas;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.R;

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

                // Chamar o método checkLogin para verificar as credenciais
                if(cpf.equals("admin") && cnh.equals("admin")){
                    startActivity(new Intent(tela_login.this, MainActivity.class));
                    Toast.makeText(tela_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                }
                else if (checkLogin(cpf, cnh)) {
                    Toast.makeText(tela_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(tela_login.this, usuario_logado.class));
                } else {
                    Toast.makeText(tela_login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkLogin(String cpf, String cnh) {
        return daoCliente.checkLogin(cpf, cnh);
    }
}
