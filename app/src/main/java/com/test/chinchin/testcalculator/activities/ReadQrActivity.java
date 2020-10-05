package com.test.chinchin.testcalculator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.gson.Gson;
import com.test.chinchin.testcalculator.R;
import com.test.chinchin.testcalculator.models.CurrencyModel;

import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.MY_PERMISSIONS_REQUEST_CAMERA;

public class ReadQrActivity extends AppCompatActivity {

    @BindView(R.id.camera_view)
    SurfaceView cameraView;

    @BindView(R.id.textView2)
    TextView title;

    @BindView(R.id.textView3)
    TextView CurrencyType1;

    @BindView(R.id.textV)
    TextView CurrencyValue1;

    @BindView(R.id.textView5)
    TextView CurrencyType2;

    @BindView(R.id.textView6)
    TextView CurrencyValue2;

    @BindView(R.id.recalculate)
    Button recalculate;

    private CameraSource cameraSource;
    private String token = "";
    private CurrencyModel currencyModel;

    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.obj!=null){
                title.setVisibility(View.VISIBLE);
                CurrencyType1.setVisibility(View.VISIBLE);
                CurrencyValue1.setVisibility(View.VISIBLE);
                CurrencyType2.setVisibility(View.VISIBLE);
                CurrencyValue2.setVisibility(View.VISIBLE);
                recalculate.setVisibility(View.VISIBLE);
                recalculate.setEnabled(true);

                title.setText(currencyModel.getInitValue());
                CurrencyType1.setText(currencyModel.getPTR());
                CurrencyValue1.setText(currencyModel.getCurrencyPTR());
                CurrencyType2.setText(currencyModel.getBS());
                CurrencyValue2.setText(currencyModel.getCurrencyBS());
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_qr);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.read_qr));

        ButterKnife.bind(this);

        recalculate.setEnabled(false);

        recalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initQR();

    }

    public void initQR() {

        BarcodeDetector barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.ALL_FORMATS)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1600, 1024)
                .setAutoFocusEnabled(true)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ReadQrActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA))
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        ie.getStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }


            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() > 0) {
                    token = barcodes.valueAt(0).displayValue.toString();

                    currencyModel = new Gson().fromJson(token, CurrencyModel.class);

                    Message message = Message.obtain();

                    message.obj = currencyModel;

                    handler.sendMessage(message);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}