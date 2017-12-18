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

import java.util.ArrayList;
import java.util.Arrays;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrans extends Fragment {


    public static FragmentTrans ma;
    protected Cursor cursor;
    DataHelper dbCenter = new DataHelper(getActivity());
    TextView id, nama, harga, scr3, scr4, scr5, txt;
    Button btn1, btn2, submit, hsl;
    Boolean stat = null;
    Integer rw = 0;
    TableLayout tl;
    TableRow tr;
    Integer hasil = 0;
    String scanResult = null;
    String[] daftaridstock, daftarnama, daftarharga;
    ArrayList sum = new ArrayList();
    public FragmentTrans() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_trans, container, false);
        tl = v.findViewById(R.id.tl1);
        tl.setColumnStretchable(0, true);
        tl.setColumnStretchable(1, true);
        tl.setColumnStretchable(2, true);
        tl.setColumnStretchable(3, true);
        tl.setColumnStretchable(4, true);
        tl.setColumnStretchable(5, true);

        txt = v.findViewById(R.id.ceng);
        btn1 = v.findViewById(R.id.btnMinus);
        btn2 = v.findViewById(R.id.btnPlus);
        submit = v.findViewById(R.id.bt_submit);
        hsl = v.findViewById(R.id.bt_total);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = (TextView) tr.getChildAt(2);
                Integer ceng = Integer.parseInt(txt.getText().toString());
                txt = (TextView) tr.getChildAt(3);
                Integer ceng2 = Integer.parseInt(txt.getText().toString());

                hasil = ceng * ceng2;
                sum.add(hasil);
                submit.setEnabled(false);
                scr4.setEnabled(false);
                scr5.setEnabled(false);
            }
        });

        hsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cek = 0;
                for (int i = 0; i < sum.size(); i++) {
                    cek += (int) sum.get(i);
                }
                Toast.makeText(getActivity(), String.valueOf(cek), Toast.LENGTH_LONG).show();
            }
        });

        Button btnScan = v.findViewById(R.id.bt_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getActivity(), CaptureActivity.class);
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
                if (rw == 0) {
                    newRow();
                    rw++;
                } else {
                    scr5.setEnabled(false);
                    scr4.setEnabled(false);
                    newRow();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {}

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void newRow() {
        int i = 0;

        tr = new TableRow(getActivity());
        id = new TextView(getActivity());
        nama = new TextView(getActivity());
        harga = new TextView(getActivity());
        scr3 = new TextView(getActivity());
        scr4 = new Button(getActivity());
        scr5 = new Button(getActivity());

        //3 baris setelah komen ini buat ngerefresh database nya
        ma = this;
        dbCenter = new DataHelper(getActivity());
        RefreshList();
        //yg diatas paling penting bro

        id.setText(Arrays.toString(daftaridstock).replaceAll("\\[|\\]", ""));
        id.setTextSize(15);
        id.setGravity(Gravity.CENTER);

        nama.setText(Arrays.toString(daftarnama).replaceAll("\\[|\\]", ""));
        nama.setTextSize(15);
        nama.setGravity(Gravity.CENTER);

        harga.setText(Arrays.toString(daftarharga).replaceAll("\\[|\\]", ""));
        harga.setTextSize(15);
        harga.setGravity(Gravity.CENTER);

        scr3.setText("1");
        scr3.setTextSize(15);
        scr3.setId(i);
        scr3.setGravity(Gravity.CENTER);

        scr4.setText("-");
        scr4.setId(R.id.btnMinus);
        scr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(scr3.getText().toString()) > 1) {
                    Integer i = Integer.parseInt(scr3.getText().toString()) - 1;
                    scr3.setText(i.toString());
                } else {
                    Toast.makeText(getActivity(), "Tidak bisa dikurangi", Toast.LENGTH_SHORT).show();
                }
            }
        });
        scr5.setText("+");
        scr5.setId(i);
        scr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(scr3.getText().toString()) > 0) {
                    Integer i = Integer.parseInt(scr3.getText().toString()) + 1;
                    scr3.setText(i.toString());
                }
            }
        });

        if (stat) {
            tr.addView(id);
            tr.addView(nama);
            tr.addView(harga);
            tr.addView(scr3);
            tr.addView(scr4);
            tr.addView(scr5);

            tl.addView(tr);
        } else {
            Toast.makeText(getActivity(), "Barang tidak ditemukan, tolong masukkan data barang!", Toast.LENGTH_LONG).show();
        }

    }

    public void RefreshList() {
        SQLiteDatabase db = dbCenter.getReadableDatabase();
        String sql = "SELECT * FROM barang, stock WHERE stock.id_stock = '" +
                scanResult + "' AND barang.id_barang = stock.id_barang";

        cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            daftaridstock = new String[cursor.getCount()];
            daftarnama = new String[cursor.getCount()];
            daftarharga = new String[cursor.getCount()];

            for (int r = 0; r < cursor.getCount(); r++) {
                cursor.moveToPosition(r);
                daftaridstock[r] = cursor.getString(5);
                daftarnama[r] = cursor.getString(1);
                daftarharga[r] = cursor.getString(3);
            }
            stat = true;
            submit.setEnabled(true);
        } else {
            stat = false;
            submit.setEnabled(false);
        }

    }
}
