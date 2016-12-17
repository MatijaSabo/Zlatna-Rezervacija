package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.responses.WebServiceResponseRegistration;
import com.foi.webservice.data_loaders.WsDataRegistrationLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by masrnec on 8.11.2016..
 */

public class RegistrationActivity extends AppCompatActivity implements DataLoadedListener {
    WebServiceResponseRegistration WSresult;
    ProgressDialog progress;

    @BindView(R.id.textinputlayout_name) TextInputLayout tilName;
    @BindView(R.id.textinputlayout_surname) TextInputLayout tilSurname;
    @BindView(R.id.textinputlayout_email) TextInputLayout tilEmail;
    @BindView(R.id.textinputlayout_phone) TextInputLayout tilPhone;
    @BindView(R.id.textinputlayout_pass) TextInputLayout tilPass;
    @BindView(R.id.textinputlayout_repass) TextInputLayout tilRePass;

    @BindView(R.id.input_name_registration) EditText nameRegistration;
    @BindView(R.id.input_surname_registration) EditText surnameRegistration;
    @BindView(R.id.input_phone_registration) EditText phoneRegistration;
    @BindView(R.id.input_email_registration) EditText emailRegistration;
    @BindView(R.id.input_password_registration) EditText passwordRegistration;
    @BindView(R.id.input_re_password_registration) EditText rePassRegistration;

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
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick(R.id.btn_registration)
    public void Click(View view) {

        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        Boolean name_validate = false;
        Boolean surname_validate = false;
        Boolean email_validate = false;
        Boolean phone_validate = false;
        Boolean pass_validate = false;
        Boolean second_pass_validate = false;

        String first_name = nameRegistration.getText().toString();
        String last_name = surnameRegistration.getText().toString();
        String email = emailRegistration.getText().toString();
        String password = passwordRegistration.getText().toString();
        String cellphone = phoneRegistration.getText().toString();
        String rePassword = rePassRegistration.getText().toString();


        if (nameRegistration.getText().toString().length() == 0) {
            tilName.setErrorEnabled(true);
            tilName.setError(getString(R.string.FirstNameError));
            name_validate=false;
        } else {
            tilName.setErrorEnabled(false);
            first_name = first_name.replace(" ","_");
            name_validate = true;
        }
        if (surnameRegistration.getText().toString().length() == 0) {
            tilSurname.setErrorEnabled(true);
            tilSurname.setError(getString(R.string.LastNameError));
            surname_validate=false;
        } else {
            tilSurname.setErrorEnabled(false);
            last_name = last_name.replace(" ","_");
            surname_validate = true;
        }
        if (emailRegistration.getText().toString().length() == 0) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.EmailError));
            email_validate=false;
        } else if (email.contains(" ") ||
                !(email.lastIndexOf("@") > 0) ||
                !(email.contains("@"))||
                !(email.contains(".")) ||
                (email.lastIndexOf("@") > email.lastIndexOf(".")) ||
                (email.contains("'")) ||
                (email.contains("#")) ||
                !((email.lastIndexOf(".") - email.lastIndexOf("@")) > 1)) {
            tilEmail.setError(getString(R.string.EmailError2));
            email_validate=false;
        } else {
            tilEmail.setErrorEnabled(false);
            email_validate = true;
        }
        if (phoneRegistration.getText().toString().length() == 0) {
            tilPhone.setErrorEnabled(true);
            tilPhone.setError(getString(R.string.PhoneError));
            phone_validate=false;
        } else {
            tilPhone.setErrorEnabled(false);
            phone_validate = true;
        }
        if (passwordRegistration.getText().toString().length() == 0) {
            tilPass.setErrorEnabled(true);
            tilPass.setError(getString(R.string.PassError));
            pass_validate=false;
        } else {
            tilPass.setErrorEnabled(false);
            pass_validate = true;
        }
        if (rePassRegistration.getText().toString().length() == 0) {
            tilRePass.setErrorEnabled(true);
            tilRePass.setError(getString(R.string.SecondPassError));
            second_pass_validate=false;
        } else if(rePassRegistration.getText().toString().equals(passwordRegistration.getText().toString())){
            tilRePass.setErrorEnabled(false);
            second_pass_validate = true;

            } else {
            tilRePass.setError(getString(R.string.EqualsPassError));
            second_pass_validate=false;
            }

        if((name_validate == true) && (surname_validate == true) && (email_validate == true) && (phone_validate == true) && (pass_validate == true) && (second_pass_validate == true)){
            int pass = password.hashCode();
            int phone=Integer.parseInt(cellphone);

            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            if(cm.getActiveNetworkInfo() != null){

                progress = ProgressDialog.show(this, getString(R.string.RegistrationInProgress), getString(R.string.PleaseWait));

                WSresult = null;
                DataLoader dataLoader;
                dataLoader = new WsDataRegistrationLoader();
                dataLoader.loadDataRegistration(this,first_name,last_name,phone,email,pass);
            }

            else{
                Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
            }

        }
    }
    @Override
    public void onDataLoaded(Object result) {
        WSresult = (WebServiceResponseRegistration) result;

        if(WSresult.getStatus().contains("1")){
            progress.dismiss();

            Toast.makeText(this, R.string.RegistrationSuccess, Toast.LENGTH_LONG).show();
            finish();
        }

        else{
            progress.dismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.RegistrationFaildMessage)
                    .setTitle(R.string.RegistrationFailedTitle)
                    .setCancelable(false)
                    .setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditText email = (EditText) findViewById(R.id.input_email_registration);
                            email.requestFocus();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
