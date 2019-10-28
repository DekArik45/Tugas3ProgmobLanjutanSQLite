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

import com.example.sqlitetugas3.Database.TokoQuery;
import com.example.sqlitetugas3.R;
import com.example.sqlitetugas3.Util.Config;
import com.example.sqlitetugas3.pojo.Toko;

public class TokoUpdateDialogFragment extends DialogFragment {

    private static long idToko;
    private static int tokoItemPosition;
    private static TokoUpdateListener tokoUpdateListener;

    private Toko toko;
    private TokoQuery tokoQuery;

    private EditText editName, editAlamat, editNoTelp;
    private Button updateButton;
    private Button cancelButton;

    private String nameString = "", alamatString = "", noTelpString = "";

    public TokoUpdateDialogFragment() {
        // Required empty public constructor
    }

    public static TokoUpdateDialogFragment newInstance(long id, int position, TokoUpdateListener listener){
        idToko = id;
        tokoItemPosition = position;
        tokoUpdateListener = listener;
        TokoUpdateDialogFragment tokoUpdateDialogFragment = new TokoUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update Toko");
        tokoUpdateDialogFragment.setArguments(args);

        tokoUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return tokoUpdateDialogFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_toko_update_dialog, container, false);

        tokoQuery = new TokoQuery(getContext());

        editName = view.findViewById(R.id.update_toko_name);
        editAlamat = view.findViewById(R.id.update_toko_alamat);
        editNoTelp = view.findViewById(R.id.update_toko_notelp);
        updateButton = view.findViewById(R.id.fragment_toko_updateButton);
        cancelButton = view.findViewById(R.id.fragment_toko_updateCancelButton);


        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        toko = tokoQuery.getTokoByID(idToko);

        if(toko !=null){
            editName.setText(toko.getTokoName());
            editAlamat.setText(toko.getTokoAlamat());
            editNoTelp.setText(toko.getTokoNoTelp());

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nameString = editName.getText().toString();
                    alamatString = editAlamat.getText().toString();
                    noTelpString = editNoTelp.getText().toString();

                    toko.setTokoName(nameString);
                    toko.setTokoAlamat(alamatString);
                    toko.setTokoNoTelp(noTelpString);

                    long id = tokoQuery.updateToko(toko);

                    if(id>0){
                        tokoUpdateListener.onTokoUpdated(toko, tokoItemPosition);
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
