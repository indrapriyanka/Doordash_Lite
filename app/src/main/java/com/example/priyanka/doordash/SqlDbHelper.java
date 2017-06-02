package com.example.priyanka.doordash;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by priyanka on 5/31/2017.
 */

public class SqlDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_TABLE = "RESTAURANT_FAVORITES";

    public static final String COLUMN1 = "Restaurant_Name";
    public static final String COLUMN2 = "Description";
    public static final String COLUMN3 = "Rating";

    private static final String SCRIPT_CREATE_DATABASE = "create table "
            + DATABASE_TABLE + " (" + COLUMN1
            + " integer primary key autoincrement, " + COLUMN2
            + " text not null, " + COLUMN3 + " text not null);";

    public SqlDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
  //      db.openOrCreateDatabase("MyDataBase",Context.MODE_PRIVATE,null);
        db.execSQL(SCRIPT_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }


}
