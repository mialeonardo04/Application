package com.example.ignasiusleo.application.fragment;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReportPreview extends Fragment {
    protected String args;
    protected Cursor cursor;
    DataHelper dbHelper;
    TextView id, total, tgl;

    public FragmentReportPreview() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report_preview, container, false);
        id = v.findViewById(R.id.id_trans);
        total = v.findViewById(R.id.hrgTotal);
        tgl = v.findViewById(R.id.dateTime);

        dbHelper = new DataHelper(getActivity());
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            args = bundle.getString("idTransaksi");
        }
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM transaksi WHERE id_transaksi= '" +
                    args + "';";
            cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor.getCount() > 0) {
                cursor.moveToPosition(0);

                id.setText(cursor.getString(0));
                total.setText(cursor.getString(1));
                tgl.setText(cursor.getString(2));
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


        return v;
    }

}
