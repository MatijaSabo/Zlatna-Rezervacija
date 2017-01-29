package com.foi.air.zlatnarezervacija;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;
import com.foi.webservice.data_loaders.WsNotificationLoader;
import com.foi.webservice.data_loaders.WsRequestForCancelDetails;
import com.foi.webservice.data_loaders.WsRequestForCancelReply;
import com.foi.webservice.responses.WebServiceRequestForCancelDetails;
import com.foi.webservice.responses.WebServiceReservationCancelResponse;
import com.foi.webservice.responses.WebServiceResponseNotification;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.data;

public class RequestForCancelActivity extends AppCompatActivity implements DataLoadedListener {

    WebServiceResponseNotification WSresult;

    private String reservation_intent;
    ProgressDialog progress;

    TextView user, date, time_arrival, time_checkout, persons, meals, remark, description;
    Button odbij, otkazi;

    Boolean flag = false;
    Boolean flag2 = false;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_cancel);

        reservation_intent = getIntent().getStringExtra("reservation_id");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.AdminReservationTitle) + reservation_intent);

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

        ButterKnife.bind(this);

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
        if(flag){

            flag = false;
            String succesCancelNotifyUser = getString(R.string.RequestForCancelNotificationFirstPart) + reservation_intent + getString(R.string.RequestForCancelNotificationSecondPartSuccess);
            String unsuccesCancelNotifyUser = getString(R.string.RequestForCancelNotificationFirstPart) + reservation_intent + getString(R.string.RequestForCancelNotificationSecondPartCancel);
            WebServiceReservationCancelResponse data = (WebServiceReservationCancelResponse) result;

            if(data.getStatus().contains("0") || data.getStatus().contains("3")){
                Toast.makeText(this, R.string.RequestForCancelDatabaseFail, Toast.LENGTH_LONG).show();
            } else if(data.getStatus().contains("1")) {
                notifyUser(user_id, succesCancelNotifyUser);
            } else{
                notifyUser(user_id, unsuccesCancelNotifyUser);
            }

            progress.dismiss();

        } else if(flag2){

            WSresult = (WebServiceResponseNotification) result;
            flag2 = false;

            if(WSresult.getSuccess().contains("1")){
                Toast.makeText(this, R.string.NotificationSuccessMessage, Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, R.string.NotificationFailMessage, Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("back", "1");
            editor.commit();

            finish();

        } else {
            WebServiceRequestForCancelDetails data = (WebServiceRequestForCancelDetails) result;

            user.setText(data.getUser_first_name() + " " + data.getUser_last_name());
            date.setText(data.getDate());
            time_arrival.setText(data.getTime_arrival());
            time_checkout.setText(data.getTime_checkout());
            persons.setText(data.getPersons());
            meals.setText(data.getMeals());
            remark.setText(data.getRemark());
            description.setText(data.getDescription());
            user_id = data.getUser_id();

            progress.dismiss();
        }
    }

    private void notifyUser(String user_id, String message) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.SendingNotificationMessage), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsNotificationLoader();

            flag2 = true;
            dataLoader1.loadNotification(this, user_id, message);

        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_odbij_zahtjev_za_otkazivanje)
    public void RefuseRequestForCancel(){
        View alert_view = getLayoutInflater().inflate(R.layout.cancel_resrvation_alert, null);
        final EditText alert_edit_text = (EditText) alert_view.findViewById(R.id.reason_for_refuse_request_for_cancel);

        AlertDialog.Builder refuse_request_for_cancel_alert = new AlertDialog.Builder(RequestForCancelActivity.this);
        refuse_request_for_cancel_alert.setTitle(R.string.RefuseRequestForCancelAlertTitle);
        refuse_request_for_cancel_alert.setCancelable(false);
        refuse_request_for_cancel_alert.setView(alert_view);
        refuse_request_for_cancel_alert.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                reply_to_request(alert_edit_text.getText().toString(), 0);
                dialogInterface.dismiss();
            }
        }) .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog builder = refuse_request_for_cancel_alert.create();
        builder.show();

        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        alert_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else if(editable.length() > 300){
                    ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                } else {
                    ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });
    }

    @OnClick(R.id.btn_potvrdi_zahtjev_za_otkazivanje)
    public void Potvrdi_zahtjev(){
        reply_to_request("-", 1);
    }

    private void reply_to_request(String s, int i) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsRequestForCancelReply();

            flag = true;

            if(i == 1){
                dataLoader1.loadReservationCancelResponse(this, reservation_intent.toString(), String.valueOf(i), "-");
            } else{
                dataLoader1.loadReservationCancelResponse(this, reservation_intent.toString(), String.valueOf(i), s);
            }

        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }
}
