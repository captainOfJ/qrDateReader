package com.example.qr_readerexample.Control.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qr_readerexample.Control.PermissionRequest;
import com.example.qr_readerexample.Model.DataType.QrDate;
import com.example.qr_readerexample.Model.DatabaseQrDate;
import com.example.qr_readerexample.Model.QrDateTableEdit;
import com.example.qr_readerexample.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {

    private ArrayList<QrDate> mQrDates;
    DatabaseQrDate mDatabaseQrDate;
    QrDateTableEdit mQrDateTableEdit;
    QrDateListAdapter mQrDateListAdapter;


    @BindView(R.id.qrDateList) ListView mQrDateList;


    @OnClick(R.id.startQrReader_BT) void startQrReader() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DecoderActivity.class);
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "labas", Toast.LENGTH_SHORT).show();
    }

   @OnClick(R.id.format_BT) void formatList(){
   mQrDateTableEdit.formatTable();
       setDefaultDate();
       updateQrDateList();
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_main);
        ButterKnife.bind(this);
        PermissionRequest permissionRequest = new PermissionRequest();
        permissionRequest.askPermission(this);
        mDatabaseQrDate = new DatabaseQrDate(this);
        mQrDateTableEdit = new QrDateTableEdit(this);
        setDefaultDate();
        updateQrDateList();

    }

    public class QrDateListAdapter extends BaseAdapter {
        private ArrayList<QrDate> userArrayList;

        private LayoutInflater mInflater;

        public QrDateListAdapter() {
            Context context = MainActivity.this;
            userArrayList = mQrDates;
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            return userArrayList.size();
        }

        public Object getItem(int position) {
            return userArrayList.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_qr_date, null);
                holder = new ViewHolder();
                holder.dateQr = (TextView) convertView.findViewById(R.id.dateQr);
                holder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.dateQr.setText(userArrayList.get(position).getDate());
            holder.image.setImageResource(userArrayList.get(position).getImage());
            holder.image.getLayoutParams().height = 70;
            holder.image.getLayoutParams().width = 70;

            return convertView;
        }

        class ViewHolder {
            TextView dateQr;
            ImageView image;
        }
    }

    /**
     * set new data in QR date list
     */
    private void updateQrDateList(){
        mQrDates = mQrDateTableEdit.getQrDate();
        mQrDateListAdapter = new QrDateListAdapter();
        mQrDateList.setAdapter(mQrDateListAdapter);
        mQrDateListAdapter.notifyDataSetChanged();
    }

    /**
     * set current date like default date in Qr date list
     */
    private void setDefaultDate(){
    if(mQrDateTableEdit.getQrDate().size()<1){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        QrDate qrDate = new QrDate();
        qrDate.setDate(currentDate);
        qrDate.setImage(R.drawable.default_date);
        mQrDateTableEdit.insertQrDateData(qrDate);
    }
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabaseQrDate.closeDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateQrDateList();
    }
}
