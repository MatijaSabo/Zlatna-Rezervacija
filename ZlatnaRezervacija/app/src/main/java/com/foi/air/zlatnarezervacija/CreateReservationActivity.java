package com.foi.air.zlatnarezervacija;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.responses.WebServiceResponseRegistration;
import com.foi.webservice.data_loaders.WsCreateReservationDataLoader;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateReservationActivity extends AppCompatActivity implements DataLoadedListener{

    String name_intent, id_intent,notifications_intent;
    String napomene;

    TextInputLayout broj_osoba_label, broj_jela_label, datum_label, vrijeme_label;
    EditText broj_osoba_input, broj_jela_input, napomene_input, datum_input, vrijeme_input;

    private Calendar calendar;
    private Integer system_day, system_month, system_year;
    private Integer system_minute, sysetem_hour;

    ProgressDialog dialog;

    TextView alert_person, alert_date, alert_time, alert_persons, alert_meals, alert_remark, alert_notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Dohvačanje podataka koji se šalju preko intenta */
        name_intent = getIntent().getStringExtra("user_name");
        id_intent = getIntent().getStringExtra("user_id");
        notifications_intent = getIntent().getStringExtra("notifications");

        setContentView(R.layout.activity_create_reservation);

        /* Prikazivanje back buttona u toolbaru */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ReservationTitle);

        /* Spajanje elemenata sa dizajna sa varijablama */
        broj_osoba_label = (TextInputLayout) findViewById(R.id.textinputlayout_persons);
        broj_jela_label = (TextInputLayout) findViewById(R.id.textinputlayout_meals);
        datum_label = (TextInputLayout) findViewById(R.id.textinputlayout_date);
        vrijeme_label = (TextInputLayout) findViewById(R.id.textinputlayout_time);

        broj_osoba_input = (EditText) findViewById(R.id.input_number_of_persons);
        broj_jela_input = (EditText) findViewById(R.id.input_number_of_meals);
        datum_input = (EditText) findViewById(R.id.input_date);
        vrijeme_input = (EditText) findViewById(R.id.input_time);
        napomene_input = (EditText) findViewById(R.id.input_remark);

        /* Dobivanje trenutnog datuma i vremena te spremanje vrijednosti u varijable */
        calendar = Calendar.getInstance();
        system_day = calendar.get(Calendar.DAY_OF_MONTH);
        system_month = calendar.get(Calendar.MONTH);
        system_year = calendar.get(Calendar.YEAR);
        sysetem_hour = calendar.get(Calendar.HOUR_OF_DAY);
        system_minute = calendar.get(Calendar.MINUTE);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{

                /* Provjera da li je korisnik unio neki od podataka u formu te ukoliko nije zatvara se aktivnnost,
                * ukoliko je unio neki od podataka prikazuje mu se alert */
                if((broj_osoba_input.length() < 1) && (broj_jela_input.length() < 1) &&
                        (datum_input.length() < 1) && (vrijeme_input.length() <1) &&
                        (napomene_input.length() < 1)){
                    finish();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle(R.string.BackAlertTitle);
                    builder.setMessage(R.string.BackAlertMessage);
                    builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
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
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        /* Provjera da li je korisnik unio neki od podataka u formu te ukoliko nije zatvara se aktivnnost,
        * ukoliko je unio neki od podataka prikazuje mu se alert */
        if((broj_osoba_input.length() < 1) && (broj_jela_input.length() < 1) &&
                (datum_input.length() < 1) && (vrijeme_input.length() < 1) &&
                (napomene_input.length() < 1)){

            finish();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.BackAlertTitle);
            builder.setMessage(R.string.BackAlertMessage);
            builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();
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
    }

    @OnClick(R.id.btn_reserve)
    public void Click(View view){

        /* Gašenje virtualne tipkvnice ukliko je otvorena, te makivanje fokusa sa označenog elementa */
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            view.clearFocus();
        }

        boolean broj_osoba_validator, broj_jela_validator, datum_validator, vrijeme_validator, napomena_validator;

        /* Validacija korisnikovog unosa te prikazivanje poruka grešaka ukoliko neki uvijet nije zadovoljen */
        if(broj_osoba_input.length()  < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError(getString(R.string.PersonsError));
            broj_osoba_validator = false;
        } else if(Integer.parseInt(broj_osoba_input.getText().toString()) < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError(getString(R.string.PersonsError2));
            broj_osoba_validator = false;
        } else{
            broj_osoba_label.setErrorEnabled(false);
            broj_osoba_validator = true;
        }

        if(broj_jela_input.length()  < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError(getString(R.string.MealsError));
            broj_jela_validator = false;
        } else if(Integer.parseInt(broj_jela_input.getText().toString()) < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError(getString(R.string.MealsError2));
            broj_jela_validator = false;
        } else{
            broj_jela_label.setErrorEnabled(false);
            broj_jela_validator = true;
        }

        if(datum_input.length() < 1){
            datum_label.setErrorEnabled(true);
            datum_label.setError(getString(R.string.DateError));
            datum_validator = false;
        } else {
            datum_label.setErrorEnabled(false);
            datum_validator = true;
        }

        if(vrijeme_input.length() < 1){
            vrijeme_label.setErrorEnabled(true);
            vrijeme_label.setError(getString(R.string.TimeError));
            vrijeme_validator = false;
        } else{
            vrijeme_label.setErrorEnabled(false);
            vrijeme_validator = true;
        }

        if(napomene_input.length() > 500){
            napomena_validator = false;
        } else {
            napomena_validator = true;
        }

        /* Ukoliko je korisnik unio sve podatke dobro poziva se funkcija za kreiranje rezervacije */
        if(broj_osoba_validator && broj_jela_validator && datum_validator && vrijeme_validator && napomena_validator){
            createReservation();
        }
    }

    private void createReservation() {
        
        if(napomene_input.getText().length() > 0){
            napomene = napomene_input.getText().toString();
        } else {
            napomene = getString(R.string.EmptyRemark);
        }

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {

            /* Spajanje elemenata sa layouta sa varijablama te postavljanje podataka u njih */
            LayoutInflater li = LayoutInflater.from(this);
            View alertView = li.inflate(R.layout.alert_create_reservation, null);

            alert_person = (TextView) alertView.findViewById(R.id.alert_person_name);
            alert_date = (TextView) alertView.findViewById(R.id.alert_date);
            alert_time = (TextView) alertView.findViewById(R.id.alert_time);
            alert_persons = (TextView) alertView.findViewById(R.id.alert_persons);
            alert_meals = (TextView) alertView.findViewById(R.id.alert_meals);
            alert_remark = (TextView) alertView.findViewById(R.id.alert_remark);
            alert_notifications = (TextView) alertView.findViewById(R.id.alert_notifications);

            alert_person.setText(name_intent);
            alert_date.setText(datum_input.getText().toString());
            alert_time.setText(vrijeme_input.getText().toString());
            alert_persons.setText(broj_osoba_input.getText().toString());
            alert_meals.setText(broj_jela_input.getText().toString());

            if(napomene_input.length() < 1){
                alert_remark.setText(R.string.EmptyRemark);
            } else{
                alert_remark.setText(napomene_input.getText().toString());
            }

            if(notifications_intent.endsWith("1")){
                alert_notifications.setText(R.string.Vibration);
            } else{
                alert_notifications.setText(R.string.Notification);
            }

            /* Kreiranje alert dialoga sa vlastitim layoutom */
            final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.ReservationTitle);
            builder.setView(alertView);
            builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    sendReservation();
                }
            })
                    .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    View view = CreateReservationActivity.this.getCurrentFocus();
                    if (view != null) { view.clearFocus(); }

                    dialog.dismiss();
                }
            });

            builder.create().show();
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.input_date)
    public void Date_Click(View view){

        /* Zatvaranje virtualne tipkovnice te brisanje fokusa sa elementa s fokusom*/
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            view.clearFocus();
        }

        showDialog(999);

    }

    @OnClick(R.id.input_time)
    public void Time_Click(View view){

        /* Zatvaranje virtualne tipkovnice te brisanje fokusa sa elementa s fokusom*/
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            view.clearFocus();
        }

        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            /* Otvaranje DatePickerDialoga sa trenutnim datumom */
            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateReservationActivity.this, myDateListener, system_year, system_month, system_day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.setTitle(null);
            return datePickerDialog;
        }

        if(id == 998){
            /* Otvaranje TimePickerDialoga sa trenutnim vremenom */
            TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReservationActivity.this, myTimeListener, sysetem_hour, system_minute, true);
            timePickerDialog.setTitle(null);
            return timePickerDialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener(){
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            showDate(i, i1+1, i2);
        }
    };

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            showTime(i, i1);
        }
    };

    /* Metoda koja spaja odabrane podatke iz TimePickerDialoga sa EditTextom na zaslonu */
    private void showTime(Integer sati, Integer minute) {
        vrijeme_input.setTextColor(getResources().getColor(R.color.btnColor));
        vrijeme_input.setText(new StringBuilder().append(sati).append(" : ").append(minute).append(" : 00"));
    }

    /* Metoda koja spaja odabrane podatke iz DatePickerDialoga sa EditTextom na zaslonu */
    private void showDate(Integer year, Integer i, Integer day) {
        datum_input.setTextColor(getResources().getColor(R.color.btnColor));
        datum_input.setText(new StringBuilder().append(year).append(" - ").append(i).append(" - ").append(day));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /* Metoda koja šalje podatke na WebServis */
    private void sendReservation() {
        dialog = ProgressDialog.show(this, getString(R.string.SendReservationInProgress), getString(R.string.PleaseWait));

        int broj_osoba = Integer.parseInt(broj_osoba_input.getText().toString());
        int broj_jela = Integer.parseInt(broj_jela_input.getText().toString());
        int user_id = Integer.parseInt(id_intent);

        DataLoader dataLoader;
        dataLoader = new WsCreateReservationDataLoader();
        dataLoader.loadReservationCreateStatus(this, user_id, broj_osoba, datum_input.getText().toString(), vrijeme_input.getText().toString(), broj_jela, napomene);
    }

    /* Metoda koja prima odgovor sa WebServisa te obrađuje te podatke */
    @Override
    public void onDataLoaded(Object result) {
        WebServiceResponseRegistration Wsresult = (WebServiceResponseRegistration) result;

        dialog.dismiss();

        if(Wsresult.getStatus().equals("1")){
            Toast.makeText(this, R.string.SuccessCreatingReservation, Toast.LENGTH_SHORT).show();
            finish();
        } else{
            Toast.makeText(this, R.string.ErrorCreatingReservation, Toast.LENGTH_SHORT).show();
        }
    }
}
