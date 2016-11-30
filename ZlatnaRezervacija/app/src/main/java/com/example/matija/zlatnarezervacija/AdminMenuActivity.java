package com.example.matija.zlatnarezervacija;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.matija.zlatnarezervacija.MenuActivity;
import static android.R.id.toggle;

public class AdminMenuActivity extends AppCompatActivity {

    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String role_intent = getIntent().getStringExtra("role_id");
        String user_intent = getIntent().getStringExtra("user_id");
        String name_intent = getIntent().getStringExtra("name");
        String email_intent = getIntent().getStringExtra("email");

        setContentView(R.layout.activity_menu_admin);
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_menu_admin);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.admin_menu);
        navigationView.setNavigationItemSelectedListener(navigationOptionSelected);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View menuHeader = navigationView.getHeaderView(0);
        TextView name = (TextView) menuHeader.findViewById(R.id.user_name);
        TextView email = (TextView) menuHeader.findViewById(R.id.user_email);
        name.setText(name_intent);
        email.setText(email_intent);
    }

    NavigationView.OnNavigationItemSelectedListener navigationOptionSelected = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menu_admin_option1) {

                Intent intent = new Intent(getApplicationContext(), MenuCategoryActivity.class);
                startActivity(intent);

            } else if (item.getItemId() == R.id.menu_admin_option2) {

            } else if (item.getItemId() == R.id.menu_admin_option3) {

            } else if (item.getItemId() == R.id.menu_admin_option4) {
                MenuActivity activity = new MenuActivity();
                activity.Map();
            } else if (item.getItemId() == R.id.menu_admin_option5) {
                Intent intent = new Intent(getApplicationContext(), RestaurantDetailsActivity.class);
                startActivity(intent);

            } else {

                final AlertDialog.Builder builder = new AlertDialog.Builder(AdminMenuActivity.this);
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
            final AlertDialog.Builder builder = new AlertDialog.Builder(AdminMenuActivity.this);
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
}
