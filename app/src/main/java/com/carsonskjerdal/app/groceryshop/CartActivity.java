package com.carsonskjerdal.app.groceryshop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Button checkoutButton, shopButton, buttonClear;
    List<CartItems> cartProducts;
    CartItems cartItems;
    RecyclerView recyclerView;
    CartItemAdapter mAdapter;
    TextView textTotal;

    DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        dbManager = DatabaseManager.getInstance(this);

        recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                llm.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        //method to build the CartProducts list
        cartProducts = getCartData();

        mAdapter = new CartItemAdapter(this, cartProducts);
        recyclerView.setAdapter(mAdapter);

        textTotal = findViewById(R.id.textViewTotal);
        String total = buildPrice(cartProducts);
        textTotal.setText(total);

        checkoutButton = findViewById(R.id.button_checkout);
        shopButton = findViewById(R.id.button_shop);
        buttonClear = findViewById(R.id.button_clear);

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartProducts.size() == 0){
                    Toast.makeText(CartActivity.this,"Cart is empty",Toast.LENGTH_SHORT).show();
                } else {
                    Intent myIntent = new Intent(CartActivity.this, CheckoutActivity.class);
                    startActivity(myIntent);
                }
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAll();
            }
        });

        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, GroceryActivity.class);
                startActivity(intent);
            }
        });
    }

    private String buildPrice(List<CartItems> cartProducts) {
        int total = 0;
        CartItems cartItem;
        for (int i = 0; i < cartProducts.size(); i++) {
            cartItem = cartProducts.get(i);
            total += Integer.parseInt(cartItem.getPrice()) * Integer.parseInt(cartItem.getQuantity());
        }

        return Integer.toString(total);
    }

    private List<CartItems> getCartData() {
        List<CartItems> list = new ArrayList<>();
        //cartItems = new CartItems("App1", 0, "19.21", "1");

        //opens a cursor containing all the data from our database Table
        //loop through putting the cursor data into object which are then put into a list
        try (Cursor cursor = dbManager.queryAllItems("cart")) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                Integer itemImage = cursor.getInt(2);
                String price = cursor.getString(3);
                String quantity = cursor.getString(4);

                cartItems = new CartItems(id, name, itemImage, price, quantity);

                list.add(cartItems);
            }
            //close the cursor after use.
            cursor.close();
        }
        return list;
    }

    public void deleteById(int id) {
        dbManager.deleteById("cart", id);
        cartProducts = getCartData();
        mAdapter = new CartItemAdapter(this, cartProducts);
        recyclerView.setAdapter(mAdapter);
        String total = buildPrice(cartProducts);
        textTotal.setText(total);
    }

    public void deleteAll() {
        dbManager.deleteAll("cart");
        cartProducts = getCartData();
        mAdapter = new CartItemAdapter(this, cartProducts);
        recyclerView.setAdapter(mAdapter);
        String total = buildPrice(cartProducts);
        textTotal.setText(total);
    }
}
