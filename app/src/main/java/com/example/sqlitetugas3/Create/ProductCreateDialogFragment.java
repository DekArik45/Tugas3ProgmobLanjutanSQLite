package com.example.sqlitetugas3.Create;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitetugas3.Database.CategoryProductQuery;
import com.example.sqlitetugas3.Database.ProductQuery;
import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.CategoryProduct;
import com.example.sqlitetugas3.pojo.Product;
import com.example.sqlitetugas3.pojo.Toko;

import java.util.ArrayList;
import java.util.List;


public class ProductCreateDialogFragment extends DialogFragment {

    private static ProductCreateListener productCreateListener;

    private List<Toko> tokoList = new ArrayList<>();
    private List<CategoryProduct> categoryList = new ArrayList<>();

    TokoQuery tokoQuery;
    CategoryProductQuery categoryProductQuery;


    private EditText editProductName;
    private Spinner editProductCategory,editProductToko;
    private EditText editProductMerek;
    private EditText editProductDescription;
    private EditText editProductHarga;
    private EditText editProductQty;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "", tokoString = "",categoryString ="",merekString = "",descString = "";
    private int hargaInt = 0, qtyInt = 0;

    public ProductCreateDialogFragment() {
        // Required empty public constructor
    }

    public static ProductCreateDialogFragment newInstance(String title, ProductCreateListener listener){
        productCreateListener = listener;
        ProductCreateDialogFragment productCreateDialogFragment = new ProductCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        productCreateDialogFragment.setArguments(args);

        productCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return productCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_create_dialog, container, false);

        editProductName = view.findViewById(R.id.create_product_name);
        editProductMerek = view.findViewById(R.id.create_product_merk);
        editProductCategory = view.findViewById(R.id.create_product_categoryProduct);
        editProductToko = view.findViewById(R.id.create_product_toko);
        editProductDescription = view.findViewById(R.id.create_product_desc);
        editProductHarga = view.findViewById(R.id.create_harga);
        editProductQty = view.findViewById(R.id.create_qty);

        createButton = view.findViewById(R.id.fragment_product_createButton);
        cancelButton = view.findViewById(R.id.fragment_product_createCancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        spinnerCategory();
        spinnerToko();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameString = editProductName.getText().toString();
                merekString = editProductMerek.getText().toString();
//                tokoString = editProductToko.getText().toString();
//                categoryString = editProductCategory.getText().toString();

                descString = editProductDescription.getText().toString();
                if (editProductHarga.getText().toString().equals("")){
                    hargaInt=0;
                }
                else {
                    hargaInt = Integer.parseInt(editProductHarga.getText().toString());
                }

                if (editProductQty.getText().toString().equals("")){
                    qtyInt=0;
                }
                else {
                    qtyInt = Integer.parseInt(editProductQty.getText().toString());
                }

                Product product = new Product(-1, nameString,merekString,descString,categoryString,tokoString,hargaInt,qtyInt);

                ProductQuery productQuery = new ProductQuery(getContext());

                long id = productQuery.insertProduct(product);

                if(id>0){
                    product.setId(id);
                    productCreateListener.onProductCreated(product);
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


        return view;
    }

    private void spinnerToko(){
        tokoQuery = new TokoQuery(this.getContext());
        tokoList.addAll(tokoQuery.getAllToko());

        ArrayList<String> label = new ArrayList<>();

        for (int i =0;i<tokoList.size();i++){
            label.add(tokoList.get(i).getTokoName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, label);

        editProductToko.setAdapter(adapter);
        tokoString = adapter.getItem(0);
        // mengeset listener untuk mengetahui saat item dipilih
        editProductToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tokoString = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerCategory(){
        categoryProductQuery = new CategoryProductQuery(this.getContext());
        categoryList.addAll(categoryProductQuery.getAllCategoryProduct());

        ArrayList<String> label1 = new ArrayList<>();

        for (int i =0;i<categoryList.size();i++){
            label1.add(categoryList.get(i).getCategoryName());
        }

        final ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, label1);

        editProductCategory.setAdapter(adapter1);
        categoryString = adapter1.getItem(0);
        editProductCategory.setSelection(0, true);
        View v = editProductCategory.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        editProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryString = adapter1.getItem(i);
                ((TextView) view).setTextColor(getResources().getColor(R.color.md_black_1000));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
