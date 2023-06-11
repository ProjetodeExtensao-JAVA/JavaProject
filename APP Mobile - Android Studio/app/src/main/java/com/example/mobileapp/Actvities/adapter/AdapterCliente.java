package com.example.mobileapp.Actvities.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.mobileapp.Manifest;
import com.example.mobileapp.R;
import com.example.mobileapp.Actvities.dao.DaoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.Actvities.telas.ClienteDetalhesActivity;
import com.example.mobileapp.Actvities.telas.ClientesActivity;

public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.MyViewHolder>{
    private List<ModelCliente> listCliente;
    private Context context;
    //Metodo Construtor
    public AdapterCliente(List<ModelCliente> listar, Context context) {
        this.listCliente =listar;
        this.context = context;
    }
    /*
    * Método que deverá retornar layout criado pelo ViewHolder já inflado em uma view.
    * */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View itemListaCliente = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.adapter_cliente, viewGroup, false);
        return new MyViewHolder(itemListaCliente);
    }
    /*
    /*Método que recebe o ViewHolder e a posição da lista. Aqui é recuperado o objeto da lista
    /*de Objetos pela posição e associado à ViewHolder. É onde a mágica acontece!  */
    @Override
    public  void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
        final ModelCliente cliente = listCliente.get(i);
        //retorna o nome do cliente
        myViewHolder.modelo.setText(cliente.getCliModelo());
        myViewHolder.placa.setText(cliente.getCliPlaca());
        myViewHolder.km.setText(String.valueOf(cliente.getClikm()));

        //evento para setar detalhes clientes
        //excluir cliente
        myViewHolder.mudarCor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int novaCor = Color.RED; // Defina a cor desejada aqui
                myViewHolder.linearLayout.setBackgroundColor(novaCor);
                // Chama o método para calcular o aviso
                calculoAviso(cliente.getCliCPF());
            }
        });


        myViewHolder.excluirCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(myViewHolder.activity);
                builder.setCancelable(false);
                builder.setTitle("Excluir");
                builder.setPositiveButton("Confirma!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DaoCliente daoCliente = new DaoCliente(context);
                        Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show();
                        ModelCliente deleteCliente = listCliente.get(i);
                        deleteCliente.getCliCPF();
                        daoCliente.deleteCliente(deleteCliente);
                        listCliente.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, getItemCount());
                    }
                });
                builder.setNegativeButton("Cancelar!", null);
                builder.create().show();

            }
        });
    }
    @Override
    public int getItemCount() {
        return this.listCliente.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        Context activity;
        LinearLayout linearLayout;
        TextView placa;

        TextView modelo;
        TextView km;
        TextView mudarCor;
        TextView excluirCarro;
        //
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            activity = itemView.getContext();
            modelo = itemView.findViewById(R.id.idTxtModelo);
            placa = itemView.findViewById(R.id.idTxtPlaca);
            km = itemView.findViewById(R.id.idTxtKm);
            excluirCarro = itemView.findViewById(R.id.idTxtExcluirCarro);
            mudarCor = itemView.findViewById(R.id.idTxtMudarCor);
            linearLayout = itemView.findViewById(R.id.Layout);
        }
    }

}
