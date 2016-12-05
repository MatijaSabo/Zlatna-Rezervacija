package com.example.matija.zlatnarezervacija;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.matija.zlatnarezervacija.adapters.MenuDetailsRecycleAdapter;
import com.example.webservice.data_loaders.DataLoadedListener;
import com.example.webservice.data_loaders.DataLoader;
import com.example.webservice.responses.MenuItemDetails;
import com.example.webservice.responses.WebServiceMenuResponse;
import com.example.webservice.data_loaders.WsMenuDataLoader;

import java.util.ArrayList;

public class MenuDetailsActivity extends AppCompatActivity implements DataLoadedListener{

    private RecyclerView recyclerView;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);
        String text = getIntent().getStringExtra("kategorija");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(text);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {

            progress = ProgressDialog.show(this, getString(R.string.FetchingData), getString(R.string.PleaseWait));

            DataLoader dataLoader;
            dataLoader = new WsMenuDataLoader();
            dataLoader.loadMenuData(this, text);
        }

        else{
            Toast.makeText(this, R.string.NoInternet, Toast.LENGTH_LONG).show();
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

    @Override
    public void onDataLoaded(Object result) {
        WebServiceMenuResponse DataArrived = (WebServiceMenuResponse) result;

        MenuItemDetails[] items = (MenuItemDetails[]) DataArrived.getItems();
        ArrayList<MenuItemDetails> item = new ArrayList<MenuItemDetails>();

        for (MenuItemDetails m: items) { item.add(m); }

        recyclerView = (RecyclerView) findViewById(R.id.menu_detail_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MenuDetailsRecycleAdapter(item));

        progress.dismiss();
    }
}
