package com.example.ignasiusleo.application.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ignasiusleo.application.R;
import com.example.ignasiusleo.application.model.ItemSlideMenu;

import java.util.List;

/**
 * Created by ignasiusleo on 30/09/17.
 */

public class SlidingMenuAdapter extends BaseAdapter {
    private Context context;
    private List<ItemSlideMenu> listItem;

    public SlidingMenuAdapter(Context context, List<ItemSlideMenu> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public int getCount() {
        return listItem.size();
    }

    @Override
    public Object getItem(int i) {
        return listItem.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View v = View.inflate(context, R.layout.item_sliding_menu,null);
        ImageView imageView = v.findViewById(R.id.image1);
        TextView tv = v.findViewById(R.id.item_title);

        ItemSlideMenu itemSlideMenu = listItem.get(i);
        imageView.setImageResource(itemSlideMenu.getImgId());
        tv.setText(itemSlideMenu.getTitle());

        return v;
    }
}
