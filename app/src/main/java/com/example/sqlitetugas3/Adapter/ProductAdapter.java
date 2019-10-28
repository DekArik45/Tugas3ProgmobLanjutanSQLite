package com.example.sqlitetugas3.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitetugas3.Database.ProductQuery;
import com.example.sqlitetugas3.Show.ProductActivity;
import com.example.sqlitetugas3.pojo.Product;
import com.example.sqlitetugas3.Update.ProductUpdateDialogFragment;
import com.example.sqlitetugas3.Update.ProductUpdateListener;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.CustomViewHolder> {

    private Context context;
    private List<Product> productList;
    private ProductQuery productQuery;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        productQuery = new ProductQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final Product product = productList.get(position);

        holder.txtName.setText(product.getProductName());
        holder.txtMerk.setText(": "+product.getProductMerk());
        holder.txtCategory.setText(": "+product.getProductCategory());
        holder.txtToko.setText(": "+product.getProductToko());
        holder.txtDesc.setText(": "+product.getProductDesc());
        holder.txtHarga.setText(": Rp. "+String.valueOf(product.getProductHarga()));
        holder.txtQty.setText(": "+String.valueOf(product.getProductQty()));

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus product ini?");
                        alertDialogBuilder.setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        deleteProduct(itemPosition);
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
                ProductUpdateDialogFragment productUpdateDialogFragment = ProductUpdateDialogFragment.newInstance(product.getId(), itemPosition, new ProductUpdateListener() {
                    @Override
                    public void onProductInfoUpdated(Product product, int position) {
                        productList.set(position, product);
                        notifyDataSetChanged();
                    }
                });
                productUpdateDialogFragment.show(((ProductActivity) context).getSupportFragmentManager(), Config.UPDATE_PRODUCT);
            }
        });
    }

    private void deleteProduct(int position) {
        Product product = productList.get(position);
        long count = productQuery.deleteProductByID(product.getId());

        if(count>0){
            productList.remove(position);
            notifyDataSetChanged();
            ((ProductActivity) context).viewVisibility();
            Toast.makeText(context, "Product berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtMerk, txtCategory, txtDesc,txtHarga,txtQty,txtToko;
        ImageView imgDelete;
        ImageView imgEdit;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.item_product_name);
            txtMerk = itemView.findViewById(R.id.item_product_merek);
            txtToko = itemView.findViewById(R.id.item_product_toko);
            txtCategory = itemView.findViewById(R.id.item_product_category);
            txtDesc = itemView.findViewById(R.id.item_product_description);
            txtHarga = itemView.findViewById(R.id.item_product_harga);
            txtQty = itemView.findViewById(R.id.item_product_qty);
            imgDelete = itemView.findViewById(R.id.crossImageView);
            imgEdit = itemView.findViewById(R.id.editImageView);
        }
    }
}
