package com.example.ignasiusleo.application.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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
    private static final String CREATE_TABEL_BARANG = "CREATE TABLE " + TABEL_BARANG + " (" +
            "id_barang INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nama_barang VARCHAR (100), " +
            "jumlah INTEGER, " +
            "harga_barang INTEGER, " +
            "keterangan VARCHAR (100)" +
            ");";
    private static final String CREATE_TABEL_STOCK = "CREATE TABLE " + TABEL_STOCK + " (" +
            "id_stock VARCHAR (100) PRIMARY KEY, " +
            "id_barang INTEGER, " +
            "tgl_datang VARCHAR (100), " +
            "tgl_kadaluarsa VARCHAR (100)," +
            "FOREIGN KEY(id_barang) REFERENCES " + TABEL_BARANG + "(id_barang)" +
            ");";
    private static final String CREATE_TABEL_PENJUALAN = "CREATE TABLE " + TABEL_PENJUALAN + " (" +
            "id_transaksi INTEGER , " +
            "id_barang INTEGER , " +
            "id_stock VARCHAR (100)," +
            "jumlah_keluar INTEGER, " +
            "FOREIGN KEY(id_transaksi) REFERENCES " + TABEL_TRANSAKSI + "(id_transaksi)," +
            "FOREIGN KEY(id_barang) REFERENCES " + TABEL_BARANG + "(id_barang)," +
            "FOREIGN KEY(id_stock) REFERENCES " + TABEL_STOCK + "(id_stock)" +
            ");";
    private static final String CREATE_TABEL_TRANSAKSI = "CREATE TABLE " + TABEL_TRANSAKSI + " (" +
            "id_transaksi INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "total_harga INTEGER, " +
            "tgl_transaksi VARCHAR(100)" +
            ");";
    protected Cursor cursor;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<SpinnerObject> getAllLabels() {
        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();
        String selectQuery = "SELECT * FROM barang";
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        labels.add(new SpinnerObject("Choose item here"));
        if (cursor.moveToFirst()) {
            do {
                labels.add(new SpinnerObject(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return labels;
    }

    public List<SpinnerObject> getAllMonth() {
        List<SpinnerObject> labels = new ArrayList<SpinnerObject>();
        String selectQ = "SELECT * FROM transaksi;";
        SQLiteDatabase db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQ, null);


        labels.add(new SpinnerObject("Select Transaction Date below"));
        if (cursor.moveToFirst()) {
            do {
                labels.add(new SpinnerObject(cursor.getInt(0), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return labels;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.d("Tabel Barang", "onCreate" + CREATE_TABEL_BARANG);
        Log.d("Tabel Stock", "onCreate" + CREATE_TABEL_STOCK);
        Log.d("Tabel Penjualan", "onCreate" + CREATE_TABEL_PENJUALAN);
        Log.d("Tabel Transaksi", "onCreate" + CREATE_TABEL_TRANSAKSI);
        sqLiteDatabase.execSQL(CREATE_TABEL_BARANG);
        sqLiteDatabase.execSQL(CREATE_TABEL_STOCK);
        sqLiteDatabase.execSQL(CREATE_TABEL_PENJUALAN);
        sqLiteDatabase.execSQL(CREATE_TABEL_TRANSAKSI);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }
}
