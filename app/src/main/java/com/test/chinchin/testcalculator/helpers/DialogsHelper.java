package com.test.chinchin.testcalculator.helpers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.test.chinchin.testcalculator.R;

public class DialogsHelper {

    public static AlertDialog ShowDialogSimpleOKButton(Context context, String title, String msg,
                                                       DialogInterface.OnClickListener positiveOnClick) {


        return new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.dialog_ok), positiveOnClick)
                .setCancelable(false)
                .create();


    }

    public static AlertDialog ShowDialogSimpleOKAndCancelButton(Context context, String title, String msg,
                                                                DialogInterface.OnClickListener positiveOnClick,
                                                                DialogInterface.OnClickListener negativeOnClick) {


        return new AlertDialog.Builder(context, R.style.DialogTheme)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.dialog_ok), positiveOnClick)
                .setNegativeButton(context.getString(R.string.dialog_cancel), negativeOnClick)
                .setCancelable(false)
                .create();


    }
}
