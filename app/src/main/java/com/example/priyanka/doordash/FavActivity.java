package com.example.priyanka.doordash;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by priyanka on 5/31/2017.
 */

public class FavActivity extends ActionBarActivity {
    ArrayList<Discover> fav_list_result;
    ListView fav_list;
    SqlHandler sqlHandler;
    TextView fav_activity_RestaurantName;
    TextView fav_activity_Description;
    TextView fav_activity_Status;
    ArrayList<String>stock_list;
    SQLiteDatabase db;
    String restaurant_name_received1;
    String status_received_received2;
    String rating_received3;
    String temp_img_url_received;
    ArrayList<Discover>contactList;
    Cursor c;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favorites);

        fav_list_result = null;
        fav_list_result = new ArrayList<Discover>();
        fav_list_result.clear();
        fav_list = (ListView) findViewById(R.id.lv_favlist);
        ImageView fav_activity_img;

        mDrawerList = (ListView)findViewById(R.id.navList1);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout1);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

       final Intent fav_intentthatStarted = getIntent();

        db=openOrCreateDatabase("MY DATABASE", Context.MODE_PRIVATE,null);
        db.execSQL("create table if not exists priyanka(imgurl varchar(70), restaurantname varchar(50) , status varchar(50),rating varchar(20))");

        ContentValues val=new ContentValues();
        if(fav_intentthatStarted.hasExtra("temp_img_url"))
        {
            temp_img_url_received = fav_intentthatStarted.getStringExtra("temp_img_url");
            val.put("imgurl",temp_img_url_received);
        }
        if(fav_intentthatStarted.hasExtra("fav_name"))
        {
            restaurant_name_received1 = fav_intentthatStarted.getStringExtra("fav_name");
            val.put("restaurantname", restaurant_name_received1.toString());
       }
        if(fav_intentthatStarted.hasExtra("fav_status"))
        {
            status_received_received2 = fav_intentthatStarted.getStringExtra("fav_status");
            val.put("status", status_received_received2.toString());
        }
        if(fav_intentthatStarted.hasExtra("fav_rating"))
        {
            rating_received3 = fav_intentthatStarted.getStringExtra("fav_rating");
            val.put("rating", rating_received3.toString());
        }
        contactList = new ArrayList<Discover>();
        if(fav_intentthatStarted.hasExtra("temp_img_url") && fav_intentthatStarted.hasExtra("fav_name") && fav_intentthatStarted.hasExtra("fav_status") &&  fav_intentthatStarted.hasExtra("fav_rating"))
        {
            long i=db.insert("priyanka", null, val);
            if(i>0)
            {
                 c=db.rawQuery("select *  from priyanka", null);
            }
        }
        else
        {
            Log.d("vacuum","BIG ELSE CONDITION");
             c=db.rawQuery("select *  from priyanka", null);
        }
            while(c.moveToNext())
            {
           Discover discov = new Discover();
                discov.setMcover_img_url(c.getString(c.getColumnIndex("imgurl")));
                discov.setMbusiness_name(c.getString(c.getColumnIndex("restaurantname")));
                discov.setMdescription(c.getString(c.getColumnIndex("status")));
                discov.setMstatus(c.getString(c.getColumnIndex("rating")));
                contactList.add(discov);
            }
            if(contactList.size()>0)
        {
            Log.d("vacuum","CONTACT LIST SIZE GREATER THAN 0");
           fav_list.setAdapter(new MyFavAdapter(this,contactList));
        }
    }

    private void addDrawerItems() {
        final String[] osArray = { "Discover", "Favorites"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(osArray[position]=="Discover")
                {
                    Intent intent_drawer = new Intent(FavActivity.this,MainActivity.class);
                    startActivity(intent_drawer);
                }
                else if(osArray[position]=="Favorites")
                {
                    Intent intent_drawer = new Intent(FavActivity.this,FavActivity.class);
                    startActivity(intent_drawer);
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}