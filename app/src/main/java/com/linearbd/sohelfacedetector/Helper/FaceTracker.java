package com.linearbd.sohelfacedetector.Helper;

import android.util.Log;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.linearbd.sohelfacedetector.Model.GraphicOverlay;
import com.linearbd.sohelfacedetector.Model.FaceGraphic;

/**
 * Created by Genius 03 on 8/19/2017.
 */

public class FaceTracker extends Tracker<Face> {
    private static final String TAG=FaceTracker.class.getSimpleName();

    private GraphicOverlay mOverlay;
    private FaceGraphic mFaceGraphic;

    public FaceTracker(GraphicOverlay overlay) {
        mOverlay = overlay;
        mFaceGraphic = new FaceGraphic(overlay);
    }

    @Override
    public void onNewItem(int faceId, Face face) {
        mFaceGraphic.setId(faceId);
    }

    @Override
    public void onUpdate(Detector.Detections<Face> detections, Face face) {
        mOverlay.add(mFaceGraphic);
        mFaceGraphic.updateFace(face);
    }

    @Override
    public void onMissing(FaceDetector.Detections<Face> detectionResults) {
        mOverlay.remove(mFaceGraphic);
    }


    @Override
    public void onDone() {
        mOverlay.remove(mFaceGraphic);
    }
}
