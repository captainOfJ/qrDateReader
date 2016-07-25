package com.example.qr_readerexample.Control.Activity;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.example.qr_readerexample.Control.Dialog.GetDataDialog;
import com.example.qr_readerexample.Control.Interface.DialogToActivity;
import com.example.qr_readerexample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DecoderActivity extends Activity implements OnQRCodeReadListener,DialogToActivity {

  private TextView myTextView;
  private QRCodeReaderView mydecoderview;
  private ImageView line_image;
  private GetDataDialog mGetDataDialog;
  public static int dialogCounter=0;

  @OnClick (R.id.exit_BT) void stopCamera(){
    mydecoderview.getCameraManager().stopPreview();
    DecoderActivity.this.finish();
  }


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_decoder);
     ButterKnife.bind(this);
    mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
    mydecoderview.setOnQRCodeReadListener(this);


    line_image = (ImageView) findViewById(R.id.red_line_image);

    TranslateAnimation mAnimation =
        new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0.5f);
    mAnimation.setDuration(1000);
    mAnimation.setRepeatCount(-1);
    mAnimation.setRepeatMode(Animation.REVERSE);
    mAnimation.setInterpolator(new LinearInterpolator());
    line_image.setAnimation(mAnimation);
  }

  // Called when a QR is decoded
  // "text" : the text encoded in QR
  // "points" : points where QR control points are placed
  @Override public void onQRCodeRead(String date, PointF[] points) {
    if (dialogCounter==0){
      showGetDataDialog(date);
      dialogCounter++;
    }

    mydecoderview.getCameraManager().stopPreview();
  }

  // Called when your device have no camera
  @Override public void cameraNotFound() {

  }

  // Called when there's no QR codes in the camera preview image
  @Override public void QRCodeNotFoundOnCamImage() {

  }

  @Override protected void onResume() {
    super.onResume();
    mydecoderview.getCameraManager().startPreview();
  }

  @Override protected void onPause() {
    super.onPause();
    mydecoderview.getCameraManager().stopPreview();
  }

  /**
   * show dialog
   * @param date - Received Date of qr code
     */
  public void showGetDataDialog(String date){
    mGetDataDialog = new GetDataDialog();
    Bundle bundle = new Bundle();
    bundle.putString("date", date);
    mGetDataDialog.setArguments(bundle);
    mGetDataDialog.setCancelable(false);
    if(!mGetDataDialog.isVisible()){
      mGetDataDialog.show(getFragmentManager(),"My Alert");
    }
  }


  @Override
  // if in dialog pressed try again button
  public void tryAgain() {
    mydecoderview.getCameraManager().startPreview();
  }
}
