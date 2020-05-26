package com.SevenNine.Partnercode.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.SevenNine.Partnercode.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by vinod on 6/3/18.
 */

public class CameraFragment extends Fragment  {
    public static Camera camera;
    int camBackId = Camera.CameraInfo.CAMERA_FACING_BACK;
    ImageView imageView,selfie;
    CircleImageView gallery;
    ConstraintLayout constraintLayout;
    Camera.PictureCallback jpegCallback;
    int currentCameraId=camBackId;
    public static SurfaceHolder surfaceHolder;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    SurfaceView surfaceView;
    private static final int REQUEST_PERMISSIONS = 100;
    public Bitmap selectedImage;

    Fragment selectedFragment;

    public static CameraFragment newInstance() {
        CameraFragment itemOnFragment = new CameraFragment();
        return itemOnFragment;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvv" + requestCode);

        switch (requestCode) {

            case 100:


                surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

                    @Override
                    public void surfaceCreated(SurfaceHolder holder) {
                        System.out.println("gggggggggggggggggggggggggggggggggg");

                        try {
                            // open the camera
                            camera = Camera.open(camBackId);
                            camera.setDisplayOrientation(90);
                            Camera.Parameters p = camera.getParameters();
                            p.set("jpeg-quality", 100);
                            p.set("orientation", "landscape");
                            p.setRotation(90);
                            p.setPictureFormat(PixelFormat.JPEG);
                            // p.setPreviewSize(h, w);
                            camera.setParameters(p);

                        } catch (RuntimeException e) {
                            // check for exceptions
                            System.err.println(e);
                            return;
                        }
                        Camera.Parameters param;
                        param = camera.getParameters();

                        try {
                            // The Surface has been created, now tell the camera where to draw
                            // the preview.
                            // modify parameter
                            // param.setPreviewSize(mPreviewSize.width, mPreviewSize.height);
                            param.setPreviewSize(352, 288);
                            param.setPictureSize(1200, 900);
                            camera.setParameters(param);
                            camera.setPreviewDisplay(surfaceHolder);
                            camera.startPreview();
                        } catch (Exception e) {
                            // check for exceptions
                            System.err.println(e);
                            return;
                        }


                    }

                    @Override
                    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                        refreshCamera();

                    }

                    @Override
                    public void surfaceDestroyed(SurfaceHolder holder) {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    }
                });
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sell_camera, container, false);

        if ((ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                System.out.println("abc1111hhhhhhhhh"+"granted");

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        }else {
            Log.e("Else","Else");
            // fn_imagespath();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_CAMERA_REQUEST_CODE);
            }
        }


        return view;

    }


    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            System.out.println("abcccccamerA");

            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();

            System.out.println("abcccc");
        } catch (Exception e) {
            System.out.println("abcccc11122");

            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

}