package com.example.ignasiusleo.application.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ignasiusleo on 05/12/17.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mokas.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABEL_BARANG = "barang";
    private static final String TABEL_STOCK = "stock";
    private static final String TABEL_PENJUALAN = "penjualan";
    private static final String TABEL_TRANSAKSI = "transaksi";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlBarang = "create table " + TABEL_BARANG + "(id_barang text PRIMARY KEY NOT NULL,nama_barang text null, harga_barang text null, keterangan text null);";
        String sqlStock = "create table " + TABEL_STOCK + "(id_stock INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,id_barang text PRIMARY KEY NOT NULL,jumlah text not nul, tgl_datang text null, tgl_kadaluarsa text null);";
        String sqlPenjualan = "create table " + TABEL_PENJUALAN + "(id_transaksi INTEGER not null,id_barang text not null, jumlah text null);";
        String sqlTransaksi = "create table " + TABEL_TRANSAKSI + "(id_transaksi INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, jumlah text null)";

        Log.d("Tabel Barang", "onCreate" + sqlBarang);
        Log.d("Tabel Stock", "onCreate" + sqlStock);
        Log.d("Tabel Penjualan", "onCreate" + sqlPenjualan);
        Log.d("Tabel Transaksi", "onCreate" + sqlTransaksi);

        sqLiteDatabase.execSQL(sqlBarang);
        sqLiteDatabase.execSQL(sqlStock);
        sqLiteDatabase.execSQL(sqlPenjualan);
        sqLiteDatabase.execSQL(sqlTransaksi);


    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
