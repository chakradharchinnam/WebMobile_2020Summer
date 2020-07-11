package com.example.pizzahouse;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.pizzahouse.R;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void orderPizza(View view) {
        Intent intent = new Intent(MainActivity.this, PizzaOrder.class);
        startActivity(intent);
    }

}