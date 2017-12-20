package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;
import com.example.ignasiusleo.application.model.SpinnerObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddStock extends Fragment {
    protected int getId;
    TextView txtIdStock, txtSpinner;
    DataHelper dbHelper;
    Button save, cancel;
    Spinner id_barang;
    EditText tgl_datang, tgl_expired;

    public FragmentAddStock() {
        // Required empty public constructor
    }


    public void showSpinner() {
        DataHelper db = new DataHelper(getActivity());
        List<SpinnerObject> labels = db.getAllLabels();
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        id_barang.setAdapter(dataAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_add_stock, container, false);
        Button btnScan = v.findViewById(R.id.scanStockID);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getContext(), CaptureActivity.class);
                CaptureActivityIntents.setPromptMessage(capture, "Scanning");
                startActivityForResult(capture, 0);
            }
        });
        dbHelper = new DataHelper(getActivity());
        txtIdStock = v.findViewById(R.id.id_stock);
        txtSpinner = v.findViewById(R.id.textSpinner);
        id_barang = v.findViewById(R.id.selectIDBarang);

        showSpinner();

        id_barang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                getId = ((SpinnerObject) id_barang.getSelectedItem()).getDatabaseId();
                String txt = String.valueOf(getId);
                if (txt.toString().equals("0")) {
                    txtSpinner.setHint("your item index shown here");
                } else {
                    txtSpinner.setText(txt);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // I'm not doin' anything bro wkwk
            }
        });
        final Calendar myCal = Calendar.getInstance();
        final Calendar myCal2 = Calendar.getInstance();
        tgl_datang = v.findViewById(R.id.tgl_masuk);
        tgl_expired = v.findViewById(R.id.tgl_kadaluarsa);

        final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCal.set(Calendar.YEAR, year);
                myCal.set(Calendar.MONTH, monthOfYear);
                myCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

            private void updateLabel() {
                String format = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                tgl_datang.setText(sdf.format(myCal.getTime()));
            }
        };
        tgl_datang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dateSetListener,
                        myCal.get(Calendar.YEAR), myCal.get(Calendar.MONTH),
                        myCal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final DatePickerDialog.OnDateSetListener dataSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCal2.set(Calendar.YEAR, year);
                myCal2.set(Calendar.MONTH, month);
                myCal2.set(Calendar.DAY_OF_MONTH, day);
                updateLabel2();
            }

            private void updateLabel2() {
                String format = "MM/dd/yy";
                SimpleDateFormat sdf1 = new SimpleDateFormat(format, Locale.US);
                tgl_expired.setText(sdf1.format(myCal2.getTime()));
            }
        };
        tgl_expired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), dataSetListener2,
                        myCal2.get(Calendar.YEAR), myCal2.get(Calendar.MONTH),
                        myCal2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        save = v.findViewById(R.id.save);
        final String a = String.valueOf(getId);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                try {
                    String sqlInsertStock = "insert into stock(id_stock, id_barang, tgl_datang, tgl_kadaluarsa) values('" +
                            txtIdStock.getText().toString() + "','" +
                            txtSpinner.getText().toString() + "','" +
                            tgl_datang.getText().toString() + "','" +
                            tgl_expired.getText().toString() + "')";
                    if (txtSpinner.getText().equals(null) || txtIdStock.getText().equals(null) ||
                            txtSpinner.getText().equals("") || txtIdStock.getText().equals("") ||
                            tgl_datang.getText().equals("") || tgl_expired.getText().equals("") ||
                            tgl_datang.getText().equals(null) || tgl_expired.getText().equals(null)) {
                        Toast.makeText(getActivity(), "NULL DATA! NOT ALLOWED", Toast.LENGTH_LONG).show();
                    } else {
                        db.execSQL(sqlInsertStock);
                        clearText();
                        Toast.makeText(getActivity(), "Data Inserted Successful", Toast.LENGTH_LONG).show();
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

        /*cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pindahFragment();
            }
        });*/
        return v;
    }


    private void pindahFragment() {
        Fragment fragment3 = new FragmentInventory();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_content, fragment3);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String val = data.getStringExtra("SCAN_RESULT");
                txtIdStock.setText(val);

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void clearText() {
        txtIdStock.setText("");
        txtSpinner.setText("");
        tgl_datang.setText("");
        tgl_expired.setText("");

    }
}
