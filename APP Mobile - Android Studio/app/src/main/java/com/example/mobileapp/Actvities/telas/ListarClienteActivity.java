package com.example.mobileapp.Actvities.telas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.example.mobileapp.R;
import com.example.mobileapp.Actvities.adapter.AdapterCliente;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;

public class ListarClienteActivity extends AppCompatActivity {
    //Classe Java, p/ lista clientes cadastrados
    //declaração de variavel
    private RecyclerView recyclerViewCliente;
    private AdapterCliente adapterCliente;
    private List<ModelCliente> listCliente = new ArrayList<>();
    private TextView txtDelete;
    //metodos da classe
    //1-metodo de inicilizar componentes
    public void inicializarComponentes() {
        recyclerViewCliente = (RecyclerView) findViewById(R.id.idRecyclerViewListarCliente);
        txtDelete = (TextView) findViewById(R.id.idTxtExcluirCarro);
    }
    //2-metodo para carregar Lista de clientes
    public void carregarListacliente() {
        //lista clientes do banco de dados
        DaoCliente daoCliente = new DaoCliente(getBaseContext());
        listCliente = daoCliente.listarCliente();
        //exibir lista de cliente no RecyclerView
        //configurar um adapter
        adapterCliente = new AdapterCliente(listCliente, getApplicationContext());
        //configurar recycleriew
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        // GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerViewCliente.setLayoutManager(layoutManager);
        recyclerViewCliente.setHasFixedSize(true);
        recyclerViewCliente.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerViewCliente.setAdapter(adapterCliente);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_cliente);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //inicializar componentes: metodo de inicializar
        inicializarComponentes();
        carregarListacliente();
    }
    @Override
    protected void onStart() {
        carregarListacliente();
        super.onStart();
    }
}