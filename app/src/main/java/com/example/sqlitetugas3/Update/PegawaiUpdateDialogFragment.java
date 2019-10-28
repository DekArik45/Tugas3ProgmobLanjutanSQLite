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
import com.example.sqlitetugas3.pojo.Devisi;
import com.example.sqlitetugas3.pojo.Pegawai;
import com.example.sqlitetugas3.pojo.Toko;

import java.util.ArrayList;
import java.util.List;


public class PegawaiUpdateDialogFragment extends DialogFragment {
    private static long idPegawaai;
    private static int pegawaiItemPosition;
    private static PegawaiUpdateListener pegawaiUpdateListener;

    private List<Toko> tokoList = new ArrayList<>();
    private List<Devisi> devisiList = new ArrayList<>();
    private Pegawai pegawai;

    private PegawaiQuery pegawaiQuery;
    TokoQuery tokoQuery;
     DevisiQuery devisiQuery;

    private EditText editName, editAlamat, editNoTelp;
    private Spinner editToko, editDevisi;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "", tokoString = "", devisiString = "",alamatString = "",noTelpString = "";

    public PegawaiUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static PegawaiUpdateDialogFragment newInstance(long id, int position, PegawaiUpdateListener listener){
        idPegawaai = id;
        pegawaiItemPosition = position;
        pegawaiUpdateListener = listener;
        PegawaiUpdateDialogFragment pegawaiUpdateDialogFragment = new PegawaiUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Pegawai");
        pegawaiUpdateDialogFragment.setArguments(args);

        pegawaiUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return pegawaiUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pegawai_update_dialog, container, false);

        pegawaiQuery = new PegawaiQuery(getContext());

        editName = view.findViewById(R.id.update_pegawai_name);
        editToko = view.findViewById(R.id.update_pegawai_toko);
        editDevisi = view.findViewById(R.id.update_pegawai_devisi);
        editAlamat = view.findViewById(R.id.update_pegawai_alamat);
        editNoTelp = view.findViewById(R.id.update_pegawai_notelp);

        updateButton = view.findViewById(R.id.fragment_pegawai_updateButton);
        cancelButton = view.findViewById(R.id.fragment_pegawai_updateCancelButton);

        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        pegawai = pegawaiQuery.getPegawaiByID(idPegawaai);
        spinnerDevisi();
        spinnerToko();

        if(pegawai !=null){
            editName.setText(pegawai.getPegawaiName());
//            editToko.setText(pegawai.getPegawaiToko());
//            editDevisi.setText(pegawai.getPegawaiDevisi());
            editAlamat.setText(pegawai.getPegawaiAlamat());
            editNoTelp.setText(pegawai.getPegawaiNoTelp());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = editName.getText().toString();
                    alamatString = editAlamat.getText().toString();
                    noTelpString = editNoTelp.getText().toString();

                    pegawai.setPegawaiName(nameString);
                    pegawai.setPegawaiToko(tokoString);
                    pegawai.setPegawaiDevisi(devisiString);
                    pegawai.setPegawaiAlamat(alamatString);
                    pegawai.setPegawaiNoTelp(noTelpString);

                    long id = pegawaiQuery.updatePegawai(pegawai);

                    if(id>0){
                        pegawaiUpdateListener.onPegawaiUpdated(pegawai, pegawaiItemPosition);
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
            if (pegawai.getPegawaiToko().equals(tokoList.get(i).getTokoName())){
                editToko.setSelection(i, true);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, label);

        editToko.setAdapter(adapter);
        tokoString = pegawai.getPegawaiToko();
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
            if (pegawai.getPegawaiDevisi().equals(devisiList.get(i).getDevisiName())){
                editDevisi.setSelection(i,true);
            }
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, label);

        editDevisi.setAdapter(adapter);
        devisiString = pegawai.getPegawaiDevisi();
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
