package com.example.sqlitetugas3.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitetugas3.Database.PegawaiQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Show.PegawaiActivity;
import com.example.sqlitetugas3.Update.PegawaiUpdateDialogFragment;
import com.example.sqlitetugas3.Update.PegawaiUpdateListener;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Pegawai;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.CustomViewHolder> {

    private Context context;
    private List<Pegawai> pegawaiList;
    private PegawaiQuery pegawaiQuery;

    public PegawaiAdapter(Context context, List<Pegawai> pegawaiList) {
        this.context = context;
        this.pegawaiList = pegawaiList;
        pegawaiQuery = new PegawaiQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pegawai, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Pegawai pegawai = pegawaiList.get(position);

        holder.txtName.setText(pegawai.getPegawaiName());
        holder.txtAlamat.setText(": " + pegawai.getPegawaiAlamat());
        holder.txtNotelp.setText(": " + pegawai.getPegawaiNoTelp());
        holder.txtToko.setText(": " + pegawai.getPegawaiToko());
        holder.txtDevisi.setText(": " + pegawai.getPegawaiDevisi());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus pegawai ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deletePegawai(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PegawaiUpdateDialogFragment pegawaiUpdateDialogFragment = PegawaiUpdateDialogFragment.newInstance(pegawai.getId(), itemPosition, new PegawaiUpdateListener() {
                    @Override
                    public void onPegawaiUpdated(Pegawai pegawai, int position) {
                        pegawaiList.set(position, pegawai);
                        notifyDataSetChanged();
                    }
                });
                pegawaiUpdateDialogFragment.show(((PegawaiActivity) context).getSupportFragmentManager(), Config.UPDATE_PEGAWAI);
            }
        });
    }

    private void deletePegawai(int position) {
        Pegawai pegawai = pegawaiList.get(position);
        long count = pegawaiQuery.deletePegawaiByID(pegawai.getId());

        if (count > 0) {
            pegawaiList.remove(position);
            notifyDataSetChanged();
            ((PegawaiActivity) context).viewVisibility();
            Toast.makeText(context, "Pegawai berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return pegawaiList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAlamat, txtNotelp, txtToko, txtDevisi;
        ImageView imgDelete;
        ImageView imgEdit;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtAlamat = itemView.findViewById(R.id.item_pegawai_alamat);
            txtNotelp = itemView.findViewById(R.id.item_pegawai_no_telp);
            txtName = itemView.findViewById(R.id.item_pegawai_name);
            txtDevisi = itemView.findViewById(R.id.item_pegawai_devisi);
            txtToko = itemView.findViewById(R.id.item_pegawai_toko);
            imgDelete = itemView.findViewById(R.id.item_pegawai_crossImageView);
            imgEdit = itemView.findViewById(R.id.item_pegawai_editImageView);
        }
    }
}