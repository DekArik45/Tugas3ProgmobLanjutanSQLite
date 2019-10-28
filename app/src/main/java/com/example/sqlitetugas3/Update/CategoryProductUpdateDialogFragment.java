package com.example.sqlitetugas3.Update;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlitetugas3.Database.CategoryProductQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.CategoryProduct;


public class CategoryProductUpdateDialogFragment extends DialogFragment {

    private static long idCategoryProduct;
    private static int categoryProductItemPosition;
    private static CategoryProductUpdateListener categoryProductUpdateListener;

    private CategoryProduct categoryProduct;
    private CategoryProductQuery categoryProductQuery;

    private EditText editName;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";

    public CategoryProductUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static CategoryProductUpdateDialogFragment newInstance(long id, int position, CategoryProductUpdateListener listener){
        idCategoryProduct = id;
        categoryProductItemPosition = position;
        categoryProductUpdateListener = listener;
        CategoryProductUpdateDialogFragment categoryProductUpdateDialogFragment = new CategoryProductUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Category Product");
        categoryProductUpdateDialogFragment.setArguments(args);

        categoryProductUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return categoryProductUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_product_update_dialog, container, false);

        categoryProductQuery = new CategoryProductQuery(getContext());

        editName = view.findViewById(R.id.update_category_product_nama);
        updateButton = view.findViewById(R.id.fragment_category_product_updateButton);
        cancelButton = view.findViewById(R.id.fragment_category_product_updateCancelButton);


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        categoryProduct = categoryProductQuery.getCategoryProductByID(idCategoryProduct);

        if(categoryProduct !=null){
            editName.setText(categoryProduct.getCategoryName());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = editName.getText().toString();

                    categoryProduct.setCategoryName(nameString);

                    long id = categoryProductQuery.updateCategoryProduct(categoryProduct);

                    if(id>0){
                        categoryProductUpdateListener.onCategoryProductUpdated(categoryProduct, categoryProductItemPosition);
                        getDialog().dismiss();
                    }
                }
            });

            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }

}
