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
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlitetugas3.Create.DevisiCreateListener;
import com.example.sqlitetugas3.Database.DevisiQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Devisi;

public class DevisiUpdateDialogFragment extends DialogFragment {

    private static long idDevisi;
    private static int devisiItemPosition;
    private static DevisiUpdateListener devisiUpdateListener;

    private Devisi devisi;
    private DevisiQuery devisiQuery;

    private EditText editName;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "";

    public DevisiUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static DevisiUpdateDialogFragment newInstance(long id, int position, DevisiUpdateListener listener){
        idDevisi = id;
        devisiItemPosition = position;
        devisiUpdateListener = listener;
        DevisiUpdateDialogFragment devisiUpdateDialogFragment = new DevisiUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Devisi");
        devisiUpdateDialogFragment.setArguments(args);

        devisiUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return devisiUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_devisi_update_dialog, container, false);

        devisiQuery = new DevisiQuery(getContext());

        editName = view.findViewById(R.id.update_devisi_nama);
        updateButton = view.findViewById(R.id.fragment_devisi_updateButton);
        cancelButton = view.findViewById(R.id.fragment_devisi_updateCancelButton);


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        devisi = devisiQuery.getDevisiByID(idDevisi);

        if(devisi !=null){
            editName.setText(devisi.getDevisiName());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = editName.getText().toString();

                    devisi.setDevisiName(nameString);

                    long id = devisiQuery.updateDevisi(devisi);

                    if(id>0){
                        devisiUpdateListener.onDevisiUpdated(devisi, devisiItemPosition);
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
