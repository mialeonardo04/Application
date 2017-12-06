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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddStock extends Fragment {
    protected Cursor cursor;
    TextView txtIdStock;
    DataHelper dbHelper;
    Button save, cancel;
    Spinner id_barang;
    EditText tgl_datang, tgl_expired;
    String defaultIdBarang = null;


    public FragmentAddStock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_stock, container, false);
       /* Spinner spinner = v.findViewById(R.id.selectIDBarang);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        Button btnScan = v.findViewById(R.id.scanStockID);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getContext(), CaptureActivity.class);
                CaptureActivityIntents.setPromptMessage(capture, "Scanning");
                startActivityForResult(capture, 0);
            }
        });

        dbHelper = new DataHelper(getActivity());
        txtIdStock = v.findViewById(R.id.id_stock);
        id_barang = v.findViewById(R.id.selectIDBarang);
        tgl_datang = v.findViewById(R.id.tgl_masuk);
        tgl_expired = v.findViewById(R.id.tgl_kadaluarsa);

        save = v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String sqlInsertStock = "insert into stock(id_stock, id_barang, tgl_datang, tgl_kadaluarsa) values('" +
                        txtIdStock.getText().toString() + "','" +
                        id_barang.getSelectedItem().toString() + "','" +
                        tgl_datang.getText().toString() + "','" +
                        tgl_expired.getText().toString() + "')";
                db.execSQL(sqlInsertStock);
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
                txtIdStock.setText(val);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
