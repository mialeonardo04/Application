package com.example.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ignasiusleo.application.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentOne extends Fragment {

    public FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_one, container, false);
        ImageButton transaksi = v.findViewById(R.id.transaction);
        ImageButton barang = v.findViewById(R.id.inventory);
        /*ImageButton pegawai = v.findViewById(R.id.pegawai);*/
        ImageButton laporan = v.findViewById(R.id.report);

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentTwo secondFragment = new FragmentTwo();
                fragmentTransaction.replace(R.id.main_content, secondFragment);
                fragmentTransaction.commit();

            }
        });

        barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentThree thirdFragment = new FragmentThree();

                fragmentTransaction.replace(R.id.main_content, thirdFragment);
                fragmentTransaction.commit();
            }
        });


        laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentFive fragmentFive = new FragmentFive();

                fragmentTransaction.replace(R.id.main_content, fragmentFive);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}
