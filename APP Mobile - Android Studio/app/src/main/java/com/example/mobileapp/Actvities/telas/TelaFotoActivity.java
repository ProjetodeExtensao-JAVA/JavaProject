package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobileapp.Actvities.adapter.FotoAdapter;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class TelaFotoActivity extends AppCompatActivity {
    private DaoCliente daoCliente;
    private List<ModelCliente> clientes;
    private RecyclerView recyclerViewFotos;
    private FotoAdapter fotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fotos);

        // Inicializar o DAO de cliente
        daoCliente = new DaoCliente(this);

        // Inicializar o RecyclerView e o Adapter
        recyclerViewFotos = findViewById(R.id.recyclerViewFotos);
        recyclerViewFotos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fotoAdapter = new FotoAdapter(this);
        recyclerViewFotos.setAdapter(fotoAdapter);

        // Buscar todos os clientes no banco de dados
        buscarClientes();
    }

    private void buscarClientes() {
        clientes = daoCliente.listarCliente();

        List<ModelCliente> clientesComFotos = new ArrayList<>();

        for (ModelCliente cliente : clientes) {
            // Buscar as fotos do cliente no banco de dados com base no CPF
            ModelCliente clienteComFotos = daoCliente.buscarFotosCliente(cliente.getCliCPF());

            if (clienteComFotos != null) {
                // Adicionar o cliente com fotos na lista
                clientesComFotos.add(clienteComFotos);
            }
        }

        // Atualizar os dados do Adapter com a lista de clientes com fotos
        fotoAdapter.setClientes(clientesComFotos);
    }
}
