package com.example.sqlitetugas3.Update;

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

import com.example.sqlitetugas3.Database.CategoryProductQuery;
import com.example.sqlitetugas3.Database.ProductQuery;
import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.pojo.CategoryProduct;
import com.example.sqlitetugas3.pojo.Product;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Toko;

import java.util.ArrayList;
import java.util.List;


public class ProductUpdateDialogFragment extends DialogFragment {

    private static long idProduct;
    private static int productItemPosition;
    private static ProductUpdateListener productUpdateListener;

    private Product mProduct;
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
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "", tokoString = "", categoryString = "",merekString = "",descString = "";
    private int hargaInt = 0, qtyInt = 0;

    private ProductQuery productQuery;

    public ProductUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static ProductUpdateDialogFragment newInstance(long id, int position, ProductUpdateListener listener){
        idProduct = id;
        productItemPosition = position;
        productUpdateListener = listener;
        ProductUpdateDialogFragment productUpdateDialogFragment = new ProductUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Product");
        productUpdateDialogFragment.setArguments(args);

        productUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return productUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_product_update_dialog, container, false);

        productQuery = new ProductQuery(getContext());

        editProductName = view.findViewById(R.id.update_product_name);
        editProductMerek = view.findViewById(R.id.update_product_merk);
        editProductCategory = view.findViewById(R.id.update_product_categoryProduct);
        editProductToko = view.findViewById(R.id.update_product_toko);
        editProductDescription = view.findViewById(R.id.update_product_desc);
        editProductHarga = view.findViewById(R.id.update_harga);
        editProductQty = view.findViewById(R.id.update_qty);

        updateButton = view.findViewById(R.id.fragment_product_updateButton);
        cancelButton = view.findViewById(R.id.fragment_product_updateCancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        mProduct = productQuery.getProductByID(idProduct);
        spinnerCategory();
        spinnerToko();

        if(mProduct !=null){
            editProductName.setText(mProduct.getProductName());
            editProductMerek.setText(mProduct.getProductMerk());
            editProductDescription.setText(mProduct.getProductDesc());
            editProductHarga.setText(String.valueOf(mProduct.getProductHarga()));
            editProductQty.setText(String.valueOf(mProduct.getProductQty()));

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = editProductName.getText().toString();
                    merekString = editProductMerek.getText().toString();
                    descString = editProductDescription.getText().toString();
                    hargaInt = Integer.parseInt(editProductHarga.getText().toString());
                    qtyInt = Integer.parseInt(editProductQty.getText().toString());

                    mProduct.setProductName(nameString);
                    mProduct.setProductMerk(merekString);
                    mProduct.setProductCategory(categoryString);
                    mProduct.setProductToko(tokoString);
                    mProduct.setProductDesc(descString);
                    mProduct.setProductHarga(hargaInt);
                    mProduct.setProductQty(qtyInt);

                    long id = productQuery.updateProduct(mProduct);

                    if(id>0){
                        productUpdateListener.onProductInfoUpdated(mProduct, productItemPosition);
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

    private void spinnerToko(){
        tokoQuery = new TokoQuery(this.getContext());
        tokoList.addAll(tokoQuery.getAllToko());

        ArrayList<String> label = new ArrayList<>();

        for (int i =0;i<tokoList.size();i++){
            label.add(tokoList.get(i).getTokoName());
            if (mProduct.getProductToko().equals(tokoList.get(i).getTokoName())){
                editProductToko.setSelection(i);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, label);

        editProductToko.setAdapter(adapter);
        tokoString = mProduct.getProductToko();
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

        ArrayList<String> label = new ArrayList<>();

        for (int i =0;i<categoryList.size();i++){
            label.add(categoryList.get(i).getCategoryName());
            if (mProduct.getProductCategory().equals(categoryList.get(i).getCategoryName())){
                editProductCategory.setSelection(i);

            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, label);

        editProductCategory.setAdapter(adapter);
        categoryString = mProduct.getProductCategory();
        // mengeset listener untuk mengetahui saat item dipilih
        editProductCategory.setSelection(0, true);
        View v = editProductCategory.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));

        editProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryString = adapter.getItem(i);
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
