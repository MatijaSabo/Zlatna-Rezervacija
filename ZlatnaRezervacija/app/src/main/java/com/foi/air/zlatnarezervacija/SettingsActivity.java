package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsDataRegistrationLoader;
import com.foi.webservice.data_loaders.WsDataSettingsLoader;
import com.foi.webservice.responses.WebServiceResponseSettings;

import butterknife.ButterKnife;

/**
 * Created by masrnec on 21.12.2016..
 */

public class SettingsActivity extends AppCompatActivity implements DataLoadedListener {
    WebServiceResponseSettings WSresult;
    private String user,type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        /*  Preuzimanje podataka poslanih aktivnosti, postavljanje back buttona i teksta u toolbaru */
        user = getIntent().getStringExtra("user_id");
        type = getIntent().getStringExtra("notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.postavke);
        /*  Provjera dostupnosti interneta */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            /*  Oznacavanje prve opcije  */
            if(type.contains("1")){

                RadioButton opcija1 = (RadioButton)findViewById(R.id.option1);
                opcija1.setChecked(true);
            }
            /*  Oznacavanje druge opcije */
            else if (type.contains("2")) {

                RadioButton opcija2 = (RadioButton)findViewById(R.id.option2);
                opcija2.setChecked(true);
            }

        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }

        ButterKnife.bind(this);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*  Postavljanje sharedPreferences sa oznacenom opcijom */
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("obavijest", type);
                editor.commit();

                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        /*  Postavljanje sharedPreferences sa oznacenom opcijom */
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("obavijest", type);

        editor.commit();

        finish();
    }

    public void onRadioButtonClicked(View view) {
            boolean checked = ((RadioButton) view).isChecked();
            switch(view.getId()) {
                /*  Provjera ukoliko je prva opcija odabrana, pozivanje skripte za zapis na bazu i zapis u SharedPrefeences */
                case R.id.option1:
                    if (checked){
                        WSresult = null;
                        type="1";
                        DataLoader dataLoader;
                        dataLoader = new WsDataSettingsLoader();
                        dataLoader.loadDataSettings(this,user,type);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("opcija", type);

                        editor.commit();
                        break;}
                /*  Provjera ukoliko je druga opcija odabrana, pozivanje skripte za zapis na bazu i zapis u SharedPrefeences */
                case R.id.option2:
                    if (checked){
                        type="2";
                        WSresult = null;
                        DataLoader dataLoader;
                        dataLoader = new WsDataSettingsLoader();
                        dataLoader.loadDataSettings(this,user,type);
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("opcija", type);

                        editor.commit();
                        break;}
            }


    }

    @Override
    public void onDataLoaded(Object result) {
        WSresult = (WebServiceResponseSettings) result;
        /*  Prikaz poruke ovisno o vracenom rezultatu skripte */
        if(WSresult.getStatus().contains("1")){
            Toast.makeText(this, R.string.settingsSucces, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, R.string.settingsUnsucces, Toast.LENGTH_SHORT).show();
        }
    }
}
