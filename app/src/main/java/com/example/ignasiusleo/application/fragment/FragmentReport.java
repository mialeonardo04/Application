package com.example.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;
import com.example.ignasiusleo.application.model.SpinnerObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentReport extends Fragment {
    protected int getIdTrans;
    protected String txtIdTrans;
    Spinner getBulan;
    public FragmentReport() {
        // Required empty public constructor
    }

    public void showSpinner() {
        DataHelper db = new DataHelper(getActivity());
        List<SpinnerObject> labels = db.getAllMonth();
        ArrayAdapter<SpinnerObject> dataAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        getBulan.setAdapter(dataAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        /*Spinner spinner = v.findViewById(R.id.month);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.month, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);*/
        getBulan = v.findViewById(R.id.month);
        showSpinner();
        getBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapterView, View view, int i, long l) {
                getIdTrans = ((SpinnerObject) getBulan.getSelectedItem()).getDatabaseId();
                String txtId = String.valueOf(getIdTrans);
                txtIdTrans = txtId;
                /*Bundle bundle = new Bundle();
                bundle.putString("idTransaksi",txtIdTrans);*/
                if (txtIdTrans.equals("0")) {

                } else {
                    Toast.makeText(getActivity(), "id transaksi ke - " + txtIdTrans, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //im doing nothing wkwk
            }
        });
        return v;
    }

}
