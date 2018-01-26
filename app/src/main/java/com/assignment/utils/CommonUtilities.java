package com.assignment.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.assignment.R;
import com.assignment.database.DBHelper;

import java.io.ByteArrayOutputStream;


/**
 * Created by prabhu on 24/1/18.
 */

public class CommonUtilities {

    /**
     * Check if singleton object of DB is null and not open; in the case
     * reinitialize and open DB.
     *
     * @param mContext
     */
    public static DBHelper getDBObject(Context mContext) {
        DBHelper dbhelper = DBHelper.getInstance(mContext);
        return dbhelper;
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }


    public static byte[] getPhotoFromByteArray(Resources res, int photoId) {
        Bitmap bitmap = BitmapFactory.decodeResource(res, photoId);
        // ByteArrayOutputStream class is used to write common data into multiple files.
        ByteArrayOutputStream stream = new ByteArrayOutputStream(); // Creating object
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        // Will return a newly allocated Byte array
        return stream.toByteArray();
    }

}
