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

import com.example.sqlitetugas3.Adapter.PegawaiAdapter;
import com.example.sqlitetugas3.Adapter.ProductAdapter;
import com.example.sqlitetugas3.Create.PegawaiCreateDialogFragment;
import com.example.sqlitetugas3.Create.PegawaiCreateListener;
import com.example.sqlitetugas3.Create.ProductCreateListener;
import com.example.sqlitetugas3.Database.PegawaiQuery;
import com.example.sqlitetugas3.Database.ProductQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.Util.DrawerMenu;
import com.example.sqlitetugas3.pojo.Pegawai;
import com.example.sqlitetugas3.pojo.Product;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class PegawaiActivity extends AppCompatActivity implements PegawaiCreateListener {

    private PegawaiQuery pegawaiQuery = new PegawaiQuery(this);

    private List<Pegawai> pegawaiList = new ArrayList<>();

    private TextView pegawaiEmpty;
    private RecyclerView recyclerView;
    private PegawaiAdapter pegawaiAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pegawai);

        Toolbar toolbar = (Toolbar) findViewById(R.id.pegawai_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Pegawai");
        DrawerMenu drawer = new DrawerMenu();
        drawer.createDrawer(this, this, toolbar);

        Logger.addLogAdapter(new AndroidLogAdapter());

        recyclerView = (RecyclerView) findViewById(R.id.pegawai_rv);
        pegawaiEmpty = (TextView) findViewById(R.id.pegawai_empty_rv);

        pegawaiList.addAll(pegawaiQuery.getAllPegawai());

        pegawaiAdapter = new PegawaiAdapter(this, pegawaiList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(pegawaiAdapter);

        viewVisibility();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.pegawai_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPegawaiCreateDialog();
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
            alertDialogBuilder.setMessage("Yakin ingin menghapus semua Pegawai?");
            alertDialogBuilder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            boolean isAllDeleted = pegawaiQuery.deleteAllPegawai();
                            if(isAllDeleted){
                                pegawaiList.clear();
                                pegawaiAdapter.notifyDataSetChanged();
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
        if(pegawaiList.isEmpty())
            pegawaiEmpty.setVisibility(View.VISIBLE);
        else
            pegawaiEmpty.setVisibility(View.GONE);
    }

    private void openPegawaiCreateDialog() {
        PegawaiCreateDialogFragment pegawaiCreateDialogFragment = PegawaiCreateDialogFragment.newInstance("Create Pegawai", this);
        pegawaiCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_PEGAWAI);
    }

    @Override
    public void onPegawaiCreated(Pegawai pegawai) {
        pegawaiList.add(pegawai);
        pegawaiAdapter.notifyDataSetChanged();
        viewVisibility();
        Logger.d(pegawai.getPegawaiName());
    }
}
