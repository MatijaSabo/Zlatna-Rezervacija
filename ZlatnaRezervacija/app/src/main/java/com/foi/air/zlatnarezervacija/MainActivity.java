package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.responses.WebServiceResponse;
import com.foi.webservice.data_loaders.WsDataLoader;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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

        /* Sakrivanje toolbara */
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void mainButtonClick(View view) {

        /* Dobivanje tokena sa Firebasea */
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("TOKEN= ",token);

        /* Zatvaranje virtualne tipkovnice te birsanje fokusa sa elemnta koji ima fokus */
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        /* Validacija korisničkog unosa */
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
            tilEmail.setErrorEnabled(false);
            email_validate = true;
        }

        if (passText.getText().length() == 0) {
            tilPass.setErrorEnabled(true);
            tilPass.setError(getString(R.string.PassError));
            pass_validate = false;
        } else {
            tilPass.setErrorEnabled(false);
            pass_validate = true;
        }

        /* Ukoliko je validacija prošla šalju se podaci na WebServis */
        if (email_validate == true && pass_validate == true) {
            String password = passText.getText().toString();
            Integer hashPass = password.hashCode();

            /* Provjera internet konekcije */
            ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null) {

                progress = ProgressDialog.show(this, getString(R.string.LoginInProgres), getString(R.string.PleaseWait));

                WSresult = null;
                DataLoader dataLoader;
                dataLoader = new WsDataLoader();
                dataLoader.loadData(this, emailText.getText().toString(), hashPass,token);
            } else {
                Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* Otvaranje aktivnosti registracije */
    @OnClick(R.id.link_registration)
    public void Click(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
    }

    /* Alert dialog u kojemu se pita korisika da li želi zatvoriti aplikaciju */
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

    /* Dobivanje podataka sa WebServisa */
    @Override
    public void onDataLoaded(Object result) {
        WSresult = (WebServiceResponse) result;

        if (WSresult.getStatus().endsWith("1")) {

            /* Prema dobivenom odgovoru sa WebServisa otvara se odgovarajuća aktivnost te se šalju podaci
            * preko intenta */
            if (WSresult.getRole_id().endsWith("1")) {
                Intent intent = new Intent(getApplicationContext(), AdminMenuActivity.class);
                intent.putExtra("name", WSresult.getName());
                intent.putExtra("email", WSresult.getEmail());
                intent.putExtra("role_id", WSresult.getRole_id());
                intent.putExtra("user_id", WSresult.getUser_id());

                progress.dismiss();

                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                intent.putExtra("name", WSresult.getName());
                intent.putExtra("email", WSresult.getEmail());
                intent.putExtra("role_id", WSresult.getRole_id());
                intent.putExtra("user_id", WSresult.getUser_id());
                intent.putExtra("notifications", WSresult.getNotifications());

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("obavijest", WSresult.getNotifications());
                editor.commit();

                progress.dismiss();

                startActivity(intent);
            }
        } else {
            progress.dismiss();
            Toast.makeText(this, R.string.LoginFailed, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        /* Brisanje poruka greške kada se korisnik vraća na aktivnost */
        if((tilEmail != null) && (tilPass != null)){
            if(tilEmail.isErrorEnabled() == true){

                tilEmail.setErrorEnabled(false);
            }

            if(tilPass.isErrorEnabled() == true){
                tilPass.setErrorEnabled(false);
            }
        }

        /* Brisanje podataka lozinke i brisanje fokusa sa elemenata kada se korisnik vraća na aktivnost */
        passText.setText("");
        passText.clearFocus();
        emailText.clearFocus();
        super.onResume();
    }
}


