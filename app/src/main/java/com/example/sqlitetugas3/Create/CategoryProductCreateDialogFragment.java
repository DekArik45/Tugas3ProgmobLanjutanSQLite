package com.example.sqlitetugas3.Create;

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

public class CategoryProductCreateDialogFragment extends DialogFragment {
    private static CategoryProductCreateListener categoryProductCreateListener;

    private CategoryProductQuery categoryProductQuery;

    private EditText editName;
    private Button createBtn, cancelBtn;
    private String nameString = "";

    public CategoryProductCreateDialogFragment() {
        // Required empty public constructor
    }

    public static CategoryProductCreateDialogFragment newInstance(String title, CategoryProductCreateListener listener) {
        categoryProductCreateListener = listener;
        CategoryProductCreateDialogFragment categoryProductCreateDialogFragment = new CategoryProductCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        categoryProductCreateDialogFragment.setArguments(args);
        categoryProductCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return categoryProductCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_product_create_dialog, container, false);

        editName = view.findViewById(R.id.create_category_product_nama);
        createBtn = view.findViewById(R.id.fragment_category_product_createButton);
        cancelBtn = view.findViewById(R.id.fragment_category_product_createCancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    private void save(){
        nameString = editName.getText().toString();

        CategoryProduct categoryProduct = new CategoryProduct(-1,nameString);

        categoryProductQuery = new CategoryProductQuery(getContext());

        long id = categoryProductQuery.insertCategoryProduct(categoryProduct);

        if(id>0){
            categoryProduct.setId(id);
            categoryProductCreateListener.onCategoryProductCreated(categoryProduct);
            getDialog().dismiss();
        }
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
