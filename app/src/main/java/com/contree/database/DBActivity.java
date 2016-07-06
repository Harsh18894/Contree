package com.contree.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Harsh on 7/6/2016.
 */
public class DBActivity {

    private static final String DATABASE_NAME = "contree";
    private static final String DATABASE_TABLE = "passwords";
    private static final int DATABASE_VERSION = 1;
    public static final String KEY_ROWID = "_id";
    public static final String KEY_PLATFORM = "platform";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    private DBHelper dbHelper;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE +
                    "(" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_PLATFORM + " VARCHAR(100), "
                    + KEY_USERNAME + " VARCHAR(100), "
                    + KEY_PASSWORD + " VARCHAR(100));");
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }

    public DBActivity(Context c) {
        myContext = c;
    }

    public void close() {
        dbHelper.close();
    }

    public DBActivity open() {
        dbHelper = new DBHelper(myContext);
        myDataBase = dbHelper.getWritableDatabase();
        return this;
    }

    public void insertDB(String platform, String username, String password) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_PLATFORM, platform);
        cv.put(KEY_USERNAME, username);
        cv.put(KEY_PASSWORD, password);
        myDataBase.insert(DATABASE_TABLE, null, cv);
    }

    public Cursor getData() {

        String selectQuery = "SELECT  rowid as " +
                KEY_ROWID + "," +
                KEY_PLATFORM + "," +
                KEY_USERNAME + "," +
                KEY_PASSWORD +
                " FROM " + DATABASE_TABLE;

        Cursor cursor = myDataBase.rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor getDataByPlatform(String search) {
        String selectQuery = "SELECT  rowid as " +
                KEY_ROWID + "," +
                KEY_PLATFORM + "," +
                KEY_USERNAME + "," +
                KEY_PASSWORD +
                " FROM " + DATABASE_TABLE +
                " WHERE " + KEY_PLATFORM + "  LIKE  '%" + search + "%' ";
        Cursor cursor = myDataBase.rawQuery(selectQuery, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    public ArrayList<String> getPasswords() {
        ArrayList<String> passwords = new ArrayList<>();
        String query = "select * from " + DATABASE_TABLE;
        Cursor c = myDataBase.rawQuery(query, null);
        int iName = c.getColumnIndex(KEY_PASSWORD);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            passwords.add(c.getString(iName));
        }
        return passwords;
    }
}

