package io.githubleonard04.ignasiusleo.application.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(io.githubleonard04.ignasiusleo.application.R.layout.fragment_home, container, false);
        ImageButton transaksi = v.findViewById(io.githubleonard04.ignasiusleo.application.R.id.transaction);
        ImageButton barang = v.findViewById(io.githubleonard04.ignasiusleo.application.R.id.inventory);
        /*ImageButton pegawai = v.findViewById(R.id.pegawai);*/
        ImageButton laporan = v.findViewById(io.githubleonard04.ignasiusleo.application.R.id.report);

        transaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentTrans secondFragment = new FragmentTrans();
                fragmentTransaction.replace(io.githubleonard04.ignasiusleo.application.R.id.main_content, secondFragment);
                fragmentTransaction.commit();

            }
        });

        barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentInventory thirdFragment = new FragmentInventory();

                fragmentTransaction.replace(io.githubleonard04.ignasiusleo.application.R.id.main_content, thirdFragment);
                fragmentTransaction.commit();
            }
        });


        laporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentReport fragmentReport = new FragmentReport();

                fragmentTransaction.replace(io.githubleonard04.ignasiusleo.application.R.id.main_content, fragmentReport);
                fragmentTransaction.commit();
            }
        });

        return v;
    }
}
