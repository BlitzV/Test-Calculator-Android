package com.test.chinchin.testcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.test.chinchin.testcalculator.adapters.SpinnerAdapter;
import com.test.chinchin.testcalculator.helpers.DialogsHelper;
import com.test.chinchin.testcalculator.helpers.FunctionsHelper;
import com.test.chinchin.testcalculator.preferences.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.OLD_DATA_OBTAINED;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.spinner)
    AppCompatSpinner spinner;

    @BindView(R.id.quantity)
    EditText quantity;

    @BindView(R.id.error_quantity)
    TextInputLayout errorQuantity;

    @BindView(R.id.calculate)
    Button Calculate;

    @Inject
    EndPoints RetrofitProvider;

    private Disposable subscription;
    private ApiModel modelObject = FunctionsHelper.MockDataApiModel();
    private SpinnerAdapter spinnerAdapterData;
    private int pos = 0;
    private boolean isBadResponse = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.getAppComponent().inject(this);

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

    public void SpinnerPoblate(){
        if(modelObject.getData()!=null && modelObject.getData().size()>4){
            spinnerAdapterData = new SpinnerAdapter(this, modelObject.getData());
            spinner.setAdapter(spinnerAdapterData);
            spinnerAdapterData.notifyDataSetChanged();
        } else {
            if(PreferencesHelper.GetStringValue(MainActivity.this,OLD_DATA_OBTAINED)!=null){
                modelObject = new Gson().fromJson(PreferencesHelper.GetStringValue(MainActivity.this,OLD_DATA_OBTAINED),ApiModel.class);
                spinnerAdapterData = new SpinnerAdapter(this, modelObject.getData());
                spinner.setAdapter(spinnerAdapterData);
                spinnerAdapterData.notifyDataSetChanged();
            } else {
                ErrorApiResponse(isBadResponse);
                isBadResponse = false;
            }
        }
    }

    public void ErrorApiResponse(boolean value){
        if(value){
            DialogsHelper.ShowDialogSimpleOKAndCancelButton(MainActivity.this, getString(R.string.error_remote),
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

    public ApiModel getRemoteData(){
        subscription = null;
        subscription = RetrofitProvider.CryptoInfo().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ApiModel>() {
                    @Override
                    public void onNext(ApiModel apiModels) {
                        modelObject.setData(apiModels.getData());
                        PreferencesHelper.SetStringValue(MainActivity.this, OLD_DATA_OBTAINED, new Gson().toJson(modelObject));
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

}