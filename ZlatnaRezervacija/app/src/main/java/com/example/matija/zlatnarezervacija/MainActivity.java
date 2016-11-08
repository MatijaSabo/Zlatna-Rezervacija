package com.example.matija.zlatnarezervacija;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_login) Button mainButton;
    @BindView(R.id.input_email) EditText emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void mainButtonClick(View view){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        intent.putExtra("role_id", emailText.getText().toString());
        startActivity(intent);
        finish();
    }
    @OnClick(R.id.link_registration)
    public void Click(View view){
        Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setTitle(R.string.Exit_alert_title);
        builder.setMessage(R.string.Exit_alert_message);
        builder.setPositiveButton(R.string.Alert_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                System.exit(0);
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
