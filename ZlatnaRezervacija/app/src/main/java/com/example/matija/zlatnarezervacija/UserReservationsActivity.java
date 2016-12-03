package com.example.matija.zlatnarezervacija;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.matija.zlatnarezervacija.adapters.UserReservationsRecycleAdapter;
import com.example.webservice.DataLoadedListener;
import com.example.webservice.DataLoader;
import com.example.webservice.ReservationItemDetails;
import com.example.webservice.WebServiceReservationResponse;
import com.example.webservice.WsReservationsDataLoader;

import java.util.ArrayList;

public class UserReservationsActivity extends AppCompatActivity implements DataLoadedListener{
    String user;
    private RecyclerView recyclerView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.moje_rezervacije);
        user = getIntent().getStringExtra("user_id");

        DataLoader dataLoader;
        dataLoader = new WsReservationsDataLoader();
        dataLoader.loadDataMyReservations(this, user);
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
    public void onDataLoaded(Object result) {
        WebServiceReservationResponse DataArrived = (WebServiceReservationResponse) result;
        ReservationItemDetails[] items = (ReservationItemDetails[]) DataArrived.getReservations();
        ArrayList<ReservationItemDetails> item = new ArrayList<ReservationItemDetails>();
        for (ReservationItemDetails m: items) { item.add(m); }

        recyclerView = (RecyclerView) findViewById(R.id.my_reservations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new UserReservationsRecycleAdapter(item));

        progress.dismiss();


    }
}
