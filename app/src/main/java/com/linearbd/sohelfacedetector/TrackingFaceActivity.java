package com.linearbd.sohelfacedetector;

import android.Manifest;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.LargestFaceFocusingProcessor;
import com.linearbd.sohelfacedetector.Helper.FaceTracker;
import com.linearbd.sohelfacedetector.Model.CameraSourcePreview;
import com.linearbd.sohelfacedetector.Model.GraphicOverlay;

import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class TrackingFaceActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION=1000;
    private static final int RC_HANDLE_GMS=2000;

    // View
    private CameraSourcePreview cameraSourcePreview;
    private GraphicOverlay graphicOverlay;

    private CameraSource cameraSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_face);

        cameraSourcePreview = (CameraSourcePreview) findViewById(R.id.cameraSourcePreview);
        graphicOverlay = (GraphicOverlay) findViewById(R.id.graphicOverLay);
        cameraPermission();





    }

    @Override
    protected void onResume() {
        super.onResume();

        startCameraSource();
    }

    private void startCameraSource() {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                cameraSourcePreview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e("HHHHH", "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        cameraSourcePreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }


    @AfterPermissionGranted(CAMERA_PERMISSION)
    private void cameraPermission() {
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            createCameraSource();

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "App need to Permission for Location",
                    CAMERA_PERMISSION, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }


    private void createCameraSource(){

        final FaceDetector faceDetector = new FaceDetector.Builder(getApplicationContext())
                .setProminentFaceOnly(true)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Detector<Face> detector = new Detector<Face>() {
            @Override
            public SparseArray<Face> detect(Frame frame) {
                return faceDetector.detect(frame);
            }
        };

        faceDetector.setProcessor(new LargestFaceFocusingProcessor.Builder(detector,new FaceTracker(graphicOverlay))
                .build());

        if(!faceDetector.isOperational()){
            Log.d("TTTT","Detector not Yet Available");
        }

        cameraSource= new CameraSource.Builder(getApplicationContext(), faceDetector)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedPreviewSize(640, 480)
                .build();


    }
}
