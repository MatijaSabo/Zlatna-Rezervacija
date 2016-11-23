package com.example.matija.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webservice.DataLoadedListener;
import com.example.webservice.DataLoader;
import com.example.webservice.WebServiceCaller;
import com.example.webservice.WebServiceHandler;
import com.example.webservice.WebServiceResponse;
import com.example.webservice.WsDataLoader;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DataLoadedListener {

    @BindView(R.id.btn_login)
    Button mainButton;
    @BindView(R.id.input_email)
    EditText emailText;
    @BindView(R.id.input_password)
    EditText passText;
    ProgressDialog progress;

    TextInputLayout tilEmail;
    TextInputLayout tilPass;

    WebServiceResponse WSresult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void mainButtonClick(View view) {
        boolean email_validate = true;
        boolean pass_validate = true;
        tilEmail = (TextInputLayout) findViewById(R.id.emailtextlayout);
        tilPass = (TextInputLayout) findViewById(R.id.passtextlayout);

        if (emailText.getText().length() == 0) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.EmailError));
            email_validate = false;
        } else if ((emailText.getText().toString().contains(" ") ||
                !(emailText.getText().toString().lastIndexOf("@") > 0) ||
                !(emailText.getText().toString().contains("@")) ||
                !(emailText.getText().toString().contains(".")) ||
                (emailText.getText().toString().lastIndexOf("@") > emailText.getText().toString().lastIndexOf("."))) ||
                (emailText.getText().toString().contains("'")) ||
                (emailText.getText().toString().contains("#")) ||
                !((emailText.getText().toString().lastIndexOf(".") - emailText.getText().toString().lastIndexOf("@")) > 1 )) {
            tilEmail.setErrorEnabled(true);
            tilEmail.setError(getString(R.string.EmailError2));
            email_validate = false;
        } else {
            tilEmail.setError(null);
            email_validate = true;
        }

        if (passText.getText().length() == 0) {
            tilPass.setErrorEnabled(true);
            tilPass.setError(getString(R.string.PassError));
            pass_validate = false;
        } else {
            tilPass.setError(null);
            pass_validate = true;
        }

        if (email_validate == true && pass_validate == true) {
            String password = passText.getText().toString();
            Integer hashPass = password.hashCode();

            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null) {

                progress = ProgressDialog.show(this, getString(R.string.LoginInProgres), getString(R.string.PleaseWait));

                WSresult = null;
                DataLoader dataLoader;
                dataLoader = new WsDataLoader();
                dataLoader.loadData(this, emailText.getText().toString(), hashPass);
            } else {
                Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }


    @OnClick(R.id.link_registration)
    public void Click(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.Exit_alert_title);
        builder.setMessage(R.string.Exit_alert_message);
        builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                System.exit(0);
            }
        })

                .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.create().show();
    }

    @Override
    public void onDataLoaded(Object result) {
        WSresult = (WebServiceResponse) result;

        progress.dismiss();

        if (WSresult.getStatus().endsWith("1")) {
            if (WSresult.getRole_id().endsWith("1")) {
                Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                intent.putExtra("name", WSresult.getName());
                intent.putExtra("email", WSresult.getEmail());
                intent.putExtra("role_id", WSresult.getRole_id());
                intent.putExtra("user_id", WSresult.getUser_id());
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("name", WSresult.getName());
                intent.putExtra("email", WSresult.getEmail());
                intent.putExtra("role_id", WSresult.getRole_id());
                intent.putExtra("user_id", WSresult.getUser_id());
                startActivity(intent);
            }
        } else {
            Toast.makeText(this, R.string.LoginFailed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        if((tilEmail != null) && (tilPass != null)){
            if(tilEmail.isErrorEnabled() == true){

                tilEmail.setErrorEnabled(false);
            }

            if(tilPass.isErrorEnabled() == true){
                tilPass.setErrorEnabled(false);
            }
        }

        passText.setText("");
        passText.clearFocus();
        emailText.clearFocus();
        super.onResume();
    }
}


