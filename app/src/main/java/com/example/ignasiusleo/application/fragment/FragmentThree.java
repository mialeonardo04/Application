package com.example.ignasiusleo.application.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {
    public static FragmentThree fragmentThree;
    protected Cursor cursor;
    String[] daftar;
    ListView ListView01;
    Menu menu;
    DataHelper dbCenter;
    private TextView id;
    private Button addStock;

    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        ListView01 = v.findViewById(R.id.listView1);
        addStock = v.findViewById(R.id.addNewStock);
        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentAddBarang = new FragmentAddStock();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragmentAddBarang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        dbCenter = new DataHelper(getActivity());
        fragmentThree = this;
        RefreshList();
        return v;
    }

    public void RefreshList() {

        SQLiteDatabase db = dbCenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM stock", null);
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(1).toString();
        }

        ListView01.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftar));
        ListView01.setSelected(true);
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                final CharSequence[] dialogItem = {"Detail", "Edit", "Delete"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        Bundle bundle = new Bundle();
                        switch (item) {
                            case 0:
                                Fragment detail = new FragmentDetailStock();
                                bundle.putString("id_stock", selection);
                                detail.setArguments(bundle);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.main_content, detail);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                                break;

                            case 1:
                                Fragment edit = new FragmentEditStock();
                                bundle.putString("id_stock", selection);
                                edit.setArguments(bundle);

                                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                                fragmentTransaction2.replace(R.id.main_content, edit);
                                fragmentTransaction2.addToBackStack(null);
                                fragmentTransaction2.commit();
                                break;

                            case 2:
                                SQLiteDatabase database = dbCenter.getWritableDatabase();
                                database.execSQL("DELETE FROM stock where id_stock ='" + selection + "';");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
        //ListView01.getAdapter().notifyAll();
    }

}
