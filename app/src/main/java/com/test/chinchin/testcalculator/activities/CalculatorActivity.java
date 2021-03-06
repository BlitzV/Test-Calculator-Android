package com.test.chinchin.testcalculator.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.test.chinchin.testcalculator.R;
import com.test.chinchin.testcalculator.adapters.SpinnerAdapter;
import com.test.chinchin.testcalculator.fragments.FragmentShowResults;
import com.test.chinchin.testcalculator.helpers.DialogsHelper;
import com.test.chinchin.testcalculator.helpers.FunctionsHelper;
import com.test.chinchin.testcalculator.login.LoginActivity;
import com.test.chinchin.testcalculator.models.ApiModel;
import com.test.chinchin.testcalculator.preferences.PreferencesHelper;
import com.test.chinchin.testcalculator.retrofit.EndPoints;
import com.test.chinchin.testcalculator.root.App;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.OLD_DATA_OBTAINED;

public class CalculatorActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    AppCompatSpinner spinner;

    @BindView(R.id.txtTotal)
    TextView txtTotal;

    @BindView(R.id.button0)
    TextView button0;

    @BindView(R.id.button1)
    TextView button1;

    @BindView(R.id.button2)
    TextView button2;

    @BindView(R.id.button3)
    TextView button3;

    @BindView(R.id.button4)
    TextView button4;

    @BindView(R.id.button5)
    TextView button5;

    @BindView(R.id.button6)
    TextView button6;

    @BindView(R.id.button7)
    TextView button7;

    @BindView(R.id.button8)
    TextView button8;

    @BindView(R.id.button9)
    TextView button9;

    @BindView(R.id.buttonClear)
    TextView buttonClear;

    @BindView(R.id.calculate)
    Button Calculate;

    @Inject
    EndPoints RetrofitProvider;

    private Disposable subscription;
    private ApiModel modelObject = FunctionsHelper.MockDataApiModel();
    private SpinnerAdapter spinnerAdapterData;
    private String quantity = null;
    private int pos = 0;
    private String finalAmount = null;
    private boolean isBadResponse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.calculator));

        App.getAppComponent().inject(this);

        View.OnClickListener numberListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calculate.setEnabled(false);

                TextView button = (TextView) v;

                if (quantity == null && !button.getText().toString().equals("0") && !button.getText().toString().equals(".")) {

                    quantity = button.getText().toString();

                } else if (quantity != null && quantity.length() < 11 && !quantity.matches(".*\\.[0-9]{2}?.*")) {

                    quantity = quantity + button.getText().toString();

                    if (quantity.indexOf(".") != (quantity.length() - 1)) {
                        quantity = FunctionsHelper.DecimalFormat(Float.valueOf(quantity.replace(",", "")));
                    }
                }
                CaptureQuantity();
            }
        };

        spinnerAdapterData = new SpinnerAdapter(this, getRemoteData().getData());
        spinner.setAdapter(spinnerAdapterData);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button0.setOnClickListener(numberListener);
        button1.setOnClickListener(numberListener);
        button2.setOnClickListener(numberListener);
        button3.setOnClickListener(numberListener);
        button4.setOnClickListener(numberListener);
        button5.setOnClickListener(numberListener);
        button6.setOnClickListener(numberListener);
        button7.setOnClickListener(numberListener);
        button8.setOnClickListener(numberListener);
        button9.setOnClickListener(numberListener);

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity != null) {

                    quantity = quantity.subSequence(0, quantity.length() - 1).toString();

                    if (quantity.length() > 0) {

                        if (quantity.indexOf(".") == (quantity.length() - 1)) {
                            quantity = quantity.subSequence(0, quantity.length() - 1).toString();
                        }

                        quantity = FunctionsHelper.DecimalFormat(Float.valueOf(quantity.replace(",", "")));

                    }

                }

                CaptureQuantity();

            }
        });

        Calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(finalAmount!=null){
                    FragmentShowResults bottomFragment = FragmentShowResults.newInstance(finalAmount.replace(",",""),pos);
                    bottomFragment.show(getSupportFragmentManager(),bottomFragment.getTag());
                }
            }
        });
    }

    //Management of amounts captured
    private void CaptureQuantity() {

        if (quantity != null && quantity.length() > 0) {

            txtTotal.setText(quantity);

            finalAmount = quantity;

            Calculate.setEnabled(true);

        } else {
            txtTotal.setText(null);
            Calculate.setEnabled(false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (subscription != null) {
            if (!subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SpinnerPoblate();
    }

    //Filling spinner with request to endpoint and data management in case of error in connection with preferences
    public void SpinnerPoblate(){
        if(modelObject.getData()!=null && modelObject.getData().size()>4){
            spinnerAdapterData = new SpinnerAdapter(this, modelObject.getData());
            spinner.setAdapter(spinnerAdapterData);
            spinnerAdapterData.notifyDataSetChanged();
        } else {
            if(PreferencesHelper.GetStringValue(CalculatorActivity.this,OLD_DATA_OBTAINED)!=null){
                modelObject = new Gson().fromJson(PreferencesHelper.GetStringValue(CalculatorActivity.this,OLD_DATA_OBTAINED),ApiModel.class);
                spinnerAdapterData = new SpinnerAdapter(this, modelObject.getData());
                spinner.setAdapter(spinnerAdapterData);
                spinnerAdapterData.notifyDataSetChanged();
            } else {
                ErrorApiResponse(isBadResponse);
                isBadResponse = false;
            }
        }
    }

    //Error handling in endpoint
    public void ErrorApiResponse(boolean value){
        if(value){
            DialogsHelper.ShowDialogSimpleOKAndCancelButton(CalculatorActivity.this, getString(R.string.error_remote),
                    getString(R.string.message_error_remote_data), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getRemoteData().setData(modelObject.getData());
                            dialog.dismiss();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    //Asynchronous request with javaRx of a get to endpoint to get the types of currency
    public ApiModel getRemoteData(){
        subscription = null;
        subscription = RetrofitProvider.CryptoInfo().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ApiModel>() {
                    @Override
                    public void onNext(ApiModel apiModels) {
                        modelObject.setData(apiModels.getData());
                        PreferencesHelper.SetStringValue(CalculatorActivity.this, OLD_DATA_OBTAINED, new Gson().toJson(modelObject));
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.getStackTrace();
                        modelObject = FunctionsHelper.MockDataApiModel();
                        isBadResponse = true;
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return modelObject;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    //Menu Management with request for permission to the user for the use of the camera
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.read_qr:
                if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(CalculatorActivity.this, ReadQrActivity.class));
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, 22);
                    }
                }
                return true;

                //Closing session with dialogue
            case R.id.close_sesion:
                DialogsHelper.ShowDialogSimpleOKAndCancelButton(this, getString(R.string.close_sesion),
                        getString(R.string.close_session_message), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                                new Intent(CalculatorActivity.this, LoginActivity.class);
                                PreferencesHelper.ClearPreferences(CalculatorActivity.this);
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 22:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(new Intent(CalculatorActivity.this,ReadQrActivity.class));
                } else {
                    DialogsHelper.ShowDialogSimpleOKButton(this, getString(R.string.error_permission),
                            getString(R.string.mensaje_permission), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
                return;
        }
    }
}