package com.example.ignasiusleo.application.model;

/**
 * Created by ignasiusleo on 03/12/17.
 */

public class Barang {
    private String id_stock;
    private String nama_barang;
    private int harga_barang;
    private int jumlah;
    private String date_come;
    private String date_expired;
    private String et;

    public String getId_stock() {
        return id_stock;
    }

    public void setId_stock(String id_stock) {
        this.id_stock = id_stock;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public int getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(int harga_barang) {
        this.harga_barang = harga_barang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getDate_come() {
        return date_come;
    }

    public void setDate_come(String date_come) {
        this.date_come = date_come;
    }

    public String getDate_expired() {
        return date_expired;
    }

    public void setDate_expired(String date_expired) {
        this.date_expired = date_expired;
    }

    public String getEt() {
        return et;
    }

    public void setEt(String et) {
        this.et = et;
    }
}
