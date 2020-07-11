package com.example.pizzahouse;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.pizzahouse.R;

public class OrderSummary extends AppCompatActivity {

    TextView summaryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordersummary);
        summaryData = findViewById(R.id.summaryData);
        summaryData.setText(Html.fromHtml("<u>Order Revise</u><br/><br/>"));
        if(getIntent() != null){
            summaryData.append(getIntent().getStringExtra("orderSummary"));
        }else{
            summaryData.setText("No Orders !!");
        }
        summaryData.append(Html.fromHtml("<br/>"));
        summaryData.setVisibility(View.VISIBLE);
    }
}