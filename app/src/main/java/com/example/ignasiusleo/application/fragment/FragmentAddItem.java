package com.example.ignasiusleo.application.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    protected Cursor cursor;
    DataHelper dbHelper;
    Button save, cancel;
    EditText id_barang, nama_barang, quantity, price, desc;
    FragmentThree f3;

    public FragmentAddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_item, container, false);
        dbHelper = new DataHelper(getActivity());
        id_barang = v.findViewById(R.id.id_item);
        nama_barang = v.findViewById(R.id.nama_item);
        quantity = v.findViewById(R.id.qty);
        price = v.findViewById(R.id.harga);
        desc = v.findViewById(R.id.et);
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
        save = v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("INSERT into barang(id_barang,nama_barang,jumlah,harga_barang,keterangan) values('" +
                        id_barang.getText().toString() + "','" +
                        nama_barang.getText().toString() + "','" +
                        quantity.getText().toString() + "','" +
                        price.getText().toString() + "','" +
                        desc.getText().toString() + "');"
                );
                Toast.makeText(getActivity(), "Data Inserted", Toast.LENGTH_LONG).show();
                FragmentThree.fragmentThree.RefreshList();
                clearText();
            }
        });
        return v;
    }

    private void clearText() {
        id_barang.setText("");
        nama_barang.setText("");
        quantity.setText("");
        price.setText("");
        desc.setText("");
    }

}
