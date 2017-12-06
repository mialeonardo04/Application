package com.example.ignasiusleo.application.model;

/**
 * Created by ignasiusleo on 06/12/17.
 */

public class SpinnerObject {
    private int databaseId;
    private String databaseValue;

    public SpinnerObject(int databaseId, String databaseValue) {
        this.databaseId = databaseId;
        this.databaseValue = databaseValue;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    @Override
    public String toString() {
        return databaseValue;
    }
}
