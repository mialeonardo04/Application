package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddStuff extends Fragment {
    protected Cursor cursor;
    TextView txtId;
    DataHelper dbHelper;
    Button save, cancel;
    EditText namaBrg, hargaBrg, tgl_datang, tgl_expired, jumlah_brg, ket;
    String defaultIdBarang = null;


    public FragmentAddStuff() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_stuff, container, false);
        txtId = v.findViewById(R.id.id_barang);
        Button btnScan = v.findViewById(R.id.scanID);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getContext(), CaptureActivity.class);
                CaptureActivityIntents.setPromptMessage(capture, "Scanning");
                startActivityForResult(capture, 0);
            }
        });

        dbHelper = new DataHelper(getActivity());
        namaBrg = v.findViewById(R.id.nama);
        hargaBrg = v.findViewById(R.id.harga);
        tgl_datang = v.findViewById(R.id.tgl_masuk);
        tgl_expired = v.findViewById(R.id.tgl_expired);
        jumlah_brg = v.findViewById(R.id.jumlah);
        ket = v.findViewById(R.id.et);
        save = v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                //SQLiteDatabase dbRead = dbHelper.getReadableDatabase();
                String sqlInsertBarang = "insert into barang(id_barang, nama_barang, jumlah, harga_barang, keterangan) values('" +
                        defaultIdBarang + "','" +
                        namaBrg.getText().toString() + "','" +
                        jumlah_brg.getText().toString() + "','" +
                        hargaBrg.getText().toString() + "','" +
                        ket.getText().toString() + "')";
                //cursor = dbRead.rawQuery("SELECT ",null);
                String sqlInsertStock = "insert into stock(id_stock, id_barang, tgl_datang, tgl_kadaluarsa) values('" +
                        txtId.getText().toString() + "','','" +
                        hargaBrg.getText().toString() + "','" +
                        ket.getText().toString() + "')";
                db.execSQL(sqlInsertBarang);
            }
        });
        cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment3 = new FragmentThree();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode, resultCode, data);*/
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String val = data.getStringExtra("SCAN_RESULT");
                txtId.setText(val);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
