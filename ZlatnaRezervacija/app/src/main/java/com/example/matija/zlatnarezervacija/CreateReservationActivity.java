package com.example.matija.zlatnarezervacija;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class CreateReservationActivity extends AppCompatActivity {

    String name_intent, id_intent;

    TextInputLayout broj_osoba_label, broj_jela_label, datum_label, vrijeme_label;
    EditText broj_osoba_input, broj_jela_input, napomene_input;
    TextView datum_input, vrijeme_input;

    private Calendar calendar;
    private Integer system_day, system_month, system_year;
    private Integer system_minute, sysetem_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name_intent = getIntent().getStringExtra("user_name");
        id_intent = getIntent().getStringExtra("user_id");
        setContentView(R.layout.activity_create_reservation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ReservationTitle);

        broj_osoba_label = (TextInputLayout) findViewById(R.id.textinputlayout_persons);
        broj_jela_label = (TextInputLayout) findViewById(R.id.textinputlayout_meals);

        broj_osoba_input = (EditText) findViewById(R.id.input_number_of_persons);
        broj_jela_input = (EditText) findViewById(R.id.input_number_of_meals);
        datum_input = (TextView) findViewById(R.id.input_date);
        vrijeme_input = (TextView) findViewById(R.id.input_time);
        napomene_input = (EditText) findViewById(R.id.input_remark);

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
                if((broj_osoba_input.length() < 1) && (broj_jela_input.length() < 1) &&
                        (datum_input.getText().toString().equals("Datum nije odabran")) &&
                        (vrijeme_input.getText().toString().equals("Vrijeme nije odabrano")) &&
                        (napomene_input.length() < 1)){
                    finish();
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
                    builder.setCancelable(false);
                    builder.setTitle("Povratak");
                    builder.setMessage("Jeste li sigurni da se želite vratiti? Promjene koje ste napravili neće biti spremljene.");
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
        if((broj_osoba_input.length() < 1) && (broj_jela_input.length() < 1) &&
                (datum_input.getText().toString().equals("Datum nije odabran")) &&
                (vrijeme_input.getText().toString().equals("Vrijeme nije odabrano")) &&
                (napomene_input.length() < 1)){
            finish();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
            builder.setCancelable(false);
            builder.setTitle("Povratak");
            builder.setMessage("Jeste li sigurni da se želite vratiti? Promjene koje ste napravili neće biti spremljene.");
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

        boolean broj_osoba_validator, broj_jela_validator, datum_validator, vrijeme_validator, napomena_validator;

        if(broj_osoba_input.length()  < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError("Unesite broj osoba");
            broj_osoba_validator = false;
        } else if(Integer.parseInt(broj_osoba_input.getText().toString()) < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError("Broj osoba mora biti minimalno 1");
            broj_osoba_validator = false;
        } else{
            broj_osoba_label.setErrorEnabled(false);
            broj_osoba_validator = true;
        }

        if(broj_jela_input.length()  < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError("Unesite broj jela");
            broj_jela_validator = false;
        } else if(Integer.parseInt(broj_jela_input.getText().toString()) < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError("Broj jela mora biti minimalno 1");
            broj_jela_validator = false;
        } else{
            broj_jela_label.setErrorEnabled(false);
            broj_jela_validator = true;
        }

        if(datum_input.getText().toString().equals("Datum nije odabran")){
            datum_input.setTextColor(getResources().getColor(R.color.errorColor));
            datum_input.setText("Datum nije odabran");
            datum_validator = false;
        } else {
            datum_validator = true;
        }

        if(vrijeme_input.getText().toString().equals("Vrijeme nije odabrano")){
            vrijeme_input.setTextColor(getResources().getColor(R.color.errorColor));
            vrijeme_input.setText("Vrijeme nije odabrano");
            vrijeme_validator = false;
        } else{
            vrijeme_validator = true;
        }

        if(napomene_input.length() > 500){
            napomena_validator = false;
        } else {
            napomena_validator = true;
        }

        if(broj_osoba_validator && broj_jela_validator && datum_validator && vrijeme_validator && napomena_validator){
            createReservation();
        } else{
            Toast.makeText(this, "Forma nije prošla validaciju", Toast.LENGTH_SHORT).show();
        }
    }

    private void createReservation() {
        String string = "<p>Slanje rezervacije sa ovim podacima?</p>"
                + "<b>Ime i prezime: </b>" + name_intent + "<br><b>Datum: </b>" + datum_input.getText()
                + "<br><b>Vrijeme: </b>" + vrijeme_input.getText() + "<br><b>Broj osoba: </b>" + broj_osoba_input.getText()
                + "<br><b>Broj jela: </b>" + broj_jela_input.getText() +  "<br><b>Napomene: </b>" + napomene_input.getText()
                + "<br><b>Obavijest: </b><br><br><i>Za promjenu načina primanja obavijsti posjesite postavke aplikacije</i>";

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(CreateReservationActivity.this);
            builder.setCancelable(true);
            builder.setTitle("Rezervacija");
            builder.setMessage(Html.fromHtml(string));
            builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getApplication(), "Šaljem rezervaciju", Toast.LENGTH_LONG).show();
                }
            })
                    .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.input_date_button)
    public void Date_Click(View view){
        showDialog(999);
    }

    @OnClick(R.id.input_time_button)
    public void Time_Click(View view){
        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            DatePickerDialog datePickerDialog = new DatePickerDialog(CreateReservationActivity.this, myDateListener, system_year, system_month, system_day);
            datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
            datePickerDialog.setTitle(null);
            return datePickerDialog;
        }

        if(id == 998){
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

    private void showTime(Integer sati, Integer minute) {
        vrijeme_input.setTextColor(getResources().getColor(R.color.btnColor));
        vrijeme_input.setText(new StringBuilder().append(sati).append(" : ").append(minute).append(" : 00"));
    }

    private void showDate(Integer year, Integer i, Integer day) {
        datum_input.setTextColor(getResources().getColor(R.color.btnColor));
        datum_input.setText(new StringBuilder().append(year).append(" - ").append(i).append(" - ").append(day));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
