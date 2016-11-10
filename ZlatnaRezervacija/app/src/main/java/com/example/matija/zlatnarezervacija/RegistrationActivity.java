package com.example.matija.zlatnarezervacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservice.WebService;
import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceRequestRegistration;
import com.example.webservice.WebServiceResponse;
import com.squareup.okhttp.OkHttpClient;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by masrnec on 8.11.2016..
 */

public class RegistrationActivity extends AppCompatActivity {
    Boolean validate = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.registration);
        ButterKnife.bind(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.btn_registration)
    public void Click(View view) {
        TextInputLayout tilName = (TextInputLayout) findViewById(R.id.textinputlayout_name);
        TextInputLayout tilSurname = (TextInputLayout) findViewById(R.id.textinputlayout_surname);
        TextInputLayout tilEmail = (TextInputLayout) findViewById(R.id.textinputlayout_email);
        TextInputLayout tilPhone = (TextInputLayout) findViewById(R.id.textinputlayout_phone);
        TextInputLayout tilPass = (TextInputLayout) findViewById(R.id.textinputlayout_pass);
        TextInputLayout tilRePass = (TextInputLayout) findViewById(R.id.textinputlayout_repass);
        EditText nameRegistration = (EditText) findViewById(R.id.input_name_registration);
        String first_name = nameRegistration.getText().toString();
        EditText surnameRegistration = (EditText) findViewById(R.id.input_surname_registration);
        String last_name = surnameRegistration.getText().toString();
        EditText emailRegistration = (EditText) findViewById(R.id.input_email_registration);
        String email = emailRegistration.getText().toString();
        EditText passwordRegistration = (EditText) findViewById(R.id.input_password_registration);
        String password = passwordRegistration.getText().toString();
        EditText phoneRegistration = (EditText) findViewById(R.id.input_phone_registration);
        String cellphone = phoneRegistration.getText().toString();
        EditText rePassRegistration = (EditText) findViewById(R.id.input_re_password_registration);
        String rePassword = rePassRegistration.getText().toString();
        if (nameRegistration.getText().toString().length() == 0) {
            tilName.setErrorEnabled(true);
            tilName.setError("Unesite ime");
            validate=false;
        } else {
            tilName.setError(null);
            first_name = first_name.replace(" ","_");
            validate = true;
        }
        if (surnameRegistration.getText().toString().length() == 0) {
            tilSurname.setErrorEnabled(true);
            tilSurname.setError("Unesite prezime");
            validate=false;
        } else {
            tilSurname.setError(null);
            last_name = last_name.replace(" ","_");
            validate = true;
        }
        if (emailRegistration.getText().toString().length() == 0) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError("Unesite email");
            validate=false;
        } else if (email.contains(" ") || !(email.contains("@"))|| !(email.contains(".")) || (email.lastIndexOf("@") > email.lastIndexOf("."))) {
            tilEmail.setError("Unesite pravilan email");
        } else {
            tilEmail.setError(null);
            validate = true;
        }
        if (phoneRegistration.getText().toString().length() == 0) {
            tilPhone.setErrorEnabled(true);
            tilPhone.setError("Unesite kontaktni broj");
            validate=false;
        } else {
            tilPhone.setError(null);
            validate = true;
        }
        if (passwordRegistration.getText().toString().length() == 0) {
            tilPass.setErrorEnabled(true);
            tilPass.setError("Unesite lozinku");
            validate=false;
        } else {
            tilPass.setError(null);
            validate = true;
        }
        if (rePassRegistration.getText().toString().length() == 0) {
            tilRePass.setErrorEnabled(true);
            tilRePass.setError("Unesite ponovljenu lozinku");
            validate=false;
        } else if(rePassRegistration.getText().toString().equals(passwordRegistration.getText().toString())){
            tilRePass.setError(null);
            validate = true;

            } else {
            tilRePass.setError("Unesite jednake lozinke");
            validate=false;
            }

        if(validate==true){
            int pass = password.hashCode();
            int phone=Integer.parseInt(cellphone);


           WebServiceCaller webServiceCaller = new WebServiceCaller();
           webServiceCaller.registrateUser(first_name,last_name,email,phone,pass,2);

        }
    }


    }
