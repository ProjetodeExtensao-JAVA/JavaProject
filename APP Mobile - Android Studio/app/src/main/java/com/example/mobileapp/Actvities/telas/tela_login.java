package com.example.mobileapp.Actvities.telas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileapp.R;

public class tela_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_login);
    }
}
//logica de login ou quase isso
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_screen);
//
//        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView tLogin = (TextView) findViewById(R.id.inputLogin);
//                TextView tSenha = (TextView) findViewById(R.id.inputSenha);
//                String login = tLogin.getText().toString();
//                String senha = tSenha.getText().toString();
//                Intent x = new Intent(ActvityLogin.this, UserScreen.class);
//                Intent y = new Intent(ActvityLogin.this, MainAdmin.class);
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