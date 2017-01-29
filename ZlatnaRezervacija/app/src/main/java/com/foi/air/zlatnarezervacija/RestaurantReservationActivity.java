package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.foi.air.zlatnarezervacija.adapters.RestaurantReservationsRecycleAdapter;
import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsRestaurantReservations;
import com.foi.webservice.responses.ReservationItemDetails;
import com.foi.webservice.responses.WebServiceReservationResponse;

import java.util.ArrayList;

public class RestaurantReservationActivity extends AppCompatActivity implements DataLoadedListener{
    private RecyclerView recyclerView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_reservation);

        /* Prikaz back buttona i promjena teksta u toolbaru */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.RestaurantReservationsTitle);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));

            /* Pozivanje skripte koja vraća sve potvrđene rezervacije restorana */
            DataLoader dataLoader1;
            dataLoader1 = new WsRestaurantReservations();
            dataLoader1.loadRestaurantReservations(this, "1");
        } else{
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_LONG).show();
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Dohvačanje dobivenih podataka sa WebServisa */
    @Override
    public void onDataLoaded(Object result) {
        WebServiceReservationResponse DataArrived = (WebServiceReservationResponse) result;

        /* Smještanje rezervacija u ArrayList kako bi se one prikazale u RecyclerView-u*/
        ReservationItemDetails[] items = (ReservationItemDetails[]) DataArrived.getReservations();
        ArrayList<ReservationItemDetails> item = new ArrayList<ReservationItemDetails>();

        for (ReservationItemDetails m: items) { item.add(m); }

        /* Spajanje dobivenih rezervacija sa RecyclerView-om */
        recyclerView = (RecyclerView) findViewById(R.id.restaurant_reservations_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RestaurantReservationsRecycleAdapter(item));

        progress.dismiss();
    }
}
