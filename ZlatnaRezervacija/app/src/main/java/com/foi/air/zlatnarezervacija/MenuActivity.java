package com.foi.air.zlatnarezervacija;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.foi.map.MapFragmentActivity;

public class MenuActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    private String user_intent, name_intent, notifications_intent, email_intent, role_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        role_intent = getIntent().getStringExtra("role_id");
        user_intent = getIntent().getStringExtra("user_id");
        name_intent = getIntent().getStringExtra("name");
        email_intent = getIntent().getStringExtra("email");
        notifications_intent = getIntent().getStringExtra("notifications");

        setContentView(R.layout.activity_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_menu);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.user_menu);
        navigationView.setNavigationItemSelectedListener(navigationOptionSelected);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View menuHeader = navigationView.getHeaderView(0);
        TextView name = (TextView) menuHeader.findViewById(R.id.user_name);
        TextView email = (TextView) menuHeader.findViewById(R.id.user_email);
        name.setText(name_intent);
        email.setText(email_intent);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("opcija", notifications_intent);
        editor.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String tip = sharedPreferences.getString("obavijest", "");

        if(tip!=notifications_intent){
            notifications_intent=tip;
        }
    }

    NavigationView.OnNavigationItemSelectedListener navigationOptionSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menu_option1) {

                Intent intent = new Intent(getApplicationContext(), MenuCategoryActivity.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.menu_option2) {

                Intent intent = new Intent(getApplicationContext(), CreateReservationActivity.class);
                intent.putExtra("user_id", user_intent);
                intent.putExtra("user_name", name_intent);
                intent.putExtra("notifications", notifications_intent);
                startActivity(intent);

            } else if (item.getItemId() == R.id.menu_option3) {
                Intent intent = new Intent(getApplicationContext(), UserReservationsActivity.class);
                intent.putExtra("user_id", user_intent);
                startActivity(intent);

            } else if (item.getItemId() == R.id.menu_option4) {

                startMap();

            } else if (item.getItemId() == R.id.menu_option5) {
                Intent intent = new Intent(getApplicationContext(), RestaurantDetailsActivity.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.menu_option6) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setCancelable(false);
                builder.setTitle(R.string.logout_alert_title);
                builder.setMessage(R.string.logout_alert_message);
                builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getBaseContext(), R.string.LogoutToastText, Toast.LENGTH_LONG).show();
                        finish();
                    }
                })
                        .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.create().show();

            } else if (item.getItemId() == R.id.menu_option7){
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                intent.putExtra("user_id", user_intent);
                intent.putExtra("notifications", notifications_intent);
                startActivity(intent);
            }

            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
            builder.setCancelable(false);
            builder.setTitle(R.string.logout_alert_title);
            builder.setMessage(R.string.logout_alert_message);
            builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    Toast.makeText(getBaseContext(), R.string.LogoutToastText, Toast.LENGTH_LONG).show();
                    finish();
                }
            })
                    .setNegativeButton(R.string.Alert_cancel_button, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
        }
    }
    public void startMap(){

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo() != null){
            Intent intent = new Intent(MenuActivity.this, MapFragmentActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_SHORT).show();
        }
    }
}
