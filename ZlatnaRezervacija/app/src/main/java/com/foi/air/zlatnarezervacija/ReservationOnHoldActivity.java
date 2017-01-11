package com.foi.air.zlatnarezervacija;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsReservationOnHold;
import com.foi.webservice.responses.FreeTables;
import com.foi.webservice.responses.WebServiceResponseReservationOnHold;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReservationOnHoldActivity extends AppCompatActivity implements DataLoadedListener {

    private String reservation_intent;
    LinearLayout linearLayout;
    CheckBox checkBox;
    ProgressDialog progress;

    private Calendar calendar;
    private Integer system_minute, sysetem_hour;

    EditText vrijeme_input;
    TextView user, date, time, persons, meals, remark;
    Button potvrdi, odbij;

    Boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_on_hold);

        reservation_intent = getIntent().getStringExtra("reservation_id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rezervacija " + reservation_intent);

        linearLayout = (LinearLayout) findViewById(R.id.checkbox_group);
        user = (TextView) findViewById(R.id.reservation_user);
        date = (TextView) findViewById(R.id.reservation_date);
        time = (TextView) findViewById(R.id.reservation_time);
        persons = (TextView) findViewById(R.id.reservation_persons);
        meals = (TextView) findViewById(R.id.reservation_meals);
        remark = (TextView) findViewById(R.id.reservation_remark);

        potvrdi = (Button) findViewById(R.id.btn_potvrdi_rezervaciju);
        potvrdi.setEnabled(false);
        potvrdi.setBackgroundColor(Color.parseColor("#808080"));

        odbij = (Button) findViewById(R.id.btn_odbij_rezervaciju);
        odbij.setEnabled(false);
        odbij.setBackgroundColor(Color.parseColor("#808080"));

        calendar = Calendar.getInstance();
        sysetem_hour = calendar.get(Calendar.HOUR_OF_DAY);
        system_minute = calendar.get(Calendar.MINUTE);

        vrijeme_input = (EditText) findViewById(R.id.input_time_reservation_on_hold);

        ButterKnife.bind(this);

        loadReservation();
    }

    private void loadReservation() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsReservationOnHold();
            dataLoader1.loadReservationOnHold(this, reservation_intent.toString());
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataLoaded(Object result) {
        WebServiceResponseReservationOnHold data = (WebServiceResponseReservationOnHold) result;

        user.setText(data.getUser_first_name() + " " + data.getUser_last_name());
        date.setText(data.getDate());
        time.setText(data.getTime_arrival());
        persons.setText(data.getPersons());
        meals.setText(data.getMeals());
        remark.setText(data.getRemark());

        odbij.setEnabled(true);
        odbij.setBackgroundColor(getResources().getColor(R.color.btnColor));

        String label;
        flag = false;

        for (FreeTables t : data.getTables()){

            if(!(t.getFree_from().contains("-") && t.getFree_from().contains("-"))){
                label = t.getLabel() + " (" + t.getNumber_of_seats() + " mjesta) - Vrijeme : " + t.getFree_from() + " - " + t.getFree_to();

                checkBox = new CheckBox(this);
                checkBox.setText(label);
                checkBox.setId(Integer.parseInt(t.getId()));
                checkBox.setTextColor(getResources().getColor(R.color.btnColor));

                linearLayout.addView(checkBox);
                flag = true;
            }
        }

        if(!flag){
            TextView text = new TextView(this);
            text.setText("Nema slobodnih stolova");
            linearLayout.addView(text);
        } else{
            potvrdi.setEnabled(true);
            potvrdi.setBackgroundColor(getResources().getColor(R.color.btnColor));
        }
        
        progress.dismiss();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.input_time_reservation_on_hold)
    public void TimePicker(){
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            TimePickerDialog timePickerDialog = new TimePickerDialog(ReservationOnHoldActivity.this, myTimeListener, sysetem_hour, system_minute, true);
            timePickerDialog.setTitle(null);
            return timePickerDialog;
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener(){
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            showTime(i, i1);
        }
    };

    private void showTime(int sati, int minute) {
        vrijeme_input.setTextColor(getResources().getColor(R.color.btnColor));
        vrijeme_input.setText(new StringBuilder().append(sati).append(" : ").append(minute).append(" : 00"));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
