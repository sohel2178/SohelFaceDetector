package com.linearbd.sohelfacedetector.Thread;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.linearbd.sohelfacedetector.FaceDetectorActivity;
import com.linearbd.sohelfacedetector.FaceView;
import com.linearbd.sohelfacedetector.R;

/**
 * Created by Genius 03 on 8/20/2017.
 */

public class FaceDetectorThread extends Thread {
    private Handler mHandler;
    private Activity activity;

    private ProgressDialog dialog;


    public FaceDetectorThread(Activity activity){
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
        dialog.setMessage("Please Wait...");
        dialog.setTitle("Processing Image");
        dialog.setCancelable(false);
    }



    @Override
    public void run() {
        super.run();

        // Show ing the Dialog in the Main Thread
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

        final Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.face);

        final FaceDetector faceDetector = new FaceDetector.Builder(activity)
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
            return;

        }

        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        final SparseArray<Face> faces = detector.detect(frame);


        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Hiding the Dialog in the UI Thread
                dialog.dismiss();
                FaceDetectorActivity faceDetectorActivity = (FaceDetectorActivity) activity;
                faceDetectorActivity.faceView = (FaceView) activity.findViewById(R.id.faceView);
                faceDetectorActivity.faceView.setContent(faces,bitmap);
            }
        });

        faceDetector.release();

    }
}
