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

import com.example.sqlitetugas3.Adapter.DevisiAdapter;
import com.example.sqlitetugas3.Create.DevisiCreateDialogFragment;
import com.example.sqlitetugas3.Create.DevisiCreateListener;
import com.example.sqlitetugas3.Database.DevisiQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.Util.DrawerMenu;
import com.example.sqlitetugas3.pojo.Devisi;
import com.example.sqlitetugas3.pojo.Product;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class DevisiActivity extends AppCompatActivity implements DevisiCreateListener {

    private DevisiQuery devisiQuery = new DevisiQuery(this);

    private List<Devisi> devisiList = new ArrayList<>();

    private TextView devisiEmpty;
    private RecyclerView recyclerView;
    private DevisiAdapter devisiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devisi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.devisi_toolbar);
        toolbar.setTitle("Devisi");
        setSupportActionBar(toolbar);

        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, toolbar);

        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.devisi_rv);
        devisiEmpty = (TextView) findViewById(R.id.devisi_empty_rv);

        devisiList.addAll(devisiQuery.getAllDevisi());

        devisiAdapter = new DevisiAdapter(this, devisiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(devisiAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.devisi_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDevisiCreateDialog();
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
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Devisi?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = devisiQuery.deleteAllDevisi();
                            if(isAllDeleted){
                                devisiList.clear();
                                devisiAdapter.notifyDataSetChanged();
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
        if(devisiList.isEmpty())
            devisiEmpty.setVisibility(View.VISIBLE);
        else
            devisiEmpty.setVisibility(View.GONE);
    }

    private void openDevisiCreateDialog() {
        DevisiCreateDialogFragment devisiCreateDialogFragment = DevisiCreateDialogFragment.newInstance("Create Devisi", this);
        devisiCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_DEVISI);
    }

    @Override
    public void onDevisiCreated(Devisi devisi) {
        devisiList.add(devisi);
        devisiAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(devisi.getDevisiName());
    }
}
