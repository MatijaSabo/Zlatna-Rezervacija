package com.example.matija.zlatnarezervacija;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class CreateReservationActivity extends AppCompatActivity {

    TextInputLayout broj_osoba_label, broj_jela_label, datum_label, vrijeme_label;
    EditText broj_osoba_input, broj_jela_input, datum_input, vrijeme_input, napomene_input;

    private Calendar calendar;
    private Integer day, month, year, system_day, system_month, system_year, selected_day, selected_month, selected_year;
    private int minute, hour, selected_minute, selected_hour, system_minute, sysetem_hour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ReservationTitle);

        broj_osoba_label = (TextInputLayout) findViewById(R.id.textinputlayout_persons);
        broj_jela_label = (TextInputLayout) findViewById(R.id.textinputlayout_meals);
        datum_label = (TextInputLayout) findViewById(R.id.textinputlayout_date);
        vrijeme_label = (TextInputLayout) findViewById(R.id.textinputlayout_time);

        broj_osoba_input = (EditText) findViewById(R.id.input_number_of_persons);
        broj_jela_input = (EditText) findViewById(R.id.input_number_of_meals);
        datum_input = (EditText) findViewById(R.id.input_date);
        vrijeme_input = (EditText) findViewById(R.id.input_time);

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

    @OnClick(R.id.btn_reserve)
    public void Click(View view){

        if(broj_osoba_input.length()  < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError("Unesite broj osoba");
        } else if(Integer.parseInt(broj_osoba_input.getText().toString()) < 1){
            broj_osoba_label.setErrorEnabled(true);
            broj_osoba_label.setError("Broj osoba mora biti minimalno 1");
        } else{
            broj_osoba_label.setErrorEnabled(false);
        }

        if(broj_jela_input.length()  < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError("Unesite broj jela");
        } else if(Integer.parseInt(broj_jela_input.getText().toString()) < 1){
            broj_jela_label.setErrorEnabled(true);
            broj_jela_label.setError("Broj jela mora biti minimalno 1");
        } else{
            broj_jela_label.setErrorEnabled(false);
        }

        if(datum_input.length() < 1){
            datum_label.setErrorEnabled(true);
            datum_label.setError("Unesite datum");
        } else {
            Integer a = datum_input.getText().toString().indexOf("-");
            Integer b = datum_input.getText().toString().lastIndexOf("-");

            if((a == -1) || (b == -1) || (a == b)){
                datum_label.setErrorEnabled(true);
                datum_label.setError("Datum nije ispravnog oblika");
            } else {
                selected_year = Integer.parseInt(datum_input.getText().toString().substring(0, a));
                selected_month = Integer.parseInt(datum_input.getText().toString().substring(a+1, b));
                selected_day = Integer.parseInt(datum_input.getText().toString().substring(b+1, datum_input.length()));

                if((selected_year > 2100) || (selected_month > 12) || (selected_month < 1) || (selected_day < 1) || (selected_day > 31)
                        || (selected_year < 1) || (((selected_month - 2) == 0) && (selected_day > 29))){
                    datum_label.setErrorEnabled(true);
                    datum_label.setError("Datum nije ispravnog oblika");
                } else if((system_year > selected_year) ||
                        (((system_year - selected_year) == 0) && ((system_month+1) > selected_month)) ||
                        (((system_year - selected_year) == 0) && (((system_month+1) - selected_month) == 0) && (system_day > selected_day))){
                    datum_label.setErrorEnabled(true);
                    datum_label.setError("Datum ne smije biti u prošlosti");
                } else{
                    datum_label.setErrorEnabled(false);
                }
            }
        }

        if(vrijeme_input.length() < 1){
            vrijeme_label.setErrorEnabled(true);
            vrijeme_label.setError("Unsetite vrijeme");
        } else{
            Integer a = vrijeme_input.getText().toString().indexOf(":");
            Integer b = vrijeme_input.getText().toString().lastIndexOf(":");

            if((a == -1) || (b == -1) || (a == b) ||(a > 2) || ((b-a) > 3) || (a == 0) || ((b-a) == 1) ||
                    ((vrijeme_input.length() - b) < 3) || ((vrijeme_input.length() - b) > 3)){
                vrijeme_label.setErrorEnabled(true);
                vrijeme_label.setError("Vrijeme nije ispravnog oblika");
            } else{
                selected_hour = Integer.parseInt(vrijeme_input.getText().toString().substring(0, a));
                selected_minute = Integer.parseInt(vrijeme_input.getText().toString().substring(a+1, b));
                Integer s = Integer.parseInt(vrijeme_input.getText().toString().substring(b+1, vrijeme_input.length()));

                if( (s < 0) || (s > 60) || (selected_hour < 0) || (selected_hour > 23) || (selected_minute < 0) || (selected_minute > 59)){
                    vrijeme_label.setErrorEnabled(true);
                    vrijeme_label.setError("Vrijeme nije ispravnog oblika");
                } else{
                    vrijeme_label.setErrorEnabled(false);
                }
            }
        }
    }

    @OnFocusChange(R.id.input_date)
    public void Date_Focus(View view){
        if(datum_input.isFocused()){

            calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            showDialog(999);
        }
    }

    @OnFocusChange(R.id.input_time)
    public void Time_Focus(View view){
        if(vrijeme_input.isFocused()){
            calendar = Calendar.getInstance();
            minute = calendar.get(Calendar.MINUTE);
            hour = calendar.get(Calendar.HOUR_OF_DAY);

            showDialog(998);
        }
    }


    @OnClick(R.id.input_date)
    public void Date_Click(View view){
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        showDialog(999);
    }

    @OnClick(R.id.input_time)
    public void Time_Click(View view){
        calendar = Calendar.getInstance();
        minute = calendar.get(Calendar.MINUTE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);

        showDialog(998);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }

        if(id == 998){
            return  new TimePickerDialog(this, myTimeListener, hour, minute, true);
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
        vrijeme_input.setText(new StringBuilder().append(sati).append(":").append(minute).append(":00"));
    }

    private void showDate(Integer year, Integer i, Integer day) {
        datum_input.setText(new StringBuilder().append(year).append("-").append(i).append("-").append(day));
    }



}
