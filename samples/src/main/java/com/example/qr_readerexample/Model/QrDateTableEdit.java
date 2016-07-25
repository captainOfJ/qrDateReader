package com.example.qr_readerexample.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qr_readerexample.Model.DataType.QrDate;

import java.util.ArrayList;

/**
 * Created by Daumantas on 2016-07-22.
 */
public class QrDateTableEdit {
    private final String TABLE_NAME = DatabaseQrDate.TABLE_NAME;
    //**************Profile Table stulpeliai************
    private final String COL_1_ID = DatabaseQrDate.COL_1_ID;
    private final String COL_2_DATE = DatabaseQrDate.COL_2_DATE;
    private final String COL_3_IMAGE = DatabaseQrDate.COL_3_IMAGE;

    SQLiteDatabase mDatabase;
    DatabaseQrDate mDatabaseQrDate;

    public QrDateTableEdit(Context context){
        mDatabaseQrDate = new DatabaseQrDate(context);
    }

    /**
     *
     * @param data Qr Data.
     * @return true - successful
     */
    public boolean insertQrDateData(QrDate data){
        mDatabase = mDatabaseQrDate.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2_DATE,data.getDate());
        contentValues.put(COL_3_IMAGE,data.getImage());
        long result = mDatabase.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    /**
     * get QR date information from database
     * @return ArrayList with all QR date information
     */
    public ArrayList<QrDate> getQrDate(){
        mDatabase = mDatabaseQrDate.getWritableDatabase();
        ArrayList<QrDate> qrDates = new ArrayList<QrDate>();

        Cursor res = mDatabase.rawQuery("select * from "+ TABLE_NAME,null);

        if(res.getCount()>=1){
            while (res.moveToNext()){
                QrDate qrDate = new QrDate();
                String id = res.getString(0);
                String date = res.getString(1);
                int image = res.getInt(2);

                qrDate.setID(id);
                qrDate.setDate(date);
                qrDate.setImage(image);
                qrDates.add(qrDate);
            }
        }

        return qrDates;
    }

    /**
     * format QR date table
     */
    public void formatTable(){
        mDatabase = mDatabaseQrDate.getWritableDatabase();
        mDatabase.execSQL("delete from "+TABLE_NAME);
        mDatabase.execSQL("UPDATE SQLITE_SEQUENCE SET SEQ=0 WHERE NAME="+"'"+TABLE_NAME+"'");
    }


}
