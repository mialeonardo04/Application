package com.example.ignasiusleo.application.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransaksi extends Fragment {
    public static FragmentTransaksi fragmentTransaksi;
    protected Cursor cursor;
    protected String arg1;
    DataHelper dbCenter = new DataHelper(getActivity());
    TextView tv;
    ListView list;
    String[] daftar1, daftar2, daftar3, daftar4, daftar5, daftar6, daftar7;
    public FragmentTransaksi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaksi, container, false);
        Bundle bundle = this.getArguments();
        dbCenter = new DataHelper(getActivity());
        if (bundle != null) {
            arg1 = bundle.getString("scanResult");
        }
        list = v.findViewById(R.id.listTransaksi);
        tv = v.findViewById(R.id.test);
        //tv.setText(arg1);
        fragmentTransaksi = this;
        RefreshList();

        return v;
    }

    private void RefreshList() {
        SQLiteDatabase db = dbCenter.getReadableDatabase();
        String sql = "SELECT * FROM barang,stock WHERE stock.id_stock = '" +
                arg1 + "' AND barang.id_barang = stock.id_barang;";
        cursor = db.rawQuery(sql, null);
        daftar1 = new String[cursor.getCount()];

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar1[i] = cursor.getString(1);

        }
        //String[] a = daftar1;
        list.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar1));
        ((ArrayAdapter) list.getAdapter()).notifyDataSetInvalidated();
    }

}
