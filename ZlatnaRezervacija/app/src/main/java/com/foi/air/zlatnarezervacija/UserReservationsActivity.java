package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.foi.air.zlatnarezervacija.adapters.UserReservationsRecycleAdapter;
import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsReservationCancelDataLoader;
import com.foi.webservice.responses.ReservationItemDetails;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;
import com.foi.webservice.responses.WebServiceReservationResponse;
import com.foi.webservice.data_loaders.WsReservationsDataLoader;

import java.util.ArrayList;

public class UserReservationsActivity extends AppCompatActivity implements DataLoadedListener{

    String user;
    private RecyclerView recyclerView;
    ProgressDialog progress;
    ArrayList<ReservationItemDetails> item1 = new ArrayList<ReservationItemDetails>();
    private FloatingActionButton floatingActionButton;
    private Boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.moje_rezervacije);
        user = getIntent().getStringExtra("user_id");

        getAllReservation();
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

        if(flag){
            WebServiceReservationCancelResponse DataArrived = (WebServiceReservationCancelResponse) result;
            if(DataArrived.getStatus().endsWith("1")){
                flag = false;

                getAllReservation();

            } else{
                Toast.makeText(this, "Otkazivanje rezervacije nije uspjelo, pokušajte ponovo...", Toast.LENGTH_LONG).show();
            }

        } else{
            WebServiceReservationResponse DataArrived = (WebServiceReservationResponse) result;
            ReservationItemDetails[] items = (ReservationItemDetails[]) DataArrived.getReservations();
            ArrayList<ReservationItemDetails> item = new ArrayList<ReservationItemDetails>();

            for (ReservationItemDetails m: items) {
                item.add(m);
                if(m.getStatus()==1 || m.getStatus()==2) {
                    item1.add(m);
                }
            }

            recyclerView = (RecyclerView) findViewById(R.id.my_reservations);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new UserReservationsRecycleAdapter(item));
        }

        floatingActionButton=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        //progress.dismiss();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.legend,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showDialog(){
        final View view = getLayoutInflater().inflate(R.layout.cancel_of_reservation, null);
        final RadioGroup rg = new RadioGroup(this);

        for(int i=0; i < 1 ; i++){
            for (ReservationItemDetails item: item1) {
                RadioButton rb = new RadioButton(this);
                rb.setId(Integer.parseInt(item.getId()));
                rb.setText("Rezervacija " + item.getId().toString());
                rg.addView(rb);
            }

            ((ViewGroup) view.findViewById(R.id.RadioGroup)).addView(rg);
        }

        final AlertDialog.Builder dialog = new AlertDialog.Builder(UserReservationsActivity.this);
        dialog.setTitle(R.string.Alert_cancel_title);
        dialog.setView(view);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText e=(EditText)view.findViewById(R.id.reason_for_cancel_of_reservation);
                if(e.getText().toString().isEmpty() || rg.getCheckedRadioButtonId()==-1){}
                else{
                    sendDataForCancelReservation(rg.getCheckedRadioButtonId() ,e.getText().toString());
                }

            }
        })
                .setNegativeButton(R.string.Alert_cancel_button_for_reservation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        dialog.show();

    }
    private void sendDataForCancelReservation(int reservation, String description){

        flag = true;

        DataLoader dataLoader;
        dataLoader = new WsReservationCancelDataLoader();
        dataLoader.loadReservationCancel(this,reservation ,description);
    }

    private void getAllReservation(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {

            DataLoader dataLoader1;
            dataLoader1 = new WsReservationsDataLoader();
            dataLoader1.loadDataMyReservations(this, user);
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

}
