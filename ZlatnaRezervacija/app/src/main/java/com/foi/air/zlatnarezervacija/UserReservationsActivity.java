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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
    ProgressDialog progressOfCancelReservation;

    ArrayList<ReservationItemDetails> item1 = new ArrayList<ReservationItemDetails>();
    ArrayList<ReservationItemDetails> item = new ArrayList<ReservationItemDetails>();

    private UserReservationsRecycleAdapter adapter=new UserReservationsRecycleAdapter(item1);
    private UserReservationsRecycleAdapter adapterForAllReservatons=new UserReservationsRecycleAdapter(item);

    private FloatingActionButton floatingActionButton;
    private Boolean flag = false;
    private Boolean flag2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservations);

        /* Prikazivanje back buttona i promjena teksta u toolbaru */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.moje_rezervacije);

        /* Primanje podataka koji se šalju preko intenta */
        user = getIntent().getStringExtra("user_id");

        getAllReservation();

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        /* Provjera odabrane aktivnosti iz toolbara */
        if(item.getTitle()==getString(com.foi.air.zlatnarezervacija.R.string.Legend)){

            /* Prikaz legende u AlertDialogu*/
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

    /* Dohvačanje podataka sa WebServisa */
    @Override
    public void onDataLoaded(Object result) {
        /* Provjera koja skripta WebServisa pozvana */
        if(flag){
            /* Ukoliko je pozvana skripta koja radi otkazivanje rezervacije */
            WebServiceReservationCancelResponse DataArrived = (WebServiceReservationCancelResponse) result;
            progressOfCancelReservation.dismiss();

            /* Provjera dobivenih podataka sa WebServisa */
            if(DataArrived.getStatus().endsWith("1")){
                flag = false;
                getAllReservation();
            } else{
                Toast.makeText(this, R.string.UnsuccessCancelReservation, Toast.LENGTH_LONG).show();
            }

        } else{
            /* Ukoliko je pozvana skripta koja vraća sve rezarvacije korisnika */
            WebServiceReservationResponse DataArrived = (WebServiceReservationResponse) result;
            ReservationItemDetails[] items = (ReservationItemDetails[]) DataArrived.getReservations();

            /* Rezervacije se spremaju a ArrayList radi prikazivanja u RecyclerView-u */
            for (ReservationItemDetails m: items) {
                item.add(m);
                if(m.getStatus()==1 || m.getStatus()==2) {
                    item1.add(m);
                }
            }

            /* Prikazivanje rezervacija u RecyclerView-u */
            recyclerView = (RecyclerView) findViewById(R.id.my_reservations);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new UserReservationsRecycleAdapter(item));

            progress.dismiss();
        }

        /* Spajanje varijable sa FloatingActionButton-om te kreiranje listenera na njegov klik */
        floatingActionButton=(FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Provjera da li postoje rezrvacije za otkazivanje ili ne postoje i prema tome
                * prikaz odgovorajućeg alerta */
                if(item1.size() > 0) {
                    showDialog();
                } else {
                    showNoReservationDialog();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.legend,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Metoda koja prikazuje alert dialog kada nema rezrvacija za otkazivanje */
    private void showNoReservationDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(UserReservationsActivity.this);
        dialog.setTitle(R.string.Alert_cancel_title);
        dialog.setMessage(R.string.EmptyListReservationForCancel);
        dialog.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog builder = dialog.create();
        builder.show();
    }

    /* Metoda koja prikazuje alert dialog kada ima rezrvacija za otkazivanje */
    private void showDialog(){
        /* Elementi sa layout-a se povezuju sa odgovarajučim varijablama */
        final View view = getLayoutInflater().inflate(R.layout.cancel_of_reservation, null);
        final EditText editText=(EditText)view.findViewById(R.id.reason_for_cancel_of_reservation);
        final RadioGroup rg = new RadioGroup(this);

        /* Za svaku rezervaiju koja se može otkazati prikazuje se jedan Radio Button */
        for(int i=0; i < 1 ; i++){
            for (ReservationItemDetails item: item1) {
                RadioButton rb = new RadioButton(this);
                rb.setId(Integer.parseInt(item.getId()));
                rb.setText("Rezervacija " + item.getId().toString());
                rg.addView(rb);
            }

            ((ViewGroup) view.findViewById(R.id.RadioGroup)).addView(rg);
        }

        /* Prikazivanje AlertDialoga sa vlastitim layout-om */
        final AlertDialog.Builder dialog = new AlertDialog.Builder(UserReservationsActivity.this);
        dialog.setTitle(R.string.Alert_cancel_title);
        dialog.setView(view);
        dialog.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                flag2 = false;
                sendDataForCancelReservation(rg.getCheckedRadioButtonId(), editText.getText().toString());

            }
        })
                .setNegativeButton(R.string.Alert_cancel_button_for_reservation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        flag2 = false;
                        dialog.dismiss();
                    }
                });

        final AlertDialog builder = dialog.create();
        builder.show();

        /* Isključivanje positive buttona */
        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        /* Listener koji čeka promjene u EditTextu te prema potrebi uključuje ili isključuje potive button */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else if(s.length() > 300){
                    ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    if(!flag2){
                        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else{
                        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }
                }
            }
        });

        /* Listener koji čeka promjene u RadioGroup-i te prema potrebi uključuje ili isključuje potive button */
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radioGroup.getCheckedRadioButtonId() == -1){
                    ((AlertDialog) builder).getButton(
                            AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    flag2 = false;
                } else {
                    if(editText.getText().toString().isEmpty()){
                        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else if(editText.getText().toString().length() > 300) {
                        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    } else {
                        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                    }

                    flag2 = true;
                }
            }
        });
    }

    /* Metoda koja poziva skriptu koja radi otkazivanje rezervacije */
    private void sendDataForCancelReservation(int reservation, String description){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progressOfCancelReservation = ProgressDialog.show(this, getString(R.string.CancelOfReservationInProcess), getString(R.string.PleaseWait));
            flag = true;

            /* Brisanje podataka kako bi se novi mogli prikazati */
            adapter.clearData();

            DataLoader dataLoader;
            dataLoader = new WsReservationCancelDataLoader();
            dataLoader.loadReservationCancel(this, reservation, description);
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    /* Metoda koja poziva skriptu koja vraća sve rezervacije određenog korisnika */
    private void getAllReservation(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.GetAllReservationsInProcess), getString(R.string.PleaseWait));

            /* Brisanje podataka kako bi se novi mogli prikazati */
            adapterForAllReservatons.clearData();

            DataLoader dataLoader1;
            dataLoader1 = new WsReservationsDataLoader();
            dataLoader1.loadDataMyReservations(this, user);
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

}
