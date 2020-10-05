package com.test.chinchin.testcalculator.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.test.chinchin.testcalculator.BuildConfig;
import com.test.chinchin.testcalculator.activities.CalculatorActivity;
import com.test.chinchin.testcalculator.R;
import com.test.chinchin.testcalculator.helpers.DialogsHelper;
import com.test.chinchin.testcalculator.models.LoginModel;
import com.test.chinchin.testcalculator.preferences.PreferencesHelper;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.BUNDLE_PASSWORD_COD_ERROR;
import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.BUNDLE_PASSWORD_ERROR;
import static com.test.chinchin.testcalculator.helpers.ConstantsHelper.SAVE_USER;
import static com.test.chinchin.testcalculator.helpers.FunctionsHelper.IsEmailInValid;
import static com.test.chinchin.testcalculator.helpers.FunctionsHelper.IsPasswordInValid;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.txtEditEmail)
    TextInputEditText email;

    @BindView(R.id.txtEditPassword)
    TextInputEditText password;

    @BindView(R.id.txtInputEmail)
    TextInputLayout errorEmailField;

    @BindView(R.id.txtInputPassword)
    TextInputLayout errorPasswordField;

    @BindView(R.id.signIn)
    Button signIn;

    private Gson gson = new Gson();
    private LoginModel loginModel;
    private String ValidEmail;
    private String ValidPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle(getString(R.string.login));

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate user input
                if (!IsEmailInValid(Objects.requireNonNull(email.getText()).toString())) {
                    InputError(0);
                    return;
                } else {
                    ValidEmail = email.getText().toString();
                }

                if (TextUtils.isEmpty(password.getText())) {
                    InputError(1);
                    return;
                } else {

                    Bundle bundle = IsPasswordInValid(password.getText().toString());

                    if (bundle.getBoolean(BUNDLE_PASSWORD_ERROR)) {
                        InputError(bundle.getInt(BUNDLE_PASSWORD_COD_ERROR) + 1);

                        return;
                    } else {
                        ValidPassword = password.getText().toString();
                    }
                }

                loginModel = new LoginModel(ValidEmail, ValidPassword, BuildConfig.SECURITY);

                SuccessfullLogin(loginModel);
            }
        });
    }

    //Errors management
    public void InputError(int errorCase) {

        errorEmailField.setError(null);
        errorPasswordField.setError(null);

        switch (errorCase) {
            case 0:
                errorEmailField.setError(getString(R.string.error_invalid_email));
                errorEmailField.requestFocus();
                break;
            case 1:
                errorPasswordField.setError(getString(R.string.error_field_required));
                errorPasswordField.requestFocus();
                break;
            case 2:
                errorPasswordField.setError(getString(R.string.error_not_find_special_caracter));
                errorPasswordField.requestFocus();
                break;
            case 3:
                errorPasswordField.setError(getString(R.string.error_not_find_number));
                errorPasswordField.requestFocus();
                break;
            case 4:
                errorPasswordField.setError(getString(R.string.error_not_find_lowercase_caracter));
                errorPasswordField.requestFocus();
                break;
            case 5:
                errorPasswordField.setError(getString(R.string.error_not_find_uppercase_caracter));
                errorPasswordField.requestFocus();
                break;
            case 6:
                errorPasswordField.setError(getString(R.string.error_too_short_password));
                errorPasswordField.requestFocus();
                break;
            case 7:
                errorPasswordField.setError(getString(R.string.error_spaces));
                errorPasswordField.requestFocus();
                break;
            case 8:
            case 12:
                errorPasswordField.setError(getString(R.string.error_triplicate_caracter));
                errorPasswordField.requestFocus();
                break;
            case 9:
                errorPasswordField.setError(getString(R.string.error_init_number));
                errorPasswordField.requestFocus();
                break;
            case 10:
                errorPasswordField.setError(getString(R.string.error_init_empty));
                errorPasswordField.requestFocus();
                break;
            case 11:
                errorPasswordField.setError(getString(R.string.error_final_empty));
                errorPasswordField.requestFocus();
                break;
        }
    }

    //function to continue with the flow in the application
    //request permission of use internet
    public void SuccessfullLogin(LoginModel loginModel){
        if (loginModel != null) {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED) {
                PreferencesHelper.SetStringValue(LoginActivity.this,SAVE_USER, gson.toJson(loginModel));
                finish();
                startActivity(new Intent(LoginActivity.this, CalculatorActivity.class));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.INTERNET}, 23);
                }
            }

        } else {
            DialogsHelper.ShowDialogSimpleOKButton(LoginActivity.this, getString(R.string.error_save),
                    getString(R.string.try_again), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 23:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PreferencesHelper.SetStringValue(LoginActivity.this,SAVE_USER, gson.toJson(loginModel));
                    finish();
                    startActivity(new Intent(LoginActivity.this, CalculatorActivity.class));
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