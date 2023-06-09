package com.carsonskjerdal.app.groceryshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    ProductsExpandableAdapter mAdapter;
    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = DatabaseManager.getInstance(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("resultName");

        //proper list temporarily generated until a database is built
        final List<Products> products = generateProductList(name);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new ProductsExpandableAdapter(this, products);

        recyclerView.setAdapter(mAdapter);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(llm);
    }


    private List<Products> generateProductList(String name) {

        List<Products> list = new ArrayList<>();
        Products products;
        ProductsChild productsChild;

        //opens a cursor containing all the data from our database Table

        //loop through putting the cursor data into object which are then put into a list
        try (Cursor cursor = dbManager.queryAllItems("products")) {
            while (cursor.moveToNext()) {
                //if the group matches the grocery name then add this to current list
                String data3 = cursor.getString(3);
                if (data3.equals(name)) {

                    //pull name, image, and price
                    String data = cursor.getString(1);
                    String data2 = cursor.getString(2);
                    Log.e("Cursor", " " + data2);
                    String price = cursor.getString(4);

                    //add child details, then combine to make a product item, finally add to list
                    productsChild = new ProductsChild(price);
                    products = new Products(data, data2, Collections.singletonList(productsChild));

                    list.add(products);
                }
            }
            //close the cursor after use.
            cursor.close();
        }

        return list;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_complete:
                // complete workout
                Integer int1 = 0;
                //Adds new workout to Adapter
                Intent myIntent = new Intent(ProductsActivity.this, CartActivity.class);
                startActivityForResult(myIntent, int1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
