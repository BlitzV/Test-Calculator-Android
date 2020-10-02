package com.test.chinchin.testcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.test.chinchin.testcalculator.adapters.SpinnerAdapter;
import com.test.chinchin.testcalculator.helpers.DialogsHelper;

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

    @Inject
    EndPoints RetrofitProvider;

    private Disposable subscription;
    private List<Datum> apiModelsReceiver = new ArrayList<>();
    private SpinnerAdapter spinnerAdapterData;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        App.getAppComponent().inject(this);

        subscription = null;
        subscription = RetrofitProvider.CryptoInfo().subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Datum>>() {
                    @Override
                    public void onNext(List<Datum> apiModels) {
                        apiModelsReceiver = apiModels;
                        SpinnerPoblate(apiModelsReceiver);
                    }

                    //FIXME obtener lista de datos
                    @Override
                    public void onError(Throwable e) {
                        DialogsHelper.ShowDialogSimpleOKButton(MainActivity.this, getString(R.string.error_remote),
                                getString(R.string.message_error_remote_data), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        spinnerAdapterData = new SpinnerAdapter(this, apiModelsReceiver);
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

    public void SpinnerPoblate(List<Datum> apiModelReceiver){
        if(apiModelReceiver!=null){
            apiModelReceiver.get(pos).getAn();
        }
    }
}