package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.foi.air.zlatnarezervacija.adapters.UserReservationsRecycleAdapter;
import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.responses.ReservationItemDetails;
import com.foi.webservice.responses.WebServiceReservationResponse;
import com.foi.webservice.data_loaders.WsReservationsDataLoader;

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
        if(item.getTitle()==getString(com.foi.air.zlatnarezervacija.R.string.Legend)){
            final AlertDialog.Builder builder = new AlertDialog.Builder(UserReservationsActivity.this);
            builder.setCancelable(true);
            builder.setView(R.layout.settings_about_characters);
            builder.create().show();
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.legend,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
