package com.example.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.DataHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTransaksi extends Fragment {

    protected DataHelper dbHelper;
    protected String arg1;
    TextView tv;
    public FragmentTransaksi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_transaksi, container, false);
        Bundle bundle = this.getArguments();
        dbHelper = new DataHelper(getActivity());
        if (bundle != null) {
            arg1 = bundle.getString("scanResult");
        }
        tv = v.findViewById(R.id.test);
        tv.setText(arg1);
        return v;
    }

}
