package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

import java.util.Arrays;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    public static FragmentTwo ma;
    protected Cursor cursor;
    DataHelper dbCenter = new DataHelper(getActivity());
    TextView id, nama, harga;
    TableLayout tl;
    TableRow tr;
    String scanResult = null;
    String[] daftarid, daftarnama, daftarharga;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);
        tl = v.findViewById(R.id.tl1);
        tl.setColumnStretchable(0, true);
        tl.setColumnStretchable(1, true);
        tl.setColumnStretchable(2, true);

        Button btnScan = v.findViewById(R.id.bt_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getContext(),CaptureActivity.class);
                CaptureActivityIntents.setPromptMessage(capture,"Scanning");
                startActivityForResult(capture,0);
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK && data != null){
                String val = data.getStringExtra("SCAN_RESULT");

                scanResult = val;

                tr = new TableRow(getActivity());
                id = new TextView(getActivity());
                nama = new TextView(getActivity());
                harga = new TextView(getActivity());

                //3 baris setelah komen ini buat ngerefresh database nya
                ma = this;
                dbCenter = new DataHelper(getActivity());
                RefreshList();
                //yg diatas paling penting bro

                id.setText(Arrays.toString(daftarid).replaceAll("\\[|\\]", ""));
                id.setTextSize(15);
                id.setGravity(Gravity.CENTER);

                nama.setText(Arrays.toString(daftarnama).replaceAll("\\[|\\]", ""));
                nama.setTextSize(15);
                nama.setGravity(Gravity.CENTER);

                harga.setText(Arrays.toString(daftarharga).replaceAll("\\[|\\]", ""));
                harga.setTextSize(15);
                harga.setGravity(Gravity.CENTER);

                tr.addView(id);
                tr.addView(nama);
                tr.addView(harga);

                tl.addView(tr);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {}

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void RefreshList() {
        SQLiteDatabase db = dbCenter.getReadableDatabase();
        String sql = "SELECT * FROM barang, stock WHERE stock.id_stock = '" +
                scanResult + "' AND barang.id_barang = stock.id_barang";

        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();

        daftarid = new String[cursor.getCount()];
        daftarnama = new String[cursor.getCount()];
        daftarharga = new String[cursor.getCount()];

        for (int r = 0; r < cursor.getCount(); r++) {
            cursor.moveToPosition(r);
            daftarid[r] = cursor.getString(5);
            daftarnama[r] = cursor.getString(1);
            daftarharga[r] = cursor.getString(3);
        }
    }
}
