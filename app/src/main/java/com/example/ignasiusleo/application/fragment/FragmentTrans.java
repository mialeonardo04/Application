package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTrans extends Fragment {


    public static FragmentTrans ma;
    protected Cursor cursor;
    Integer selisih;
    DataHelper dbCenter = new DataHelper(getActivity());
    TextView id, nama, harga, scr3, scr4, scr5, txt, txtTotal;
    Button btn1, btn2, submit, hsl, byr;
    Boolean stat = null;
    Integer rw = 0;
    TableLayout tl;
    TableRow tr;
    Integer hasil = 0;
    Integer qtyDB, qtyBeli;
    String simpanIdBarang, simpanIdStock = null;
    String tvHrg, tvByr, tvCashBack, s1, s2, s3, s4, s5, s6 = null;
    String scanResult = null;
    String[] daftaridstock, daftarnama, daftarharga, find, find2;
    ArrayList sum = new ArrayList();

    public FragmentTrans() {
        // Required empty public constructor
    }

    protected void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);

        dialog.setTitle("MoKas v0.0.19");
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_payment, null);
        dialog.setContentView(view);

        TextView tvTotHarga = view.findViewById(R.id.total_harga);
        final TextView tvKembali = view.findViewById(R.id.cashback);
        final EditText etBayar = view.findViewById(R.id.cash);
        Button submitPayment = view.findViewById(R.id.submit);

        tvTotHarga.setText("Rp. " + tvHrg + ",-");


        submitPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvByr = etBayar.getText().toString();
                Integer cash = Integer.parseInt(tvByr);
                Integer sum = Integer.parseInt(tvHrg);
                Integer hitung = cash - sum;
                if (hitung < 0) {
                    Toast.makeText(getActivity(), "uang yang dibayar harus lebih dari Rp." +
                                    tvHrg + ",-",
                            Toast.LENGTH_SHORT).show();
                } else if (hitung >= 0) {
                    tvCashBack = String.valueOf(hitung);
                    tvKembali.setText(tvCashBack);
                    logTransaksi(tvCashBack);
                    dialog.dismiss();
                }
                //Toast.makeText(getActivity(),tvByr,Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();

    }

    public void prosesPembelian() {
        //SQLiteDatabase dbWrite = dbCenter.getWritableDatabase();

        SQLiteDatabase dbRead = dbCenter.getReadableDatabase();
        txt = (TextView) tr.getChildAt(0);
        String idStock = txt.getText().toString();
        simpanIdStock = idStock;
        String sql1 = "SELECT id_barang FROM stock WHERE id_stock = '" +
                idStock + "';";

        cursor = dbRead.rawQuery(sql1, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            find = new String[cursor.getCount()];

            for (int z = 0; z < cursor.getCount(); z++) {
                cursor.moveToPosition(z);
                find[z] = cursor.getString(0);

            }

        }
        String hasilFind = Arrays.toString(find).replaceAll("\\[|\\]", "");
        simpanIdBarang = hasilFind;
        String sql2 = "SELECT jumlah FROM barang WHERE id_barang = '" +
                hasilFind + "';";
        cursor = dbRead.rawQuery(sql2, null);
        cursor.moveToFirst();
        if (cursor.getCount() != 0) {
            find2 = new String[cursor.getCount()];

            for (int hs = 0; hs < cursor.getCount(); hs++) {
                cursor.moveToPosition(hs);
                find2[hs] = cursor.getString(0);
            }
        }
        String getJumlah = Arrays.toString(find2).replaceAll("\\[|\\]", "");
        txt = (TextView) tr.getChildAt(3);
        qtyDB = Integer.parseInt(getJumlah);
        qtyBeli = Integer.parseInt(txt.getText().toString());
        if (qtyBeli > qtyDB) {
            if (qtyDB == 0) {
                Toast.makeText(getActivity(), "Barang habis", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Stock tidak cukup memenuhi pembelian", Toast.LENGTH_SHORT).show();
                tl.removeView(tr);
            }
        } else {
            Integer a = qtyDB - qtyBeli;
            selisih = a;
            String jmlUpdate = String.valueOf(selisih);
            SQLiteDatabase dbWrite = dbCenter.getWritableDatabase();
            String qUpdateJmlBrg = "UPDATE barang SET jumlah = '" +
                    jmlUpdate + "' WHERE id_barang = '" +
                    hasilFind + "';";
            dbWrite.execSQL(qUpdateJmlBrg);
            Toast.makeText(getActivity(), txt.getText().toString() + " barang terbeli", Toast.LENGTH_SHORT).show();
        }
    }

    public void logTransaksi(String a) {
        SQLiteDatabase dbWrite = dbCenter.getWritableDatabase();
        SQLiteDatabase dbRead = dbCenter.getReadableDatabase();

        Date currentTime = Calendar.getInstance().getTime();
        String format = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        String dateNow = sdf.format(currentTime);
        txt = (TextView) tr.getChildAt(3);


        String insertPenjualan = "INSERT INTO penjualan(id_barang, id_stock, jumlah_keluar) values ('" +
                simpanIdBarang + "', '" + simpanIdStock + "', '" + txt.getText().toString() + "' );";

        String insertTransaksi = "INSERT INTO transaksi(total_harga, tgl_transaksi) values ('" +
                tvHrg + "', '" + dateNow + "' );";

        dbWrite.execSQL(insertTransaksi);
        dbWrite.execSQL(insertPenjualan);
        Toast.makeText(getActivity(), "Data Transaksi berhasil diinput, Uang Kembali Rp." + a + ",-", Toast.LENGTH_LONG).show();
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

        txtTotal = v.findViewById(R.id.txtTotal);
        txt = v.findViewById(R.id.ceng);
        btn1 = v.findViewById(R.id.btnMinus);
        btn2 = v.findViewById(R.id.btnPlus);
        submit = v.findViewById(R.id.bt_submit);
        hsl = v.findViewById(R.id.bt_total);
        byr = v.findViewById(R.id.bt_bayar);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt = (TextView) tr.getChildAt(2);
                Integer price = Integer.parseInt(txt.getText().toString());
                txt = (TextView) tr.getChildAt(3);
                Integer qty = Integer.parseInt(txt.getText().toString());

                hasil = price * qty;
                sum.add(hasil);
                submit.setEnabled(false);
                scr4.setEnabled(false);
                scr5.setEnabled(false);
                prosesPembelian();
            }
        });

        hsl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cek = 0;
                for (int i = 0; i < sum.size(); i++) {
                    cek += (int) sum.get(i);
                }
                tvHrg = String.valueOf(cek);
                txtTotal.setText("Rp. " + tvHrg + ",-");
                byr.setEnabled(true);
            }
        });

        byr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog();
                byr.setEnabled(false);
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
