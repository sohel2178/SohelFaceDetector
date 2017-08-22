package com.linearbd.sohelfacedetector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;

import static com.linearbd.sohelfacedetector.R.id.faceDetectorActivity;
import static com.linearbd.sohelfacedetector.R.id.qrCodeActivity;
import static com.linearbd.sohelfacedetector.R.id.trackingFaceActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnFaceDetectorActivity,btnTrackinFaceActivity,btnQrCodeActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFaceDetectorActivity = (Button) findViewById(faceDetectorActivity);
        btnTrackinFaceActivity = (Button) findViewById(trackingFaceActivity);
        btnQrCodeActivity = (Button) findViewById(qrCodeActivity);

        btnFaceDetectorActivity.setOnClickListener(this);
        btnTrackinFaceActivity.setOnClickListener(this);
        btnQrCodeActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case faceDetectorActivity:
                gotoFaceDetectorActivity();
                break;

            case R.id.trackingFaceActivity:
                gotoTrackingFaceActivity();
                break;

            case R.id.qrCodeActivity:
                gotoQRCodeActivity();
                break;
        }

    }

    private void gotoQRCodeActivity() {
        startActivity(new Intent(getApplicationContext(),QRCodeActivity.class));
    }

    private void gotoTrackingFaceActivity() {
        startActivity(new Intent(getApplicationContext(),TrackingFaceActivity.class));
    }

    private void gotoFaceDetectorActivity() {
        startActivity(new Intent(getApplicationContext(),FaceDetectorActivity.class));
    }
}
