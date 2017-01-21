package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsRequestForCancelDetails;
import com.foi.webservice.responses.WebServiceRequestForCancelDetails;

public class RequestForCancelActivity extends AppCompatActivity implements DataLoadedListener {

    private String reservation_intent;
    ProgressDialog progress;

    TextView user, date, time_arrival, time_checkout, persons, meals, remark, description;
    Button odbij, otkazi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_cancel);

        reservation_intent = getIntent().getStringExtra("reservation_id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rezervacija " + reservation_intent);

        user = (TextView) findViewById(R.id.activity_request_for_cancel_user);
        date = (TextView) findViewById(R.id.activity_request_for_cancel_date);
        time_arrival = (TextView) findViewById(R.id.activity_request_for_cancel_time_arrival);
        time_checkout = (TextView) findViewById(R.id.activity_request_for_cancel_time_checkout);
        persons = (TextView) findViewById(R.id.activity_request_for_cancel_persons);
        meals = (TextView) findViewById(R.id.activity_request_for_cancel_meals);
        remark = (TextView) findViewById(R.id.activity_request_for_cancel_remark);
        description = (TextView) findViewById(R.id.activity_request_for_cancel_description);

        odbij = (Button) findViewById(R.id.btn_odbij_zahtjev_za_otkazivanje);
        otkazi = (Button) findViewById(R.id.btn_potvrdi_zahtjev_za_otkazivanje);

        getReservationData();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getReservationData() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsRequestForCancelDetails();
            dataLoader1.loadRequestForCancelDetails(this, reservation_intent.toString());
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataLoaded(Object result) {
        WebServiceRequestForCancelDetails data = (WebServiceRequestForCancelDetails) result;

        user.setText(data.getUser_first_name() + " " + data.getUser_last_name());
        date.setText(data.getDate());
        time_arrival.setText(data.getTime_arrival());
        time_checkout.setText(data.getTime_checkout());
        persons.setText(data.getPersons());
        meals.setText(data.getMeals());
        remark.setText(data.getRemark());
        description.setText(data.getDescription());

        progress.dismiss();
    }
}
