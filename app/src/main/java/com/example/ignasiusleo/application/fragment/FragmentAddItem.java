package com.example.ignasiusleo.application.fragment;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddItem extends Fragment {
    DataHelper dbHelper;
    Button save, cancel;
    EditText nama_barang, quantity, price, desc;

    public FragmentAddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_item, container, false);
        dbHelper = new DataHelper(getActivity());
        nama_barang = v.findViewById(R.id.nama_item);
        quantity = v.findViewById(R.id.qty);
        price = v.findViewById(R.id.harga);
        desc = v.findViewById(R.id.et);
        cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment3 = new FragmentInventory();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragment3);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        save = v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try {
                    String query = "INSERT into barang(nama_barang,jumlah,harga_barang,keterangan) values('" +
                            nama_barang.getText().toString() + "','" +
                            quantity.getText().toString() + "','" +
                            price.getText().toString() + "','" +
                            desc.getText().toString() + "');";
                    if (nama_barang.getText().equals("")
                            || quantity.getText().equals("") || price.getText().equals("")
                            || desc.getText().equals("")) {
                        Toast.makeText(getActivity(), "NULL DATA! NOT ALLOWED", Toast.LENGTH_LONG).show();
                    } else {
                        db.execSQL(query);
                        Toast.makeText(getActivity(), "Don't Quit! Please, insert Item barcode Here!", Toast.LENGTH_LONG).show();
                        clearText();
                        pindahFragment();
                        db.close();
                    }
                } catch (SQLiteException e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    db.close();
                }
            }
        });
        return v;
    }

    private void pindahFragment() {
        FragmentInventory.fi.RefreshList();
        Fragment back = new FragmentAddStock();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_content, back).addToBackStack(null).commit();
    }

    private void clearText() {
        nama_barang.setText("");
        quantity.setText("");
        price.setText("");
        desc.setText("");
    }
}
