package com.example.qr_readerexample.Model;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daumantas on 2016-07-22.
 */
public class DatabaseQrDate extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QrDateDatabase";

    public static final String TABLE_NAME = "QrDate";

    // Table column
    public static final String COL_1_ID = "NR";
    public static final String COL_2_DATE = "QrDATE";
    public static final String COL_3_IMAGE = "IMAGE";

    //Data type
    public static final String COL_DATA_TYPE_TEXT_PRIMATY_KEY = " INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final String COL_DATA_TYPE_TEXT = " TEXT";
    public static final String COL_DATA_TYPE_INT = " INT";

    public DatabaseQrDate(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1_ID + COL_DATA_TYPE_TEXT_PRIMATY_KEY + "," +
                COL_2_DATE + COL_DATA_TYPE_TEXT + "," +
                COL_3_IMAGE + COL_DATA_TYPE_INT +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }


    public void closeDatabase() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        db.close();

    }

    public boolean openDatabase() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.isOpen();
    }

}