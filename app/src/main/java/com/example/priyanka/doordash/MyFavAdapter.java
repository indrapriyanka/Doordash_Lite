package com.example.priyanka.doordash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by priyanka on 5/31/2017.
 */

public class MyFavAdapter extends BaseAdapter {

    FavActivity favActivity;
    ArrayList<Discover> favlist;
    SingleItem singleitem;
    LayoutInflater fav_inflater;
    String favres;
    String favstatus;
    String favrating;
    Context context;
    ArrayList<Discover> contactList;
    ImageView ivslno;

 public MyFavAdapter(FavActivity favActivity,ArrayList<Discover> favlist) {
      this.favlist = favlist;
      this.favActivity=favActivity;
    }


    @Override
    public int getCount() {
        return favlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null) {
            final Discover discoveri = favlist.get(position);
        }

//        if(convertView == null) {
           LayoutInflater inflater = (LayoutInflater) favActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_favorites_adapter, null);
//        }

        final ImageView ivslno = (ImageView) convertView.findViewById(R.id.imv_fav_View);
        Log.d("vacuum","AFTER IVSLNO");
        //ivslno.setText(favlist.get(position).getMcover_img_url());
        //ivslno.setImageResource(favlist.get(position).getMcover_img_url());

        final TextView tvSlNo = (TextView) convertView.findViewById(R.id.tv_fav_RestaurantName);
        tvSlNo.setText(favlist.get(position).getMbusiness_name());

        final TextView tvName = (TextView) convertView.findViewById(R.id.tv_fav_Description);
        tvName.setText(favlist.get(position).getMdescription());

        final TextView tvPhone = (TextView) convertView.findViewById(R.id.tv_fav_Status);
        tvPhone.setText(favlist.get(position).getMstatus());

        Picasso.with(favActivity).load(favlist.get(position).getMcover_img_url()).into(ivslno);

        return convertView;

    }
}
