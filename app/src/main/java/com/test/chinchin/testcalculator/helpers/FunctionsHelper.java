package com.test.chinchin.testcalculator.helpers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.test.chinchin.testcalculator.models.ApiModel;
import com.test.chinchin.testcalculator.models.Datum;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;
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

    public static ApiModel MockDataApiModel(){
        ApiModel apiModel = new ApiModel("",null,null,MockDatum(),false);
        return apiModel;
    }

    public static List<Datum> MockDatum() {
        Datum datum = new Datum("","","","","","",null,null,"USD",""
                ,null,null,null,null,null,null,1234567890L,null,"","",1234567890L,null,false);

        List<Datum> list = new ArrayList<>();

        list.add(datum);
        list.add(datum);
        return list;
    }

    public static Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 200, 200, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 200, 0, 0, w, h);
        return bitmap;
    }
}
