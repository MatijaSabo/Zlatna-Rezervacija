package com.foi.air.zlatnarezervacija;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Matija on 26.1.2017..
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Pozivanje aktivnosti kada je apliakcija učitana */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
