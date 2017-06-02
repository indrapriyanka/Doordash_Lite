package com.example.priyanka.doordash;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by priyanka on 5/29/2017.
 */

public class NetworkUtils {
    final static String BASE_URL = "https://api.doordash.com/v2/restaurant/?lat=37.422740&lng=-122.139956";
    //final static String BASE_URL = "https://api.doordash.com/v2/restaurant/30/";

    public static URL buildURL() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFrpmHTTPUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if(hasInput)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
