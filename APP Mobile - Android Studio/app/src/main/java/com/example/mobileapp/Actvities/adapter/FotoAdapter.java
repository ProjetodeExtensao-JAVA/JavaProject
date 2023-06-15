package com.example.mobileapp.Actvities.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.R;

import java.util.ArrayList;
import java.util.List;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.ViewHolder> {
    private Context context;
    private List<ModelCliente> clientes;

    public FotoAdapter(List<ModelCliente> clientes, Context context) {
        this.clientes = clientes;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_foto_cliente, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCliente cliente = clientes.get(position);

        // Obter as fotos do cliente
        byte[] fotoUm = cliente.getCliFotoUm();
        byte[] fotoDois = cliente.getCliFotoDois();
        byte[] fotoTres = cliente.getCliFotoTres();
        byte[] fotoQuatro = cliente.getCliFotoQuatro();

        // Converter os arrays de bytes para Bitmaps
        Bitmap fotoUmBitmap = BitmapFactory.decodeByteArray(fotoUm, 0, fotoUm.length);
        Bitmap fotoDoisBitmap = BitmapFactory.decodeByteArray(fotoDois, 0, fotoDois.length);
        Bitmap fotoTresBitmap = BitmapFactory.decodeByteArray(fotoTres, 0, fotoTres.length);
        Bitmap fotoQuatroBitmap = BitmapFactory.decodeByteArray(fotoQuatro, 0, fotoQuatro.length);

        // Exibir as fotos nos ImageViews correspondentes
        holder.cpf.setText(cliente.getCliCPF());
        holder.imageViewFotoUm.setImageBitmap(fotoUmBitmap);
        holder.imageViewFotoDois.setImageBitmap(fotoDoisBitmap);
        holder.imageViewFotoTres.setImageBitmap(fotoTresBitmap);
        holder.imageViewFotoQuatro.setImageBitmap(fotoQuatroBitmap);
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFotoUm;
        ImageView imageViewFotoDois;
        ImageView imageViewFotoTres;
        ImageView imageViewFotoQuatro;
        TextView cpf;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cpf = itemView.findViewById(R.id.textViewPlaca);
            imageViewFotoUm = itemView.findViewById(R.id.imageViewFotoUm);
            imageViewFotoDois = itemView.findViewById(R.id.imageViewFotoDois);
            imageViewFotoTres = itemView.findViewById(R.id.imageViewFotoTres);
            imageViewFotoQuatro = itemView.findViewById(R.id.imageViewFotoQuatro);
        }
    }
}
