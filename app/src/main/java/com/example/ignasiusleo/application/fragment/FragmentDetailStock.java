package com.example.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ignasiusleo.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetailStock extends Fragment {


    public FragmentDetailStock() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_stock, container, false);
        Bundle bundle = new Bundle();
        if (bundle != null) {
            String myData = getArguments().getString("id_stock");
        }
        //get data per stock
        return v;
    }

}
