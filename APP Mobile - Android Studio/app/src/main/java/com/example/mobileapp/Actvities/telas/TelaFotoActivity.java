package com.example.mobileapp.Actvities.telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mobileapp.Actvities.adapter.FotoAdapter;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class TelaFotoActivity extends AppCompatActivity {
    private List<ModelCliente> listFotos = new ArrayList<>();
    private RecyclerView recyclerViewFotos;
    private FotoAdapter fotoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fotos);
        inicializarComponentes();

        // Crie um objeto ModelCliente ou obtenha-o de alguma forma
        ModelCliente mCliente = new ModelCliente();

        exibirFotos(mCliente);
    }
    public void inicializarComponentes() {
        recyclerViewFotos = findViewById(R.id.recyclerViewFotos);
    }

    private void exibirFotos(ModelCliente mCliente) {
        DaoCliente daoCliente = new DaoCliente(getBaseContext());

        if (mCliente.getCliCPF() != null) {
            // Realizar a busca das fotos no banco de dados usando o CPF fornecido
            List<ModelCliente> listaClientes = daoCliente.buscarFotosCliente(mCliente);

            if (!listaClientes.isEmpty()) {
                // Fotos encontradas, exibir no RecyclerView
                listFotos.clear();
                listFotos.addAll(listaClientes);

                fotoAdapter = new FotoAdapter(listFotos, getApplicationContext());

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewFotos.setLayoutManager(layoutManager);
                recyclerViewFotos.setHasFixedSize(true);
                recyclerViewFotos.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
                recyclerViewFotos.setAdapter(fotoAdapter);
            } else {
                // Nenhuma foto encontrada para o CPF fornecido
                Snackbar.make(recyclerViewFotos, "Nenhuma foto encontrada para o CPF", Snackbar.LENGTH_LONG).show();
            }
        } else {
            // CPF não fornecido
            Snackbar.make(recyclerViewFotos, "CPF não fornecido", Snackbar.LENGTH_LONG).show();
        }
    }
}
