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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sqlitetugas3.Database.DevisiQuery;
import com.example.sqlitetugas3.Database.PegawaiQuery;
import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.CategoryProduct;
import com.example.sqlitetugas3.pojo.Devisi;
import com.example.sqlitetugas3.pojo.Pegawai;
import com.example.sqlitetugas3.pojo.Toko;

import java.util.ArrayList;
import java.util.List;


public class PegawaiCreateDialogFragment extends DialogFragment {
    private static PegawaiCreateListener pegawaiCreateListener;

    private List<Toko> tokoList = new ArrayList<>();
    private List<Devisi> devisiList = new ArrayList<>();

    TokoQuery tokoQuery;
    DevisiQuery devisiQuery;
    PegawaiQuery pegawaiQuery;


    private EditText editName,editAlamat,editNoTelp;
    private Spinner editToko,editDevisi;
    private Button createButton;
    private Button cancelButton;

    private String nameString = "", tokoString = "",devisiString ="",alamatString = "",noTelpString = "";

    public PegawaiCreateDialogFragment() {
        // Required empty public constructor
    }

    public static PegawaiCreateDialogFragment newInstance(String title, PegawaiCreateListener listener){
        pegawaiCreateListener = listener;
        PegawaiCreateDialogFragment pegawaiCreateDialogFragment = new PegawaiCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        pegawaiCreateDialogFragment.setArguments(args);
        pegawaiCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return pegawaiCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pegawai_create_dialog, container, false);

        editName = view.findViewById(R.id.create_pegawai_name);
        editToko = view.findViewById(R.id.create_pegawai_toko);
        editDevisi = view.findViewById(R.id.create_pegawai_devisi);
        editAlamat = view.findViewById(R.id.create_pegawai_alamat);
        editNoTelp = view.findViewById(R.id.create_pegawai_notelp);
        createButton = view.findViewById(R.id.fragment_pegawai_createButton);
        cancelButton = view.findViewById(R.id.fragment_pegawai_createCancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        spinnerDevisi();
        spinnerToko();

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void save(){
        nameString = editName.getText().toString();
        alamatString = editAlamat.getText().toString();
        noTelpString = editNoTelp.getText().toString();

        Pegawai pegawai = new Pegawai(-1, tokoString,devisiString, nameString, alamatString, noTelpString);

        pegawaiQuery = new PegawaiQuery(getContext());

        long id = pegawaiQuery.insertPegawai(pegawai);

        if(id>0){
            pegawai.setId(id);
            pegawaiCreateListener.onPegawaiCreated(pegawai);
            getDialog().dismiss();
        }
    }

    private void spinnerToko(){
        tokoQuery = new TokoQuery(this.getContext());
        tokoList.addAll(tokoQuery.getAllToko());

        ArrayList<String> label = new ArrayList<>();

        for (int i =0;i<tokoList.size();i++){
            label.add(tokoList.get(i).getTokoName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, label);

        editToko.setAdapter(adapter);
        tokoString = adapter.getItem(0);
        // mengeset listener untuk mengetahui saat item dipilih
        editToko.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tokoString = adapter.getItem(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void spinnerDevisi(){
        devisiQuery = new DevisiQuery(this.getContext());
        devisiList.addAll(devisiQuery.getAllDevisi());

        ArrayList<String> label = new ArrayList<>();

        for (int i =0;i<devisiList.size();i++){
            label.add(devisiList.get(i).getDevisiName());
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, label);

        editDevisi.setAdapter(adapter);
        devisiString = adapter.getItem(0);
        editDevisi.setSelection(0, true);
        View v = editDevisi.getSelectedView();
        ((TextView)v).setTextColor(getResources().getColor(R.color.md_black_1000));
        // mengeset listener untuk mengetahui saat item dipilih
        editDevisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                devisiString = adapter.getItem(i);
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
