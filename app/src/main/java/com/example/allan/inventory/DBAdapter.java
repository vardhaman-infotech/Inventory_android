package com.example.allan.inventory;
/**
 * Created by allan on 20/04/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_LOGTIME = "logtime";
    static final String KEY_USERNAME = "username";
    static final String KEY_REFNUM = "refNumber";
    static final String KEY_QUALITY= "quality";
    static final String KEY_LATITUDE = "latitude";
    static final String KEY_LONGITUDE = "longitude";

     static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "inventorylog";
    static final String DATABASE_TABLE = "log";
    static final int DATABASE_VERSION = 2;

    static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " (_id integer primary key autoincrement, "
                    +  KEY_LOGTIME + "  TEXT ," + KEY_USERNAME + " TEXT ," + KEY_REFNUM + " TEXT ,"
                    + KEY_QUALITY + " TEXT ," + KEY_LATITUDE + " TEXT ," + KEY_LONGITUDE + " TEXT  );";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS log");
            onCreate(db);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
       db = DBHelper.getWritableDatabase();
        return this;
    }
    public int getItemcount(){
        String countQuery = "SELECT * FROM " + DATABASE_TABLE;
        Cursor cursor = db.rawQuery(countQuery,null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;

    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a contact into the database---
    public long insertLog(String time, String username, String refnum, String quality,
                          String latitude, String longitude) {

        Log.e("/// latitude ", " " + latitude);
        Log.e("/// longitude ", " " + longitude);

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LOGTIME, time);
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_REFNUM,refnum);
        initialValues.put(KEY_QUALITY, quality);
        initialValues.put(KEY_LATITUDE, latitude);
        initialValues.put(KEY_LONGITUDE, longitude);

        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //----Delete all items in the table
    public int deletealllogs(){
        int rows = db.delete(DATABASE_TABLE,"1", null);

        return rows;
    }
    //---deletes a particular contact---
    public boolean deletelog(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    public Cursor getAlllogs()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_LOGTIME, KEY_USERNAME,
                KEY_REFNUM, KEY_QUALITY, KEY_LATITUDE, KEY_LONGITUDE}, null,
                null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getlog(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_LOGTIME,
                                KEY_USERNAME, KEY_REFNUM, KEY_QUALITY, KEY_LATITUDE, KEY_LONGITUDE},
                        KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updatelog(long rowId, String logtime, String username, String refnum, String quality )
    {
        ContentValues args = new ContentValues();
        args.put(KEY_LOGTIME, logtime);
        args.put(KEY_USERNAME, username);
        args.put(KEY_REFNUM, refnum);
        args.put(KEY_QUALITY, quality);


        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

}

