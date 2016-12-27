package com.amaysim.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amaysim.testproject.model.Collection;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private Button btn_login;
    private TextInputLayout ti_email;
    private TextInputLayout ti_password;
    private EditText et_email;
    private EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_login = (Button) findViewById(R.id.btn_login);
        ti_email = (TextInputLayout) findViewById(R.id.ti_email);
        ti_password = (TextInputLayout) findViewById(R.id.ti_password);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ti_email.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ti_password.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(new LoginValidationListener() {
                    @Override
                    public void onAccessGranted() {
                        startActivity(new Intent(LoginActivity.this, TabbedActivity.class));
                        finish();
                    }
                });
            }
        });
    }

    interface LoginValidationListener {
        void onAccessGranted();
    }

    private void validate(LoginValidationListener loginValidationListener) {
        ti_email.setErrorEnabled(true);
        ti_password.setErrorEnabled(true);
        Collection collection = Collection.first(Collection.class);
        if (collection == null)
            return;
        String accounts = collection.getAccounts();
        String email = "";
        try {
            JSONObject attributes = new JSONObject(accounts);
            email = attributes.getString("email-address");
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isValid = true;

        if (TextUtils.isEmpty(et_email.getText().toString())) {
            ti_email.setError("Email must not be empty");
            isValid = false;
        } else {
            if (!Utils.validateEmail(et_email.getText().toString())) {
                ti_email.setError("Email must be valid");
                isValid = false;
            } else {
                if (et_email.getText().toString().equals(email)) {
                    isValid = true;
                } else {
                    ti_email.setError("Invalid Credentials");
                    isValid = false;
                }
            }
        }

        if (TextUtils.isEmpty(et_password.getText().toString())) {
            ti_password.setError("Password must not be empty");
            isValid = false;
        }

        if (isValid)
            loginValidationListener.onAccessGranted();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
