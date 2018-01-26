package com.assignment.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.assignment.utils.Constants;


/**
 * Created by prabhu on 24/1/18.
 */

public class TablesClass extends SQLiteOpenHelper {
    /**
     * Write all create table statements here in this class on oncreate method
     * If any changes in table structure go for onUpgrade method
     */

    Context context;

    public TablesClass(Context context, String DatabaseName, String nullColumnHack, int databaseVersion) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = ("CREATE TABLE " + Constants.EMPLOYEE_TABLE + " "
                + "(" + Constants.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Constants.EMPLOYEE_NAME + " text,"
                + Constants.EMPLOYEE_AGE + " text,"
                + Constants.EMPLOYEE_PHOTO + " BLOB NOT NULL );");
        db.execSQL(table1);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        context.deleteDatabase(Constants.DATABASE_NAME);
        onCreate(db);
    }
}