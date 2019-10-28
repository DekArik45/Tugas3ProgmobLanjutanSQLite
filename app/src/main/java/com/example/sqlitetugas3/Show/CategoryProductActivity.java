package com.example.sqlitetugas3.Show;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.sqlitetugas3.Adapter.CategoryProductAdapater;
import com.example.sqlitetugas3.Create.CategoryProductCreateDialogFragment;
import com.example.sqlitetugas3.Create.CategoryProductCreateListener;
import com.example.sqlitetugas3.Database.CategoryProductQuery;
import com.example.sqlitetugas3.Database.ProductQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.Util.DrawerMenu;
import com.example.sqlitetugas3.pojo.CategoryProduct;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductActivity extends AppCompatActivity implements CategoryProductCreateListener {

    private CategoryProductQuery categoryProductQuery = new CategoryProductQuery(this);

    private List<CategoryProduct> categoryProductList = new ArrayList<>();

    private TextView categoryProductEmpty;
    private RecyclerView recyclerView;
    private CategoryProductAdapater categoryProductAdapater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_product);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_product_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Category Product");
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, toolbar);

        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.category_product_rv);
        categoryProductEmpty = (TextView) findViewById(R.id.category_product_empty_rv);

        categoryProductList.addAll(categoryProductQuery.getAllCategoryProduct());

        categoryProductAdapater = new CategoryProductAdapater(this, categoryProductList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(categoryProductAdapater);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.category_product_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategoryProductCreateDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.action_delete){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Category Product?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = categoryProductQuery.deleteAllCategoryProduct();
                            if(isAllDeleted){
                                categoryProductList.clear();
                                categoryProductAdapater.notifyDataSetChanged();
                                viewVisibility();
                            }
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

        return super.onOptionsItemSelected(item);
    }

    public void viewVisibility() {
        if(categoryProductList.isEmpty())
            categoryProductEmpty.setVisibility(View.VISIBLE);
        else
            categoryProductEmpty.setVisibility(View.GONE);
    }

    private void openCategoryProductCreateDialog() {
        CategoryProductCreateDialogFragment categoryProductCreateDialogFragment = CategoryProductCreateDialogFragment.newInstance("Create Category Product", this);
        categoryProductCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_CATEGORY_PRODUCT);
    }

    @Override
    public void onCategoryProductCreated(CategoryProduct categoryProduct) {
        categoryProductList.add(categoryProduct);
        categoryProductAdapater.notifyDataSetChanged();
        viewVisibility();
        Logger.d(categoryProduct.getCategoryName());
    }
}
