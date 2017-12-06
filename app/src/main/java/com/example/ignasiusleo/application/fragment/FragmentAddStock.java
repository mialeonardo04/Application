package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;
import com.example.ignasiusleo.application.model.SpinnerObject;

import java.util.List;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddStock extends Fragment {
    protected Cursor cursor;
    protected Cursor cursor1;
    TextView txtIdStock;
    EditText tv;
    DataHelper dbHelper;
    Button save, cancel;
    Spinner id_barang;
    EditText tgl_datang, tgl_expired;
    String defaultIdBarang = null;
    String id;
    private int databaseId;

    public FragmentAddStock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_add_stock, container, false);

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

        showSpinner();

        id_barang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                /////////////////////
                int id_barang = databaseId;
                String idStr = String.valueOf(id_barang);
                //tv = v.findViewById(R.id.textSpinner);
                // tv.setText(idStr);
                Log.i("val selected", idStr);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //////////////////////
            }
        });
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

    public void showSpinner() {
        DataHelper db = new DataHelper(getActivity());
        List<SpinnerObject> labels = db.getAllLabels();
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_barang.setAdapter(dataAdapter);
        int getId = ((SpinnerObject) id_barang.getSelectedItem()).getDatabaseId();
        this.databaseId = getId;

        ////////////////////////////////
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
