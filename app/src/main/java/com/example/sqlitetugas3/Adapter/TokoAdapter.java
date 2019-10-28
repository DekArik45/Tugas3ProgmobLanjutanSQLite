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

import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Show.TokoActivity;
import com.example.sqlitetugas3.Update.TokoUpdateDialogFragment;
import com.example.sqlitetugas3.Update.TokoUpdateListener;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Toko;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.CustomViewHolder> {

    private Context context;
    private List<Toko> tokoList;
    private TokoQuery tokoQuery;

    public TokoAdapter(Context context, List<Toko> tokoList) {
        this.context = context;
        this.tokoList = tokoList;
        tokoQuery = new TokoQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_toko, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Toko toko = tokoList.get(position);

        holder.txtName.setText(toko.getTokoName());
        holder.txtAlamat.setText(": "+toko.getTokoAlamat());
        holder.txtNotelp.setText(": "+toko.getTokoNoTelp());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus toko ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteToko(itemPosition);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
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
                TokoUpdateDialogFragment tokoUpdateDialogFragment = TokoUpdateDialogFragment.newInstance(toko.getId(), itemPosition, new TokoUpdateListener() {
                    @Override
                    public void onTokoUpdated(Toko toko, int position) {
                        tokoList.set(position, toko);
                        notifyDataSetChanged();
                    }
                });
                tokoUpdateDialogFragment.show(((TokoActivity) context).getSupportFragmentManager(), Config.UPDATE_TOKO);
            }
        });
    }

    private void deleteToko(int position) {
        Toko toko = tokoList.get(position);
        long count = tokoQuery.deleteTokoByID(toko.getId());

        if(count>0){
            tokoList.remove(position);
            notifyDataSetChanged();
            ((TokoActivity) context).viewVisibility();
            Toast.makeText(context, "Product berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return tokoList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtAlamat, txtNotelp;
        ImageView imgDelete;
        ImageView imgEdit;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtAlamat = itemView.findViewById(R.id.item_toko_alamat);
            txtNotelp = itemView.findViewById(R.id.item_toko_no_telp);
            txtName = itemView.findViewById(R.id.item_toko_name);
            imgDelete = itemView.findViewById(R.id.item_toko_crossImageView);
            imgEdit = itemView.findViewById(R.id.item_toko_editImageView);
        }
    }
}