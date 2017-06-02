package com.example.priyanka.doordash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Serializable {

    private ListView lv_list;
    ProgressDialog pDialog;
    ArrayList<Discover> list_result;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    String  cover_img_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        lv_list=(ListView)findViewById(R.id.lv_list);
        mDrawerList = (ListView)findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        pDialog=new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Loading...");

                URL gitHubSearch = NetworkUtils.buildURL();
              //  Toast.makeText(getApplicationContext(),gitHubSearch.toString(),Toast.LENGTH_SHORT).show();
                new JSONResultTask().execute(gitHubSearch);
    }

    public class  JSONResultTask extends AsyncTask<URL, Void, ArrayList> {
        String jsonSearchResult="";
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            pDialog.show();
        }

        @Override
        protected ArrayList<Discover> doInBackground(URL... urls) {
            URL searchURL = urls[0];
            try{
                jsonSearchResult = NetworkUtils.getResponseFrpmHTTPUrl(searchURL);
            }
           catch (Exception e)
           {
               e.printStackTrace();
           }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
            setOutputData(jsonSearchResult);
            pDialog.dismiss();

        }
    }
    private void addDrawerItems() {
        final String[] osArray = { "Discover", "Favorites"};
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
                if(osArray[position]=="Discover")
                {
                    Intent intent_drawer = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent_drawer);
                }
                else if(osArray[position]=="Favorites")
                {
                    Intent intent_drawer = new Intent(MainActivity.this,FavActivity.class);
                    startActivity(intent_drawer);
                }
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("DoorDash");
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOutputData(String result)
{
    list_result = null;
    if(result != null) {
        try {
            JSONArray restaurant_array = new JSONArray(result);
            list_result = new ArrayList<Discover>();
            list_result.clear();
            for (int i = 0; i < restaurant_array.length(); i++) {
                JSONObject array_obj = restaurant_array.getJSONObject(i);
                JSONObject business = array_obj.getJSONObject("business");
               String business_name = business.getString("name");
               String  status = array_obj.getString("status");
                String description = array_obj.getString("description");
                 cover_img_url = array_obj.getString("cover_img_url");

                Discover discover = new Discover();
                discover.setMbusiness_name(business_name);
                discover.setMstatus(status);
                discover.setMdescription(description);
                discover.setMcover_img_url(cover_img_url);

                list_result.add(discover);
            }
            if(list_result.size()>0)
            {
                lv_list.setAdapter(new MyAdapter(this,list_result));
                lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        ImageView imv_single = (ImageView)view.findViewById(R.id.imv_View);
//                        Log.d("fsffsf",""+list_result.indexOf(id));

                        TextView tv_restaurantname_single = (TextView)view.findViewById(R.id.tv_RestaurantName);
                        String text_tv_restaurantname_single = tv_restaurantname_single.getText().toString();

                        TextView tv_desc_single = (TextView)view.findViewById(R.id.tv_Description);
                        String text_tv_desc_single = tv_desc_single.getText().toString();

                        TextView tv_Status_single = (TextView)view.findViewById(R.id.tv_Status);
                        String text_tv_Status_single = tv_Status_single.getText().toString();

                        TextView tv_secret_url_single = (TextView)view.findViewById(R.id.tv_secret_url);
                        String text_tv_secret_url_single = tv_secret_url_single.getText().toString();

                        Intent intent = new Intent(MainActivity.this,SingleItem.class);

                        imv_single.buildDrawingCache();
                        Bitmap image = imv_single.getDrawingCache();
                        Bundle extras = new Bundle();
                        extras.putParcelable("imagebitmap", image);
                        intent.putExtras(extras);

                        intent.putExtra(Intent.EXTRA_TEXT,text_tv_restaurantname_single);
                        intent.putExtra("text2",text_tv_desc_single);
                        intent.putExtra("text3",text_tv_Status_single);
                        intent.putExtra("posi",position);
                        intent.putExtra("temp_img_url",text_tv_secret_url_single);
                        Log.d("Venus",cover_img_url);
                        startActivity(intent);

                    }
                });
            }
        } catch (Exception e) {

        }
    }
    }
}
