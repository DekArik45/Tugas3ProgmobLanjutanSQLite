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
import android.widget.QuickContactBadge;

import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Toko;

public class TokoCreateDialogFragment extends DialogFragment {
    private static TokoCreateListener tokoCreateListener;

    private TokoQuery tokoQuery;

    private EditText editName, editAlamat, editNoTelp;
    private Button createBtn, cancelBtn;

    private String nameString = "", alamatString = "",noTelpString ="";

    public TokoCreateDialogFragment() {
        // Required empty public constructor
    }

    public static TokoCreateDialogFragment newInstance(String title, TokoCreateListener listener){
        tokoCreateListener = listener;
        TokoCreateDialogFragment tokoCreateDialogFragment = new TokoCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        tokoCreateDialogFragment.setArguments(args);
        tokoCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return tokoCreateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toko_create_dialog, container, false);

        editName = view.findViewById(R.id.create_toko_name);
        editAlamat = view.findViewById(R.id.create_toko_alamat);
        editNoTelp = view.findViewById(R.id.create_toko_notelp);

        createBtn = view.findViewById(R.id.fragment_toko_createButton);
        cancelBtn = view.findViewById(R.id.fragment_toko_createCancelButton);

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
        alamatString = editAlamat.getText().toString();
        noTelpString = editNoTelp.getText().toString();

        Toko toko = new Toko(-1, nameString, alamatString, noTelpString);

        tokoQuery = new TokoQuery(getContext());

        long id = tokoQuery.insertToko(toko);

        if(id>0){
            toko.setId(id);
            tokoCreateListener.onTokoCreated(toko);
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
