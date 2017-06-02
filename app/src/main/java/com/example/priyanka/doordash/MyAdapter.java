package com.example.priyanka.doordash;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by priyanka on 5/30/2017.
 */

public class MyAdapter extends BaseAdapter {
    LayoutInflater inflater;
    MainActivity mainActivity;
    ArrayList<Discover> list;
    ImageView imv_icon;
    TextView restaurant_name;
    TextView description;
    TextView status;
    final Discover discoveri=null;

    public MyAdapter(MainActivity mainActivity, ArrayList<Discover> list)
    {
     this.mainActivity = mainActivity;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView != null) {
            final Discover discoveri = list.get(position);
        }
        Log.d("POSITIONS"," " + position);
        inflater = LayoutInflater.from(mainActivity);
        convertView = inflater.inflate(R.layout.adapter,null);

        final ImageView imv_icon = (ImageView)convertView.findViewById(R.id.imv_View);
        final TextView restaurant_name = (TextView)convertView.findViewById(R.id.tv_RestaurantName);
        final TextView description = (TextView)convertView.findViewById(R.id.tv_Description);
        final TextView status = (TextView)convertView.findViewById(R.id.tv_Status);
        final TextView tv_secret_url = (TextView)convertView.findViewById(R.id.tv_secret_url);

        restaurant_name.setText(list.get(position).getMbusiness_name());
        description.setText(list.get(position).getMdescription());
        status.setText(list.get(position).getMstatus());
        //status.setText(list.get(position).getMcover_img_url());
        tv_secret_url.setText(list.get(position).getMcover_img_url());

        Picasso.with(mainActivity).load(list.get(position).getMcover_img_url()).into(imv_icon);
        Log.d("MyAdapter","URL IS : " + list.get(position).getMcover_img_url());

        return convertView;
    }
}
