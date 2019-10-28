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

import com.example.sqlitetugas3.Adapter.TokoAdapter;
import com.example.sqlitetugas3.Create.TokoCreateDialogFragment;
import com.example.sqlitetugas3.Create.TokoCreateListener;
import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.Util.DrawerMenu;
import com.example.sqlitetugas3.pojo.Toko;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TokoActivity extends AppCompatActivity implements TokoCreateListener {

    private TokoQuery tokoQuery = new TokoQuery(this);

    private List<Toko> tokoList = new ArrayList<>();

    private TextView tokoEmpty;
    private RecyclerView recyclerView;
    private TokoAdapter tokoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toko);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toko_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Toko");
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, toolbar);

        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.toko_rv);
        tokoEmpty = (TextView) findViewById(R.id.toko_empty_rv);

        tokoList.addAll(tokoQuery.getAllToko());

        tokoAdapter = new TokoAdapter(this, tokoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(tokoAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.toko_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTokoCreateDialog();
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
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Toko?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = tokoQuery.deleteAllToko();
                            if(isAllDeleted){
                                tokoList.clear();
                                tokoAdapter.notifyDataSetChanged();
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
        if(tokoList.isEmpty())
            tokoEmpty.setVisibility(View.VISIBLE);
        else
            tokoEmpty.setVisibility(View.GONE);
    }

    private void openTokoCreateDialog() {
        TokoCreateDialogFragment tokoCreateDialogFragment = TokoCreateDialogFragment.newInstance("Create Toko", this);
        tokoCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_TOKO);
    }

    @Override
    public void onTokoCreated(Toko toko) {
        tokoList.add(toko);
        tokoAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(toko.getTokoName());
    }
}
