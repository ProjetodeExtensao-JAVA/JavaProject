package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.R;

public class tela_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tLogin = (TextView) findViewById(R.id.inputLogin);
                TextView tSenha = (TextView) findViewById(R.id.inputSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();
                Intent x = new Intent(tela_login.this, usuario_logado.class);
                Intent y = new Intent(tela_login.this, MainActivity.class);
                if(login.equals("Toin") && senha.equals("123")){
                    alert("Login realizado com sucesso!");
                    startActivity(x);
                }if(login.equals("admin") && senha.equals("admin")){
                    alert("Login realizado com sucesso!");
                    startActivity(y);
                } else{
                    alert("Login incorreto!");
                }
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}