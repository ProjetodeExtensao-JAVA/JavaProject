package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainProgram extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program);

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tLogin = (TextView) findViewById(R.id.inputLogin);
                TextView tSenha = (TextView) findViewById(R.id.inputSenha);
                String login = tLogin.getText().toString();
                String senha = tSenha.getText().toString();
                Intent in = new Intent(MainProgram.this, TelaPrincipal.class);
                if(login.equals("Admin") && senha.equals("Admin123")){
                    alert("Login realizado com sucesso!");
                    startActivity(in);
                }else{
                    alert("Login incorreto!");
                }
            }
        });
    }
    private void alert(String s){
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}