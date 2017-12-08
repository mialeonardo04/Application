package com.example.ignasiusleo.application.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.ignasiusleo.application.R;

import info.vividcode.android.zxing.CaptureActivity;
import info.vividcode.android.zxing.CaptureActivityIntents;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_two, container, false);

        Button btnScan = v.findViewById(R.id.bt_scan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(getContext(),CaptureActivity.class);
                CaptureActivityIntents.setPromptMessage(capture,"Scanning");
                startActivityForResult(capture,0);
            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0){
            if (resultCode == Activity.RESULT_OK && data != null){
                String val = data.getStringExtra("SCAN_RESULT");

                Bundle bundle = new Bundle();
                bundle.putString("scanResult",val);
/*
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentThree thirdFragment = new FragmentThree();
                thirdFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.main_content,thirdFragment);
                fragmentTransaction.commit();*/
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Scanning incompleted, please try again!", Toast.LENGTH_SHORT).show();
            }
        } else {}

        super.onActivityResult(requestCode, resultCode, data);
    }
}
