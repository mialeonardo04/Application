package com.example.ignasiusleo.application.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInventory extends Fragment {
    public static FragmentInventory fi;
    protected Cursor cursor, cursor2;
    String[] daftarNama, daftarId, daftarNama2, daftarId2;
    ListView ListView01;
    DataHelper dbCenter = new DataHelper(getActivity());
    String args = null;
    private TextView id;

    public FragmentInventory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inventory, container, false);
        ListView01 = v.findViewById(R.id.listView1);

        Button addItem = v.findViewById(R.id.addNewBarang);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragmentAddBarang = new FragmentAddItem();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_content, fragmentAddBarang);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        dbCenter = new DataHelper(getActivity());
        fi = this;
        RefreshList();
        return v;
    }

    public void RefreshList() {

        SQLiteDatabase db = dbCenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM barang", null);
        cursor2 = db.rawQuery("SELECT * FROM stock", null);

        daftarId = new String[cursor.getCount()];
        daftarNama = new String[cursor.getCount()];
        daftarId2 = new String[cursor2.getCount()];
        daftarNama2 = new String[cursor2.getCount()];

        cursor.moveToFirst();
        cursor2.moveToFirst();

        //getNamadanId barang
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftarId[i] = cursor.getString(0);
            daftarNama[i] = cursor.getString(1);
        }

        for (int j = 0; j < cursor2.getCount(); j++) {
            cursor2.moveToPosition(j);
            daftarId2[j] = cursor2.getString(0);
        }

        ListView01.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, daftarNama));
        ListView01.setSelected(true);
        ListView01.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView arg0, View arg1, final int arg2, long arg3) {
                final String selection = daftarId[arg2];
                //final String selection2 = daftarId2[arg2];
                final CharSequence[] dialogItem = {"Delete"};
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Options");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        Bundle bundle = new Bundle();
                        switch (item) {
                            case 0:
                                SQLiteDatabase database = dbCenter.getWritableDatabase();
                                String query = "DELETE FROM stock WHERE id_barang = '" + selection + "';";
                                String query2 = "DELETE FROM barang WHERE id_barang = '" + selection + "';";
                                database.execSQL(query);
                                database.execSQL(query2);

                                BlankFragment blank = new BlankFragment();
                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.frameContent, blank);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
                return true;
            }
        });
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                /*f(msgdesc.length() == 0 || msgdesc[i].length() == 0)*/
                try {
                    String selection = daftarId[arg2];
                    String selection2 = daftarId2[arg2];

                    args = selection2;

                    Bundle bundle = new Bundle();
                    bundle.putString("id_brg", selection);
                    bundle.putString("id_stok", selection2);

                    FragmentPreview f = new FragmentPreview();
                    f.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameContent, f);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                } catch (ArrayIndexOutOfBoundsException e) {
                    Toast.makeText(getActivity(), "Insert your barcode ID first to preview", Toast.LENGTH_LONG).show();
                }
            }
        });
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetInvalidated();
    }
}
