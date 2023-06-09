package com.carsonskjerdal.app.groceryshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

public class CheckoutActivity extends AppCompatActivity {

    Button buttonSubmit, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        CardForm cardForm = findViewById(R.id.card_form);
        
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .actionLabel("Purchase")
                .setup(this);

        buttonSubmit = findViewById(R.id.buttonFinish);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()){
                    Intent i = new Intent(CheckoutActivity.this, FinishedActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(CheckoutActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
    }
}
