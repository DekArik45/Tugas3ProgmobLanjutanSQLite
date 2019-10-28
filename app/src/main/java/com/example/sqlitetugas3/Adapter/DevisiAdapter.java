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

import com.example.sqlitetugas3.Database.DevisiQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Show.DevisiActivity;
import com.example.sqlitetugas3.Update.DevisiUpdateDialogFragment;
import com.example.sqlitetugas3.Update.DevisiUpdateListener;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Devisi;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class DevisiAdapter extends RecyclerView.Adapter<DevisiAdapter.CustomViewHolder> {

    private Context context;
    private List<Devisi> devisiList;
    private DevisiQuery devisiQuery;

    public DevisiAdapter(Context context, List<Devisi> devisiList) {
        this.context = context;
        this.devisiList = devisiList;
        devisiQuery = new DevisiQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_devisi, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Devisi devisi = devisiList.get(position);

        holder.txtName.setText(devisi.getDevisiName());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus devisi ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteDevisi(itemPosition);
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
                DevisiUpdateDialogFragment devisiUpdateDialogFragment = DevisiUpdateDialogFragment.newInstance(devisi.getId(), itemPosition, new DevisiUpdateListener() {
                    @Override
                    public void onDevisiUpdated(Devisi devisi, int position) {
                        devisiList.set(position, devisi);
                        notifyDataSetChanged();
                    }
                });
                devisiUpdateDialogFragment.show(((DevisiActivity) context).getSupportFragmentManager(), Config.UPDATE_DEVISI);
            }
        });
    }

    private void deleteDevisi(int position) {
        Devisi devisi = devisiList.get(position);
        long count = devisiQuery.deleteDevisiByID(devisi.getId());

        if (count > 0) {
            devisiList.remove(position);
            notifyDataSetChanged();
            ((DevisiActivity) context).viewVisibility();
            Toast.makeText(context, "Devisi berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return devisiList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgDelete;
        ImageView imgEdit;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.item_devisi_name);
            imgEdit = itemView.findViewById(R.id.item_devisi_editImageView);
            imgDelete = itemView.findViewById(R.id.item_devisi_crossImageView);
        }
    }
}