package com.linearbd.sohelfacedetector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.linearbd.sohelfacedetector.Thread.FaceDetectorThread;

public class FaceDetectorActivity extends AppCompatActivity {
    public FaceView faceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detector);

        //doItinAThread();

        FaceDetectorThread faceDetectorThread = new FaceDetectorThread(this);
        faceDetectorThread.start();

        //doIt();


    }


    private void doIt(){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.face);

            final FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .build();

            Detector<Face> detector = new Detector<Face>() {
                @Override
                public SparseArray<Face> detect(Frame frame) {
                    return faceDetector.detect(frame);
                }
            };


            //detector.setProcessor(new LargestFaceFocusingProcessor());

            if(!detector.isOperational()){
                Log.d("HHHH","Not Operational");
                Log.d("HHHH","Not Operational");
                return;

            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();

            SparseArray<Face> faces = detector.detect(frame);

            Log.d("HHHH","Bitmap not Nulll");


            faceDetector.release();

    }

    private void doItinAThread(){

        Log.d("HHHH","Bitmap not Nulll");
        //faceView = (FaceView) findViewById(R.id.faceView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.face);

                final FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .build();

                Detector<Face> detector = new Detector<Face>() {
                    @Override
                    public SparseArray<Face> detect(Frame frame) {
                        return faceDetector.detect(frame);
                    }
                };


                //detector.setProcessor(new LargestFaceFocusingProcessor());

                if(!detector.isOperational()){
                    Log.d("HHHH","Not Operational");
                    Log.d("HHHH","Not Operational");
                    return;

                }

                Frame frame = new Frame.Builder().setBitmap(bitmap).build();

                final SparseArray<Face> faces = detector.detect(frame);


                FaceDetectorActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TTTT","HHHH");
                        faceView = (FaceView) findViewById(R.id.faceView);
                        faceView.setContent(faces,bitmap);
                    }
                });

               /* faceView.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("TTTT","HHHH");
                        //faceView.setContent(faces,bitmap);
                    }
                });*/



                faceDetector.release();
            }
        }).start();

    }

}
