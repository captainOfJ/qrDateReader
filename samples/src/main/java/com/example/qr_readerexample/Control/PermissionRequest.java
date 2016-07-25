package com.example.qr_readerexample.Control;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import com.example.qr_readerexample.Control.Activity.MainActivity;
import com.example.qr_readerexample.Model.DatabaseQrDate;

/**
 * Created by Design1 on 2016-04-25.
 */
public class PermissionRequest extends MainActivity {
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 3;
    private static final int PERMISSION_REQUEST_CAMERA = 4;


    public void askPermission(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) // jeigu irenginys dar neturi permision
                    != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, //paprasom permission
                        PERMISSION_REQUEST_READ_EXTERNAL_STORAGE);
            }
            if (activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) // jeigu irenginys dar neturi permision
                    != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, //paprasom permission
                        PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
            if (activity.checkSelfPermission(Manifest.permission.CAMERA) // jeigu irenginys dar neturi permision
                    != PackageManager.PERMISSION_GRANTED) {

                activity.requestPermissions(new String[]{Manifest.permission.CAMERA}, //paprasom permission
                        PERMISSION_REQUEST_CAMERA);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                   DatabaseQrDate databaseQrDate = new DatabaseQrDate(this);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   DatabaseQrDate databaseQrDate = new DatabaseQrDate(this);
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
