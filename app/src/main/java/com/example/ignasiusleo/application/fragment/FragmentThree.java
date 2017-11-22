package com.example.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentThree extends Fragment {

    private TextView id;

    public FragmentThree() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_three, container, false);
        Bundle bundle = getArguments();
        if (bundle != null ){

            String txtId = bundle.getString("scanResult");

            id = v.findViewById(R.id.id3);
            id.setText(txtId);
        } else{
            /*Toast.makeText(getContext(),"No Message",Toast.LENGTH_SHORT).show()*/;
        }
        return v;
    }

}
