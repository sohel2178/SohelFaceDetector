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

public class FaceDetectorActivity extends AppCompatActivity {
    private FaceView faceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detector);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.face);

        final FaceDetector faceDetector = new FaceDetector.Builder(this)
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

        faceView = (FaceView) findViewById(R.id.faceView);
        faceView.setContent(faces,bitmap);


        /*for (int i = 0; i < faces.size(); ++i) {
            Face face = faces.valueAt(i);
            for (Landmark landmark : face.getLandmarks()) {
                Log.d("HHHH",landmark.getPosition().x+"");
            }
        }*/

        faceDetector.release();
    }
}
