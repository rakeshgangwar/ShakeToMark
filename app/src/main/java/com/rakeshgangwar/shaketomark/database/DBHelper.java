package com.rakeshgangwar.shaketomark.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Rakesh on 9/7/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Coordinates.db";
    public static final String LOCATION_TABLE_NAME = "locations";
    public static final String LOCATION_COLUMN_ID = "id";
    public static final String LOCATION_COLUMN_LATITUDE = "latitude";
    public static final String LOCATION_COLUMN_LONGITUDE = "longitude";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table locations " +
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,latitude text,longitude text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertCoordinates(String latitude, String longitude){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("latitude",latitude);
        contentValues.put("longitude",longitude);
        sqLiteDatabase.insert(LOCATION_TABLE_NAME,null,contentValues);
        return true;
    }

    public ArrayList<Locations> getAllCoordinates(){
        ArrayList<Locations> arrayList=new ArrayList<>();

        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from "+LOCATION_TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Locations locations=new Locations();
            locations.setLatitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LATITUDE)));
            locations.setLongitude(cursor.getString(cursor.getColumnIndex(LOCATION_COLUMN_LONGITUDE)));
            arrayList.add(locations);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }
}
