package com.foi.air.zlatnarezervacija;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.foi.webservice.data_loaders.DataLoadedListener;
import com.foi.webservice.data_loaders.DataLoader;

import com.foi.webservice.data_loaders.WsNotificationLoader;
import com.foi.webservice.data_loaders.WsReplyToReservation;
import com.foi.webservice.data_loaders.WsReservationOnHold;
import com.foi.webservice.responses.FreeTables;
import com.foi.webservice.responses.WebServiceResponseNotification;

import com.foi.webservice.responses.WebServiceResponseReservationOnHold;
import com.foi.webservice.responses.WebServiceResponseSettings;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReservationOnHoldActivity extends AppCompatActivity implements DataLoadedListener {
    WebServiceResponseNotification WSresult;
    private String reservation_intent;
    LinearLayout linearLayout;
    CheckBox checkBox;
    ProgressDialog progress;

    private Calendar calendar;
    private Integer system_minute, sysetem_hour;

    EditText vrijeme_input;
    TextInputLayout vrijeme_label;
    TextView user, date, time, persons, meals, remark;
    Button potvrdi, odbij;
    String user_id;

    Boolean flag;
    Boolean flag2 = false;
    Boolean flag3 = false;
    WebServiceResponseReservationOnHold data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_on_hold);

        /* Prihvačanje podataka koji se šalju preko intenta */
        reservation_intent = getIntent().getStringExtra("reservation_id");

        /* Prikazivanje back buttona i promjena teksta u toolbaru*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.AdminReservationTitle) + reservation_intent);

        /* Spajanje elementa sa layout-a sa odgovarajučim varijablama */
        linearLayout = (LinearLayout) findViewById(R.id.checkbox_group);
        user = (TextView) findViewById(R.id.reservation_user);
        date = (TextView) findViewById(R.id.reservation_date);
        time = (TextView) findViewById(R.id.reservation_time);
        persons = (TextView) findViewById(R.id.reservation_persons);
        meals = (TextView) findViewById(R.id.reservation_meals);
        remark = (TextView) findViewById(R.id.reservation_remark);

        vrijeme_input = (EditText) findViewById(R.id.input_time_reservation_on_hold);
        vrijeme_label = (TextInputLayout) findViewById(R.id.textinputlayout_time);

        /* Isključivanje buttona kako se oni nebi mogli kliknuti prije nego što se dohvate svi podaci */
        potvrdi = (Button) findViewById(R.id.btn_potvrdi_rezervaciju);
        potvrdi.setEnabled(false);
        potvrdi.setBackgroundColor(Color.parseColor("#808080"));

        odbij = (Button) findViewById(R.id.btn_odbij_rezervaciju);
        odbij.setEnabled(false);
        odbij.setBackgroundColor(Color.parseColor("#808080"));

        /* Dobivanje trenutnog vremena u odgovarajuće varijable */
        calendar = Calendar.getInstance();
        sysetem_hour = calendar.get(Calendar.HOUR_OF_DAY);
        system_minute = calendar.get(Calendar.MINUTE);

        ButterKnife.bind(this);

        loadReservation();
    }

    /* Metoda koja poziva skriptu WebServisa koja daje detalje odgovorajuće rezervacije */
    private void loadReservation() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsReservationOnHold();
            dataLoader1.loadReservationOnHold(this, reservation_intent.toString());
        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    /* Metoda koja prihvača odgovor WebServisa */
    @Override
    public void onDataLoaded(Object result) {

        /* Provjera koja je metoda dala upit na WebServis */
        if(flag2){
            /* Ukoliko je pozvana skripta koja radi odgovor na rezervaciju */

            String succesNotifyUser = data.getUser_first_name() + getString(R.string.NotificationMessageFirstpart) + reservation_intent + getString(R.string.NotificationMessageSecondPartSuccess);
            String unsuccesNotifyUser = data.getUser_first_name() + getString(R.string.NotificationMessageFirstpart) + reservation_intent + getString(R.string.NotificationMessageSecondPartCancel);
                    flag2 = false;
            WebServiceResponseSettings data = (WebServiceResponseSettings) result;

            progress.dismiss();

            /* Provjera dobivenih podataka sa WebServisa */
            if(data.getStatus().contains("2") || data.getStatus().contains("4")){
                Toast.makeText(this, getString(R.string.RequestForCancelDatabaseFail), Toast.LENGTH_LONG).show();
            } else if(data.getStatus().contains("1")) {
                notifyUser(user_id, succesNotifyUser);
            } else{
                notifyUser(user_id, unsuccesNotifyUser);
            }

        } else if(flag3){
            /* Ukoliko je pozvana skripta koja radi obavještavanje korisnika preko push notifiakcija */

            WSresult = (WebServiceResponseNotification) result;
            flag3 = false;

            /* Provjera dobivenih podataka sa WebServisa */
            if(WSresult.getSuccess().contains("1")){
                Toast.makeText(this, getString(R.string.NotificationSuccessMessage), Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, getString(R.string.NotificationFailMessage), Toast.LENGTH_SHORT).show();
            }

            progress.dismiss();

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("back", "1");
            editor.commit();

            finish();

        } else {
            /* Ukoliko je pozvana skripta koja daje detalje odgovarajuće rezervacije */
            data = (WebServiceResponseReservationOnHold) result;

            /* Spajanje dobivenih podataka sa odgovrajučim elementima na layout-u */
            user.setText(data.getUser_first_name() + " " + data.getUser_last_name());
            date.setText(data.getDate());
            time.setText(data.getTime_arrival());
            persons.setText(data.getPersons());
            meals.setText(data.getMeals());
            remark.setText(data.getRemark());
            user_id = data.getUser_id();

            /* Uključivanje buttona za odbijanje rezervacije */
            odbij.setEnabled(true);
            odbij.setBackgroundColor(getResources().getColor(R.color.btnColor));

            String label;
            flag = false;

            /* Za svaki slobodan stol radi se jedan checkbox kako bi korisnik mogao odabrati pojedini stol */
            for (FreeTables t : data.getTables()){

                /* Provjera da li je stol slobodan u odgovarajuće vrijeme */
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

            /* Ukoliko ne postoji niti jedan slobodni stol za rezervaciju korisniku se prikazuje poruka o tome,
            * a ukoliko ima slobodnih stolova uključuje se button za potvrđivanje rezervacije */
            if(!flag){
                TextView text = new TextView(this);
                text.setText(R.string.NoFreeTables);
                linearLayout.addView(text);
            } else{
                potvrdi.setEnabled(true);
                potvrdi.setBackgroundColor(getResources().getColor(R.color.btnColor));
            }

            progress.dismiss();
        }
    }

    /* Metoda koja poziva skriptu WebServisa koja radi obavještavanje korisnika preko push notifikacije */
    private void notifyUser(String user_id, String message) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.SendingNotificationMessage), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsNotificationLoader();

            flag3 = true;
            dataLoader1.loadNotification(this, user_id, message);

        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.btn_odbij_rezervaciju)
    public void RefuseResrvation(){
        /* Spajanje elemenata sa layout-a sa odgovarajučim varijablama */
        View alert_view = getLayoutInflater().inflate(R.layout.refusal_reservation_alert, null);
        final EditText alert_edit_text = (EditText) alert_view.findViewById(R.id.reason_for_refuse_reservation);

        /* Prikazivanje AlertDialoga sa vlastitim layout-om */
        AlertDialog.Builder refuse_resrvation_alert = new AlertDialog.Builder(ReservationOnHoldActivity.this);
        refuse_resrvation_alert.setTitle(R.string.RefuseReservationAlertTitle);
        refuse_resrvation_alert.setCancelable(false);
        refuse_resrvation_alert.setView(alert_view);
        refuse_resrvation_alert.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                replay_to_reservation(0, alert_edit_text.getText().toString());
                dialogInterface.dismiss();
            }
        }) .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog builder = refuse_resrvation_alert.create();
        builder.show();

        /* Isključivanje OK buttona u AlertDialogu */
        ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);

        /* Listener koji provjerava unos u AlertDialogu te prema potrebi uključuje ili isključuje OK button */
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

    @OnClick(R.id.btn_potvrdi_rezervaciju)
    public void Potvrdi_reservaciju(){
        CheckBox ch;
        Integer brojac = 0;
        String list = "";

        /* Provjera CheckBox-ova jesu li potvrđteni ili nisu, te se prema potrebi ID CheckBox-a dodaje u string koji
        * predstavlja listu potvrđenih ChackBox-ova */
        for (FreeTables table : data.getTables()) {
            ch = null;

            /* Provjeravaju se samo stolovi koji su slobodni u odgovarajuće vrijeme */
            if(!table.getFree_from().contains("-") && !table.getFree_to().contains("-")){
                ch = (CheckBox) linearLayout.findViewById(Integer.parseInt(table.getId()));

                if(ch.isChecked()){
                    brojac++;

                    if(list.length() == 0){
                        list = list + table.getId();
                    } else{
                        list = list + "%" + table.getId();
                    }
                }
            }
        }

        /* Provjera liste potvrđenih CheckBox-ova i unesenog vremena kraja rezervacije */
        if(list.length() == 0){
            Toast.makeText(this, R.string.InsertTableError, Toast.LENGTH_LONG).show();

            if(vrijeme_input.getText().length() < 1){
                vrijeme_label.setErrorEnabled(true);
                vrijeme_label.setError(getString(R.string.InsertEndTimeError));
            }
        } else if(vrijeme_input.getText().length() < 1) {
            vrijeme_label.setErrorEnabled(true);
            vrijeme_label.setError(getString(R.string.InsertEndTimeError));
        } else {
            replay_to_reservation(1, list);
        }
    }

    /* Poziva se skripta WebServisa koja radi odgovor na određenu rezervaciju */
    private void replay_to_reservation(int i, String text) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);

        /* Provjera internet konekcije */
        if (cm.getActiveNetworkInfo() != null) {
            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));
            DataLoader dataLoader1;
            dataLoader1 = new WsReplyToReservation();

            flag2 = true;

            /*Provjera dobivenih parametara kako bi se skripta pozvala na dobar način */
            if(i == 1){
                dataLoader1.loadReplyToResrvationResponse(this, reservation_intent.toString(), String.valueOf(i), vrijeme_input.getText().toString(), text, "-");
            } else {
                dataLoader1.loadReplyToResrvationResponse(this, reservation_intent.toString(), String.valueOf(i), "-", "-", text);
            }

        } else {
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.input_time_reservation_on_hold)
    public void TimePicker(){
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == 999){
            /* Otvaranje TimePickerDialoga sa odgovarajučim vremenom */
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

    /* Prikazivanje odabranih podataka sa TimePicerDialoga u EditText-u */
    private void showTime(int sati, int minute) {
        vrijeme_input.setTextColor(getResources().getColor(R.color.btnColor));
        vrijeme_input.setText(new StringBuilder().append(sati).append(" : ").append(minute).append(" : 00"));

        vrijeme_label.setErrorEnabled(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
