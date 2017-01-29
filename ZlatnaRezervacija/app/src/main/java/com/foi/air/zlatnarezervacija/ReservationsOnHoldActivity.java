package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.foi.air.zlatnarezervacija.adapters.RequestsForCancelAdapter;
import com.foi.air.zlatnarezervacija.adapters.ReservationsOnHoldAdapter;
import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsReservationsOnHold;
import com.foi.webservice.responses.ReservationsOnHold;
import com.foi.webservice.responses.WebServiceReservationOnHold;

import java.util.ArrayList;

public class ReservationsOnHoldActivity extends AppCompatActivity implements DataLoadedListener {

    ProgressDialog progress;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    ArrayList<ReservationsOnHold> group1 = new ArrayList<ReservationsOnHold>();
    ArrayList<ReservationsOnHold> group2 = new ArrayList<ReservationsOnHold>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations_on_hold);

        /* Prikazivanje back buttona i promjena teksta u toolbaru */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.ReservationsOnHoldActivityTitle);

        getReservationsOnHold();
    }

    /* Metoda koja dohvaća sve rezervacije na čekanju i zahtjeve za otkazivanje rezervacije */
    private void getReservationsOnHold() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsReservationsOnHold();
            dataLoader1.loadReservationsOnHold((DataLoadedListener) this, "1");
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    /* Metoda koja dohvaća podatke sa WebServisa */
    @Override
    public void onDataLoaded(Object result) {
        WebServiceReservationOnHold DataArrived = (WebServiceReservationOnHold) result;
        ReservationsOnHold[] reservations = (ReservationsOnHold[]) DataArrived.getReservations();

        /* Dobivene rezervacije se prema statusu smještaju u odgovarajuću ArrayList-u */
        for (ReservationsOnHold r : reservations) {
            if(r.status.contains("1")){
                group1.add(r);
            } else{
                group2.add(r);
            }
        }

        /* Prikazivanje rezervacija u odgovrajućim RecyclerView-ima */
        recyclerView1 = (RecyclerView) findViewById(R.id.restaurant_reservations_on_hold);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        recyclerView1.setAdapter(new ReservationsOnHoldAdapter(group1, this));

        recyclerView2 = (RecyclerView) findViewById(R.id.restaurant_reservations_request_for_cancel);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(new RequestsForCancelAdapter(group2, this));

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

    @Override
    protected void onResume() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        /* Ukoliko je korisnik potvrdio ili odbio rezervaciju na čekanju ili
        * odbio ili potvrdio zathtjev za otkazivanje rezervacije korisnika se preusmjerava na menu apliakcije */
        if(sharedPreferences.getString("back", "") == "1"){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("back", "2");
            editor.commit();

            finish();
        }

        super.onResume();
    }
}
