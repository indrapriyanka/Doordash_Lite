package com.example.priyanka.doordash;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by priyanka on 5/30/2017.
 */

public class SingleItem extends AppCompatActivity {


    SqlHandler sqlHandler;
    private ImageView iv_single_icon;
    private TextView tv_single_description,tv_single_status,tv_rating;
    Button btnFav;
    String icon_received;
    String restaurant_name_received;
    String status_received_received;
    String rating_received;
    String posi;
    String temp_img_urll;
    ArrayList<String > mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_list_item);

        iv_single_icon = (ImageView) findViewById(R.id.iv_single_icon);
        tv_single_description = (TextView) findViewById(R.id.tv_single_description);
        tv_single_status = (TextView) findViewById(R.id.tv_single_status);
        tv_rating = (TextView) findViewById(R.id.tv_rating);
        btnFav = (Button) findViewById(R.id.btnFav);

        final Intent intentthatStarted = getIntent();

        temp_img_urll = intentthatStarted.getStringExtra("temp_img_url");

        if(intentthatStarted.hasExtra("imagebitmap")) {
            Bundle extras = getIntent().getExtras();
            Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");
            iv_single_icon.setImageBitmap(bmp);
        }

        if(intentthatStarted.hasExtra(Intent.EXTRA_TEXT))
        {
            restaurant_name_received = intentthatStarted.getStringExtra(Intent.EXTRA_TEXT);
            tv_single_description.setText(restaurant_name_received);
        }
        if(intentthatStarted.hasExtra("text2"))
        {
             status_received_received = intentthatStarted.getStringExtra("text2");
            tv_single_status.setText(status_received_received);
        }
        if(intentthatStarted.hasExtra("text3"))
        {
             rating_received = intentthatStarted.getStringExtra("text3");
            tv_rating.setText(rating_received);
        }

        if(intentthatStarted.hasExtra("posi"))
        {
            String posi = intentthatStarted.getStringExtra("posi");
        }
//        actionBarSetup();

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SingleItem.this,FavActivity.class);

                intent.putExtra("fav_name",restaurant_name_received);
                intent.putExtra("fav_status",status_received_received);
                intent.putExtra("fav_rating",rating_received);
                intent.putExtra("temp_img_url",temp_img_urll);
                Log.d("vacuum","IMAGE URL : " + temp_img_urll);
               startActivity(intent);

            }
        });
    }
}
