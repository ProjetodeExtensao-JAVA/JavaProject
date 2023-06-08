package com.example.mobileapp.Actvities.telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileapp.R;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;

public class ClientesActivity extends AppCompatActivity {
    //Classe java para entrada de dados: cadastro de cliente
    //declaração dos componentes
    private EditText campoModelo, campoPlaca, campoKm, campoNome, campoCPF, campoCNH, campoCelular;
    private Button botaoAdicionarCliente;
    ModelCliente clienteAtual;
    //metodo para incializar componentes
    public void inicializarComponentes(){
        campoModelo = (EditText)findViewById(R.id.idModelo);
        campoPlaca = (EditText)findViewById(R.id.idPlaca);
        campoKm = (EditText)findViewById(R.id.idKm);

        campoNome = (EditText)findViewById(R.id.idNome);
        campoCPF = (EditText)findViewById(R.id.idCPF);
        campoCNH = (EditText)findViewById(R.id.idCNH);
        campoCelular = (EditText)findViewById(R.id.idCelular);

        botaoAdicionarCliente = (Button)findViewById(R.id.idBtnCadastro);
    }
    //metodo para limpar campos EdiText
    public void limparCampos(){
        campoModelo.setText("");
        campoPlaca.setText("");
        campoKm.setText("");

        campoNome.setText("");
        campoCPF.setText("");
        campoCNH.setText("");
        campoCelular.setText("");
    }
    //metodo para cadastro de  cliente na base de dados: Tabela cliente
    public void cadastroCliente(View view){
        //declarar variaveis do tipos string para receber os atributos do cliente
        String txtModelo, txtPlaca, txtNome, txtCPF, txtCNH, txtCelular;
        txtModelo = campoModelo.getText().toString();
        txtPlaca = campoPlaca.getText().toString();
        String txtKmString = campoKm.getText().toString();
        int txtKm = Integer.parseInt(txtKmString);

        txtNome = campoNome.getText().toString();
        txtCPF = campoCPF.getText().toString();
        txtCNH = campoCNH.getText().toString();
        txtCelular = campoCelular.getText().toString();

        //realizar test para verificar se os campos não estao vazio
        //se vazio retorna mensagens na tela para preencher o campo
        //Execeto campo observação
        if (!txtNome.isEmpty()){
            if (!txtCelular.isEmpty()){
                if (!txtPlaca.isEmpty()){
                    if (!txtModelo.isEmpty()){
                        if (!txtCPF.isEmpty()){
                            if (!txtCNH.isEmpty()){
                                //Instancia classe Dao cliente para chamar metodo de salvar na base de dados
                                DaoCliente escreverCliente = new DaoCliente(getBaseContext());
                                //instancia classe cliente do pacote model para atribuir o valores nos
                                //atributos da classe
                                ModelCliente setCliente = new ModelCliente();
                                //passando nos parametros os valores de entreda digitado pelo usuario
                                setCliente.setCliModelo(txtModelo);
                                setCliente.setCliPlaca(txtPlaca);
                                setCliente.setCliKm(txtKm);
                                setCliente.setCliNome(txtNome);
                                setCliente.setCliCPF(txtCPF);
                                setCliente.setCliCNH(txtCNH);
                                setCliente.setCliCelular(txtCelular);
                                //declarar variavel para receber um valor false o true
                                //que retorna da DaoCliente.salvar() que e boolean
                                boolean resultado;
                                //chamar o metodo salvar da classe DaoCliente para setar os atributos
                                //passar por paramentro os atributos do tipo cliente
                                resultado = escreverCliente.cadastroCliente(setCliente);
                                System.out.println(resultado);
                                
                                //verifica qual foi o tipo de retorno true ou false
                                if (resultado == true){
                                    //Avisar para usuario na tela que os dados foram gravados com sucesso
                                    //na base de dados: tabela cliente
                                    //Aplicar Toas personalizado
                                    Toast toast = Toast.makeText(ClientesActivity.this,"Motorista: "+txtNome+
                                                    "\n"+"Adicionado com sucesso",
                                            Toast.LENGTH_LONG);
                                    LinearLayout toastContentView = (LinearLayout) toast.getView ();
                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setImageResource(R.drawable.ic_positivo_24dp);
                                    toastContentView.addView(imageView, 0);
                                    toast.show();
                                    //chamar metodo para limpar os campos, caso o usuario deseja cadastrar
                                    //mais clientes
                                    limparCampos();
                                }else {
                                    //caso nao retornar true, Lançar Mensagens na tela
                                    Toast.makeText(ClientesActivity.this,"Erro ao Adicionar cliente: "+txtNome,
                                            Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(ClientesActivity.this,"Preencha o campo CNH!",
                                        Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(ClientesActivity.this,"Preencha o campo CPF!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(ClientesActivity.this,"Preencha o campo Endereço!",
                                Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(ClientesActivity.this,"Preencha o campo Email!",
                            Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(ClientesActivity.this,"Preencha o campo Celular!",
                        Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ClientesActivity.this,"Preencha o campo nome!",
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Inicializar Componentes
        inicializarComponentes();
    }
}