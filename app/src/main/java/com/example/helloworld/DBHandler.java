package com.example.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.*;

public class DBHandler extends SQLiteOpenHelper {
    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "HelloWorld";

    // table name
    private static final String TABLE_AKUN = "akun";

    // column tables
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private int count = 0;

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_AKUN + "("
                + KEY_EMAIL + " TEXT PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_AKUN);
        onCreate(sqLiteDatabase);
    }

    public void addRecord(DBModel dbModel){
        SQLiteDatabase db  = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, dbModel.getEmail());
        values.put(KEY_PASSWORD, dbModel.getPass());

        db.insert(TABLE_AKUN, null, values);
        db.close();
    }

    public List<DBModel> getAllRecord() {
        List<DBModel> accountList = new ArrayList<DBModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_AKUN;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                DBModel userModels = new DBModel();
                userModels.setEmail(cursor.getString(0));
                userModels.setPass(cursor.getString(1));

                accountList.add(userModels);
            } while (cursor.moveToNext());
        }

        // return contact list
        return accountList;
    }

    public int getDBModelCount() {
        String countQuery = "SELECT  * FROM " + TABLE_AKUN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        count = cursor.getCount();
        cursor.close();

        return count;
    }
}
