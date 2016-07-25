package com.example.qr_readerexample.Control.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_readerexample.Control.Activity.DecoderActivity;
import com.example.qr_readerexample.Control.Interface.DialogToActivity;
import com.example.qr_readerexample.Model.DataType.QrDate;
import com.example.qr_readerexample.Model.QrDateTableEdit;
import com.example.qr_readerexample.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Daumantas on 2016-07-22.
 */
public class GetDataDialog  extends DialogFragment{

    private Dialog mGetDataDialog;
    private String mDateFromQr;
    private QrDateTableEdit mQrDateTableEdit;
    DialogToActivity mDialogToActivity;

    @BindView(R.id.save_BT) Button mSave_BT;
    @BindView(R.id.qrDate_TV) TextView mQrDate_TV;

    @OnClick(R.id.cancel_BT) void cancelDialog(){
        mGetDataDialog.cancel();
        getActivity().finish();
    }
    @OnClick(R.id.save_BT) void okDate()  {
        try {
            saveDate(mDateFromQr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mGetDataDialog.cancel();
        getActivity().finish();

    }
    @OnClick(R.id.tryAgain_BT) void tryAgain(){
        mDialogToActivity.tryAgain();
        mGetDataDialog.cancel();
    }


    @Override
    public void onAttach(Activity activity) {
        mDialogToActivity = (DialogToActivity) activity;
        super.onAttach(activity);
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mGetDataDialog = new Dialog (getActivity(), R.style.MyDialog);
        DecoderActivity.dialogCounter=0;
        mGetDataDialog.setContentView(R.layout.dialog);
        ButterKnife.bind(this, mGetDataDialog);
        mQrDateTableEdit = new QrDateTableEdit(getActivity());
        mDateFromQr = getArguments().getString("date");

        try {
            checkDate(mDateFromQr);
        } catch (ParseException e) {
            e.printStackTrace();
            mSave_BT.setEnabled(false);
            Toast.makeText(getActivity(), R.string.errorBadDateFormat, Toast.LENGTH_SHORT).show();
            mQrDate_TV.setText(R.string.errorBadDateFormat);
        }
        return mGetDataDialog;
    }

    /**
     * save new Qr code date to database
     * @param date - new qr date
     * @throws ParseException
     */
    public void saveDate(String date) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<QrDate> qrDates = mQrDateTableEdit.getQrDate();
            String oldDateStr = qrDates.get(qrDates.size()-1).getDate();
            Date newDate = sdf.parse(date);
            //get last date from database
            Date oldDate = sdf.parse(oldDateStr);

            QrDate qrDate = new QrDate();
            if (newDate.after(oldDate)) {
                qrDate.setImage(R.drawable.after);
                qrDate.setDate(date);
                mQrDateTableEdit.insertQrDateData(qrDate);
            }
            else if(newDate.before(oldDate)) {
                qrDate.setImage(R.drawable.before);
                qrDate.setDate(date);
                mQrDateTableEdit.insertQrDateData(qrDate);
            }
    }


    /**
     * Check or QR date is right
     * @param date - new QR date
     * @return true - right, false - error
     * @throws ParseException
     */
    boolean checkDate(String date) throws ParseException {
        boolean result = false;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        ArrayList<QrDate> qrDates = mQrDateTableEdit.getQrDate();
        String oldDateStr = qrDates.get(qrDates.size()-1).getDate();
        Date newDate = sdf.parse(date);
        //get last date from database
        Date oldDate = sdf.parse(oldDateStr);
        if (newDate.after(oldDate)) {
            Toast.makeText(getActivity(), getString(R.string.after)+oldDateStr, Toast.LENGTH_LONG).show();
            mQrDate_TV.setText(date);
            result = true;
        }
        else if(newDate.before(oldDate)){
            Toast.makeText(getActivity(), getString(R.string.before)+oldDateStr, Toast.LENGTH_LONG).show();
            mQrDate_TV.setText(date);
            result = true;
        }
        else{
            //date is same
            mSave_BT.setEnabled(false);
            Toast.makeText(getActivity(), R.string.errorSame, Toast.LENGTH_LONG).show();
            mQrDate_TV.setText(R.string.errorSame);
        }
        return result;
    }


    @Override
    public void onDetach() {
        mDialogToActivity = null;
        super.onDetach();
    }
}

