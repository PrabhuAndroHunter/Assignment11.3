package com.assignment.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.assignment.model.Employee;
import com.assignment.utils.CommonUtilities;
import com.assignment.utils.Constants;

import java.io.ByteArrayOutputStream;

/**
 * Created by prabhu on 24/1/18.
 */

public class DBHelper {
    private SQLiteDatabase db;
    private final Context context;
    private final TablesClass dbHelper;
    public static int no;
    public static DBHelper db_helper = null;

    public static DBHelper getInstance(Context context) {
        try {
            if (db_helper == null) {
                db_helper = new DBHelper(context);
                db_helper.open();
            }
        } catch (IllegalStateException e) {
            //db_helper already open
        }
        return db_helper;
    }

    /*
     * set context of the class and initialize TableClass Object
	 */

    public DBHelper(Context c) {
        context = c;
        dbHelper = new TablesClass(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    /*
     * close databse.
     */
    public void close() {
        if (db.isOpen()) {
            db.close();
        }
    }

    public boolean dbOpenCheck() {
        try {
            return db.isOpen();
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * open database
     */
    public void open() throws SQLiteException {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            Log.v("open database Exception", "error==" + e.getMessage());
            db = dbHelper.getReadableDatabase();
        }
    }

    public long insertContentVals(String tableName, ContentValues content) {
        long id = 0;
        try {
            db.beginTransaction();
            id = db.insert(tableName, null, content);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
        return id;
    }

    /*
     * Get count of all tables in a table as per the condition
	 */

    public int getFullCount(String table, String where) {
        Cursor cursor = db.query(false, table, null, where, null, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                no = cursor.getCount();
                cursor.close();
            }
        } finally {
            cursor.close();
        }
        return no;
    }

    public Employee getEmployeeDetails() throws SQLException { // Creating method

        //Cursor exposes results from a query on a SQLiteDatabase.
        Cursor cursor = db.query(true, Constants.EMPLOYEE_TABLE, new String[]{Constants.EMPLOYEE_PHOTO,
                Constants.EMPLOYEE_NAME, Constants.EMPLOYEE_AGE}, null, null, null, null, null, null);

        if (cursor.moveToLast()) {  //if statement executes from top to down and decides whether a certain statement will executes or not
            String employeeName = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_NAME));
            String employeeAge = cursor.getString(cursor.getColumnIndex(Constants.EMPLOYEE_AGE));
            byte[] blob = cursor.getBlob(cursor.getColumnIndex(Constants.EMPLOYEE_PHOTO));
            cursor.close();
            return new Employee(employeeName, employeeAge, CommonUtilities.getPhoto(blob));
        }
        cursor.close();
        return null; // Return statement
    }
}
