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

import com.example.sqlitetugas3.Database.DevisiQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Devisi;

public class DevisiCreateDialogFragment extends DialogFragment {
    private static DevisiCreateListener devisiCreateListener;

    private DevisiQuery devisiQuery;

    private EditText editName;
    private Button createBtn, cancelBtn;
    private String nameString = "";

    public DevisiCreateDialogFragment() {
        // Required empty public constructor
    }

    public static DevisiCreateDialogFragment newInstance(String title, DevisiCreateListener listener) {
        devisiCreateListener = listener;
        DevisiCreateDialogFragment devisiCreateDialogFragment = new DevisiCreateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        devisiCreateDialogFragment.setArguments(args);
        devisiCreateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return devisiCreateDialogFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_devisi_create_dialog, container, false);

        editName = view.findViewById(R.id.create_devisi_nama);
        createBtn = view.findViewById(R.id.fragment_devisi_createButton);
        cancelBtn = view.findViewById(R.id.fragment_devisi_createCancelButton);

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

        Devisi devisi = new Devisi(-1,nameString);

        devisiQuery = new DevisiQuery(getContext());

        long id = devisiQuery.insertDevisi(devisi);

        if(id>0){
            devisi.setId(id);
            devisiCreateListener.onDevisiCreated(devisi);
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
