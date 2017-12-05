package com.example.ignasiusleo.application.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ignasiusleo on 05/12/17.
 */

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mokas.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlBarang = "create table barang(id_barang text primary key,nama_barang text null, harga_barang integer null, t";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
