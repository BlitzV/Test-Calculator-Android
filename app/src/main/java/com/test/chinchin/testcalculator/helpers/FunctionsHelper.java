package com.test.chinchin.testcalculator.helpers;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import java.text.NumberFormat;
import java.util.regex.Pattern;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.BUNDLE_PASSWORD_COD_ERROR;
import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.BUNDLE_PASSWORD_ERROR;

public class FunctionsHelper {

    public static String DecimalFormat(Float value) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(0);
        formatter.setMaximumFractionDigits(2);
        return formatter.format(value);
    }

    public static boolean IsEmailInValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static Bundle IsPasswordInValid(String password) {

        Bundle bundle = new Bundle();

        bundle.putBoolean(BUNDLE_PASSWORD_ERROR, false);

        if (TextUtils.isEmpty(password)) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 0);
        }

        if (!password.matches(".*[!@#$%^&*,/\\\\()¿~_<>:;+.=?-].*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 1);
        }

        if (!password.matches(".*\\d.*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 2);
        }

        if (!password.matches(".*[a-z].*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 3);
        }

        if (!password.matches(".*[A-Z].*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 4);
        }

        if (!password.matches(".{8,15}")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 5);
        }

        if (password.matches(".*\\s.*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 6);
        }

        if (password.matches("^(?!.*(.)\\1)")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 7);
        }

        if (password.matches("^\\d.*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 8);
        }

        if (password.matches("^\\s.*")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 9);
        }

        if (password.matches(".*\\s$")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 10);
        }

        if (password.matches("^.*([a-z0-9A-Z!@#$%^&*,/\\\\()¿~_<>:;+.=?-])(\\1){2}")) {

            bundle.putInt(BUNDLE_PASSWORD_COD_ERROR, 11);
        }

        if (bundle.getInt(BUNDLE_PASSWORD_COD_ERROR, -1) > -1) {
            bundle.putBoolean(BUNDLE_PASSWORD_ERROR, true);
        }
        return bundle;
    }
}
