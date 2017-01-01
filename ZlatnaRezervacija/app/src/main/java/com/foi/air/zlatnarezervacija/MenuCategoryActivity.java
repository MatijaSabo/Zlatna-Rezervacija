package com.foi.air.zlatnarezervacija;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.foi.air.zlatnarezervacija.adapters.MenuCategoryRecycleAdapter;

public class MenuCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String[] kategorije = {"Hladna predjela", "Juhe", "Tjestenine i rižota",
            "Mesna jela", "Specijaliteti Zlatnih gorica", "Jela od riba",
            "Vegetarijanska jela", "Prilozi", "Salate",
            "Slatka jela", "Kava i topli napici", "Bezalkoholna pića",
            "Piva", "Žestoka alkoholna pića", "Miješana vina"};

    private Integer[] slike = {R.mipmap.restaurant_menu, R.mipmap.restaurant_menu, R.mipmap.restaurant_menu,
            R.mipmap.restaurant_menu, R.mipmap.restaurant_menu, R.mipmap.restaurant_menu,
            R.mipmap.restaurant_menu, R.mipmap.restaurant_menu, R.mipmap.restaurant_menu,
            R.mipmap.restaurant_menu, R.mipmap.drink_black, R.mipmap.drink_black,
            R.mipmap.drink_black, R.mipmap.drink_black, R.mipmap.drink_black};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_category);

        recyclerView = (RecyclerView) findViewById(R.id.menu_category_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MenuCategoryRecycleAdapter(kategorije, slike, this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
