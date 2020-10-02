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

import com.google.android.material.textfield.TextInputLayout;
import com.test.chinchin.testcalculator.adapters.SpinnerAdapter;
import com.test.chinchin.testcalculator.helpers.DialogsHelper;
import com.test.chinchin.testcalculator.helpers.FunctionsHelper;

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

        getRemoteData();

        spinnerAdapterData = new SpinnerAdapter(this, modelObject.getData());
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
    }

    public void SpinnerPoblate(List<Datum> apiModelReceiver){
        if(apiModelReceiver!=null){
            this.getRemoteData().getData().clear();
            this.getRemoteData().setData(apiModelReceiver);
        }
    }

    public void ErrorApiResponse(boolean value){
        if(value){
            DialogsHelper.ShowDialogSimpleOKButton(MainActivity.this, getString(R.string.error_remote),
                    getString(R.string.message_error_remote_data), new DialogInterface.OnClickListener() {
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
                        SpinnerPoblate(modelObject.getData());
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.getStackTrace();
                        modelObject = FunctionsHelper.MockDataApiModel();
                        SpinnerPoblate(modelObject.getData());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        return modelObject;
    }

}