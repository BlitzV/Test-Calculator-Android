package com.test.chinchin.testcalculator.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.test.chinchin.testcalculator.models.ApiModel;
import com.test.chinchin.testcalculator.models.Datum;
import com.test.chinchin.testcalculator.R;
import com.test.chinchin.testcalculator.helpers.FunctionsHelper;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter {

    private ApiModel ApiObject;
    private List<Datum> apiModels;
    private Context context;
    private int resource;
    private LayoutInflater inflater;

    public SpinnerAdapter(@NonNull Context context, @NonNull List<Datum> apiModels) {
        super(context, R.layout.row_spinner, 0, apiModels);
        this.apiModels = apiModels;
        this.context = context;
        this.resource = R.layout.row_spinner;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(resource, parent, false);

        ApiObject = FunctionsHelper.MockDataApiModel();

        ApiObject.setData(apiModels);

        TextView txtOperator = view.findViewById(R.id.txtOperator);

        txtOperator.setText(ApiObject.getData().get(position).getAn());

        return view;
    }


}
