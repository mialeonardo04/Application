package com.example.ignasiusleo.application;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.ignasiusleo.application.adapter.SlidingMenuAdapter;
import com.example.ignasiusleo.application.dialog.CustomDialogClass;
import com.example.ignasiusleo.application.fragment.FragmentAbout;
import com.example.ignasiusleo.application.fragment.FragmentHelp;
import com.example.ignasiusleo.application.fragment.FragmentHome;
import com.example.ignasiusleo.application.fragment.FragmentInventory;
import com.example.ignasiusleo.application.fragment.FragmentReport;
import com.example.ignasiusleo.application.fragment.FragmentTrans;
import com.example.ignasiusleo.application.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignasiusleo on 30/09/17.
 */

public class MainActivity extends ActionBarActivity {
    public int n;

    private List<ItemSlideMenu> listSlide;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private RelativeLayout mainContent;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initiation content
        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mainContent = (RelativeLayout) findViewById(R.id.main_content);
        listSlide = new ArrayList<>();

        //tambah item slider menu
        listSlide.add(new ItemSlideMenu(R.mipmap.ic_launcher, "    Mo Kas"));
        listSlide.add(new ItemSlideMenu(R.drawable.button_transaction_selector,"    Transaksi"));
        listSlide.add(new ItemSlideMenu(R.drawable.button_inventory_selector,"    Data Barang"));
        listSlide.add(new ItemSlideMenu(R.drawable.button_report_selector,"    Laporan"));

        adapter = new SlidingMenuAdapter(this,listSlide);
        listViewSliding.setAdapter(adapter);

        //tampilkan icon dan slider
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set title
        setTitle(listSlide.get(0).getTitle());

        //untuk item yg di select
        listViewSliding.setItemChecked(0,true);

        //close menu
        drawerLayout.closeDrawer(listViewSliding);

        //tampil
        pindahFragment(0);

        //click listener
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setTitle(listSlide.get(i).getTitle());//set title
                listViewSliding.setItemChecked(i, true);//item dipilih

                pindahFragment(i);
                drawerLayout.closeDrawer(listViewSliding);
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_Closed){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()) {
            case R.id.menuItem1:
                pindahFragment(5);
                setTitle("User Guide");
                return true;
            case R.id.menuItem2:
                methodKeluar();
                return true;
            case R.id.menuItem3:
                pindahFragment(4);
                setTitle("About");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    //method untuk pindah fragment

    private void pindahFragment(int i){
        Fragment fragment = null;
        String TAG;

        switch (i){
            case  0:
                fragment = new FragmentHome();
                TAG = "HOME";
                n = 0;
                break;
            case  1:
                fragment = new FragmentTrans();
                TAG = null;
                n =1;
                break;
            case  2:
                fragment = new FragmentInventory();
                TAG = null;
                n=2;
                break;
            case 3:
                fragment = new FragmentReport();
                TAG = null;
                n =3;
                break;
            case 4:
                fragment = new FragmentAbout();
                TAG = null;
                n = 4;
                break;

            case 5:
                fragment = new FragmentHelp();
                TAG = null;
                break;
            default:
                fragment = new FragmentHome();
                TAG = "HOME";
                n=0;
                break;
        }

        if (fragment != null){
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            fragmentTransaction.replace(R.id.main_content,fragment,TAG);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onBackPressed() {
        switch (n){
            case 0:
                methodKeluar();
                break;
            case 1:
                pindahFragment(0);
                setTitle(listSlide.get(0).getTitle());
                break;
            case 2:
                pindahFragment(0);
                setTitle(listSlide.get(0).getTitle());
                break;
            case 3:
                pindahFragment(0);
                setTitle(listSlide.get(0).getTitle());
                break;
            case 4:
                pindahFragment(0);
                setTitle(listSlide.get(0).getTitle());
                break;
            case 5:
                pindahFragment(0);
                setTitle(listSlide.get(0).getTitle());
                break;
        }

    }

    private void methodKeluar(){
        CustomDialogClass cd = new CustomDialogClass(MainActivity.this);
        cd.show();
    }
}
