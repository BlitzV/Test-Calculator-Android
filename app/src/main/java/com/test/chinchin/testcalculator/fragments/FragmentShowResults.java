package com.test.chinchin.testcalculator.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.test.chinchin.testcalculator.R;
import com.test.chinchin.testcalculator.helpers.ConstantsHelper;
import com.test.chinchin.testcalculator.models.CurrencyModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.USD;
import static com.test.chinchin.testcalculator.helpers.FunctionsHelper.DecimalFormat;
import static com.test.chinchin.testcalculator.helpers.FunctionsHelper.encodeAsBitmap;

public class FragmentShowResults extends BottomSheetDialogFragment {

    @BindView(R.id.qr_image)
    ImageView QrImage;

    @BindView(R.id.txt_title)
    TextView title_fragment;

    @BindView(R.id.txt_currency)
    TextView txtCurrency;

    @BindView(R.id.txt_currency_value)
    TextView txtCurrencyValue;

    @BindView(R.id.txt_currency2)
    TextView txtCurrency2;

    @BindView(R.id.txt_currency_value2)
    TextView txtCurrencyValue2;

    @BindView(R.id.txt_cancel)
    TextView txtCancel;

    @BindView(R.id.txt_ok)
    TextView txtOk;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private int mParam2;

    private CurrencyModel currencyModel;
    private String PTR;
    private String BS;

    public FragmentShowResults() {
        // Required empty public constructor
    }

    public static FragmentShowResults newInstance(String param1, int spinnerPosition) {
        FragmentShowResults fragment = new FragmentShowResults();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2,spinnerPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BackgroundBottomFragmentTransparentTheme);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        ButterKnife.bind(this,view);

        CalculateValues();

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }

    private void CalculateValues() {
        if(mParam1!=null && mParam2 > -1){

            long valueToCalulate = Integer.parseInt(mParam1);

            title_fragment.setText(mParam1.concat(" ").concat(USD));

            String titletosave = mParam1.concat(" ").concat(USD);

            PTR = String.valueOf(valueToCalulate * 60);
            BS = String.valueOf(valueToCalulate * 100000);

            txtCurrency.setText(ConstantsHelper.PTR);
            PTR = DecimalFormat(Float.valueOf(PTR));

            txtCurrencyValue.setText(PTR);

            txtCurrency2.setText(ConstantsHelper.BS);
            BS = DecimalFormat(Float.valueOf(BS));

            txtCurrencyValue2.setText(BS);

            currencyModel = new CurrencyModel(titletosave,ConstantsHelper.PTR, ConstantsHelper.BS, PTR, BS);

            try {
                QrImage.setImageBitmap(encodeAsBitmap(new Gson().toJson(currencyModel)));

            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }


}