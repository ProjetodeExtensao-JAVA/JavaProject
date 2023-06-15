package com.example.mobileapp.Actvities.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.mobileapp.Actvities.metodo.MetodoCliente;
import com.example.mobileapp.Actvities.model.ModelCliente;
import com.example.mobileapp.Actvities.provedor.SQLite;
import com.google.android.material.snackbar.Snackbar;

public class DaoCliente implements MetodoCliente {
    SQLiteDatabase sqlEscrever;
    SQLiteDatabase sqlLeitura;

    public DaoCliente(Context context) {
        SQLite base = new SQLite(context);
        sqlEscrever = base.getWritableDatabase();
        sqlLeitura = base.getReadableDatabase();
    }
//    métodos para converter uma imagem Bitmap em uma string Base64 (bitmapToBase64) e para converter uma string Base64 de volta em um objeto Bitmap (base64ToBitmap).
//    No entanto, não há chamadas explícitas a esses métodos nas funções cadastroCliente e updateFotoCliente fornecidas.
//
//    Para enviar as fotos para o banco de dados, você precisa chamar o método bitmapToBase64 para converter a imagem em uma string Base64 e,
//    em seguida, salvar essa string no banco de dados.
//
//    Aqui está um exemplo de como você pode modificar a função cadastroCliente para salvar a foto do cliente no banco de dados:
    private String bitmapToBase64(Bitmap imageBitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private Bitmap base64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    @Override
    public boolean cadastroCliente(ModelCliente mCliente) {
        ContentValues clientes = new ContentValues();

        clientes.put("cliCPF", mCliente.getCliCPF());
        clientes.put("cliCNH", mCliente.getCliCNH());
        clientes.put("cliNome", mCliente.getCliNome());
        clientes.put("cliCelular", mCliente.getCliCelular());
        clientes.put("cliPlaca", mCliente.getCliPlaca());
        clientes.put("cliModelo", mCliente.getCliModelo());
        clientes.put("cliKm", mCliente.getClikm());

        try {
            sqlEscrever.insert(SQLite.TABELA_CLIENTE, null, clientes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteCliente(ModelCliente mCliente) {
        try {
            String[] cpf = {mCliente.getCliCPF().toString()};
            sqlEscrever.delete(SQLite.TABELA_CLIENTE, "cliCPF = ?", cpf);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ModelCliente> listarCliente() {
        List<ModelCliente> list = new ArrayList<>();
        String sqlSelect = "SELECT * FROM " + SQLite.TABELA_CLIENTE + ";";
        Cursor cursor = sqlLeitura.rawQuery(sqlSelect, null);

        while (cursor.moveToNext()) {
            ModelCliente dados = new ModelCliente();
            String txtNome, txtCPF, txtCNH, txtCelular, txtModelo, txtPlaca;
            int txtKm;

            txtNome = cursor.getString(cursor.getColumnIndexOrThrow("cliNome"));
            txtCPF = cursor.getString(cursor.getColumnIndexOrThrow("cliCPF"));
            txtCNH = cursor.getString(cursor.getColumnIndexOrThrow("cliCNH"));
            txtCelular = cursor.getString(cursor.getColumnIndexOrThrow("cliCelular"));

            txtModelo = cursor.getString(cursor.getColumnIndexOrThrow("cliModelo"));
            txtPlaca = cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca"));
            txtKm = cursor.getInt(cursor.getColumnIndexOrThrow("cliKm"));

            dados.setCliNome(txtNome);
            dados.setCliCPF(txtCPF);
            dados.setCliCNH(txtCNH);
            dados.setCliCelular(txtCelular);

            dados.setCliModelo(txtModelo);
            dados.setCliPlaca(txtPlaca);
            dados.setCliKm(txtKm);

            list.add(dados);
        }

        cursor.close();
        return list;
    }

    public boolean checkLogin(String cpf, String cnh) {
        String query = "SELECT * FROM " + SQLite.TABELA_CLIENTE + " WHERE cliCPF = ? AND cliCNH = ?";
        Cursor cursor = sqlLeitura.rawQuery(query, new String[]{cpf, cnh});

        boolean loginSuccessful = cursor.getCount() > 0;

        cursor.close();
        return loginSuccessful;
    }

    public boolean updateKm(ModelCliente mCliente) {
        ContentValues valores = new ContentValues();
        String[] whereArgs = {mCliente.getCliCPF()};

        valores.put("cliKm", mCliente.getClikm());

        try {
            sqlEscrever.update(SQLite.TABELA_CLIENTE, valores, "cliCPF = ?", whereArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ModelCliente obterModeloPlaca(String cpf) {
        String query = "SELECT cliModelo, cliPlaca FROM " + SQLite.TABELA_CLIENTE + " WHERE cliCPF = ?";
        String[] selectionArgs = {cpf};
        Cursor cursor = sqlLeitura.rawQuery(query, selectionArgs);

        ModelCliente cliente = null;
        if (cursor.moveToFirst()) {
            cliente = new ModelCliente();
            cliente.setCliModelo(cursor.getString(cursor.getColumnIndexOrThrow("cliModelo")));
            cliente.setCliPlaca(cursor.getString(cursor.getColumnIndexOrThrow("cliPlaca")));
        }

        cursor.close();
        return cliente;
    }
    public boolean inserirFotosBanco(ModelCliente mCliente) {
        ContentValues clientes = new ContentValues();
        String[] whereArgs = {mCliente.getCliCPF()};
        try {
            clientes.put("cliCPF", mCliente.getCliCPF());
            clientes.put("cliFotoUm", mCliente.getCliFotoUm());
            clientes.put("cliFotoDois", mCliente.getCliFotoDois());
            clientes.put("cliFotoTres", mCliente.getCliFotoTres());
            clientes.put("cliFotoQuatro", mCliente.getCliFotoQuatro());

            sqlEscrever.insert(SQLite.TABELA_FOTOS, null, clientes);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public List<ModelCliente> buscarFotosCliente(ModelCliente mCliente) {
        List<ModelCliente> listaClientes = new ArrayList<>();
        String cpf = mCliente.getCliCPF();

        if (cpf != null && !cpf.isEmpty()) {
            String[] whereArgs = {cpf};
            String query = "SELECT cliFotoUm, cliFotoDois, cliFotoTres, cliFotoQuatro FROM " + SQLite.TABELA_FOTOS + " WHERE cliCPF = ?";
            Cursor cursor = sqlLeitura.rawQuery(query, whereArgs);

            while (cursor.moveToNext()) {
                ModelCliente cliente = new ModelCliente();

                byte[] fotoUmBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("cliFotoUm"));
                byte[] fotoDoisBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("cliFotoDois"));
                byte[] fotoTresBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("cliFotoTres"));
                byte[] fotoQuatroBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("cliFotoQuatro"));
                cliente.setCliCPF(cpf);
                cliente.setCliFotoUm(fotoUmBytes);
                cliente.setCliFotoDois(fotoDoisBytes);
                cliente.setCliFotoTres(fotoTresBytes);
                cliente.setCliFotoQuatro(fotoQuatroBytes);

                listaClientes.add(cliente);
            }
            cursor.close();
        }

        return listaClientes;
    }

}

