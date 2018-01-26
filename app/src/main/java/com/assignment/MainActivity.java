package com.assignment;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.database.DBHelper;
import com.assignment.model.Employee;
import com.assignment.utils.CommonUtilities;
import com.assignment.utils.Constants;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.toString();
    private TextView mNameTv, mAgeTv, mLoadingTv;
    private ImageView mPhotoImv;

    DBHelper dbHelper;
    int count;
    private final String DEFAULT_EMPLOYEE_NAME = "Prabhu";
    private final int DEFAULT_EMPLOYEE_AGE = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // init layout
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        inItViews();
        // get DATABASE Helper instance
        dbHelper = CommonUtilities.getDBObject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Start new Thread For Database operations
        new Thread(new Runnable() {
            public void run() {
                count = dbHelper.getFullCount(Constants.EMPLOYEE_TABLE, null); // get Count of database records
                if (count == 0) {
                    insertEmployeeRecord();// If there is not records in database insert new records
                    Employee emp = dbHelper.getEmployeeDetails(); // Creating object
                    updateUi(emp);
                } else {   // if records found fetch data and update UI
                    final Employee emp = dbHelper.getEmployeeDetails(); // Creating object
                    updateUi(emp);
                }
            }
        }).start();
    }

    //Init all views
    private void inItViews() {
        mNameTv = (TextView) findViewById(R.id.name);
        mAgeTv = (TextView) findViewById(R.id.age);
        mLoadingTv = (TextView) findViewById(R.id.loadingText);
        mPhotoImv = (ImageView) findViewById(R.id.imageView);
    }

    // This will insert all records
    private void insertEmployeeRecord() {
        Log.d(TAG, "insertProductRecord: ");
        ContentValues contentValues = new ContentValues(); //Creating Object
        contentValues.put(Constants.EMPLOYEE_NAME, DEFAULT_EMPLOYEE_NAME);
        contentValues.put(Constants.EMPLOYEE_AGE, DEFAULT_EMPLOYEE_AGE);
        contentValues.put(Constants.EMPLOYEE_PHOTO, CommonUtilities.getPhotoFromByteArray(getResources(), R.drawable.prabhu));

        if (dbHelper != null)
            dbHelper.insertContentVals(Constants.EMPLOYEE_TABLE, contentValues);
        else
            Log.e(TAG, "insertProductRecord: dbhelper is null");
    }

    private void updateUi(final Employee emp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingTv.setVisibility(View.INVISIBLE);
                mNameTv.setText(emp.getName());
                mAgeTv.setText(emp.getAge());
                mPhotoImv.setImageBitmap(emp.getPhoto());
            }
        });
    }
}
