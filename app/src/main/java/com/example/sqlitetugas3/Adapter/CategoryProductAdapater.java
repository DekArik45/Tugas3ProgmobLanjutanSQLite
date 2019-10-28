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

import com.example.sqlitetugas3.Database.CategoryProductQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Show.CategoryProductActivity;
import com.example.sqlitetugas3.Update.CategoryProductUpdateDialogFragment;
import com.example.sqlitetugas3.Update.CategoryProductUpdateListener;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.CategoryProduct;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

public class CategoryProductAdapater extends RecyclerView.Adapter<CategoryProductAdapater.CustomViewHolder> {

    private Context context;
    private List<CategoryProduct> categoryProductList;
    private CategoryProductQuery categoryProductQuery;

    public CategoryProductAdapater(Context context, List<CategoryProduct> categoryProductList) {
        this.context = context;
        this.categoryProductList = categoryProductList;
        categoryProductQuery = new CategoryProductQuery(context);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_product, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int itemPosition = position;
        final CategoryProduct categoryProduct = categoryProductList.get(position);

        holder.txtName.setText(categoryProduct.getCategoryName());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("yakin ingin menghapus Category Product ini?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteCategoryProduct(itemPosition);
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
                CategoryProductUpdateDialogFragment categoryProductUpdateDialogFragment = CategoryProductUpdateDialogFragment.newInstance(categoryProduct.getId(), itemPosition, new CategoryProductUpdateListener() {
                    @Override
                    public void onCategoryProductUpdated(CategoryProduct categoryProduct, int position) {
                        categoryProductList.set(position, categoryProduct);
                        notifyDataSetChanged();
                    }
                });
                categoryProductUpdateDialogFragment.show(((CategoryProductActivity) context).getSupportFragmentManager(), Config.UPDATE_CATEGORY_PRODUCT);
            }
        });
    }

    private void deleteCategoryProduct(int position) {
        CategoryProduct categoryProduct = categoryProductList.get(position);
        long count = categoryProductQuery.deleteCategoryProductByID(categoryProduct.getId());

        if (count > 0) {
            categoryProductList.remove(position);
            notifyDataSetChanged();
            ((CategoryProductActivity) context).viewVisibility();
            Toast.makeText(context, "Category Product berhasil di delete", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(context, "gagal delete, ada yang salah!", Toast.LENGTH_LONG).show();

    }

    @Override
    public int getItemCount() {
        return categoryProductList.size();
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imgDelete;
        ImageView imgEdit;

        public CustomViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.item_category_product_name);
            imgEdit = itemView.findViewById(R.id.item_category_product_editImageView);
            imgDelete = itemView.findViewById(R.id.item_category_product_crossImageView);
        }
    }
}