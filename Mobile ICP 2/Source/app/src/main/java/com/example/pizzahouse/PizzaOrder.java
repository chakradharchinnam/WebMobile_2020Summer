package com.example.pizzahouse;

import android.os.Bundle;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;

import org.apache.commons.lang3.BooleanUtils;


public class PizzaOrder extends AppCompatActivity{

    private static final Integer base= 10;
    private static final Integer chickenprice = 3;
    private static final Integer sausageprice = 2;
    private static final Integer pepperoniprice = 2;
    private static final Integer opprice = 1;
    float totalPrice;
    Integer quantity = 1;
    String orderSummary;
    EditText peru;
    TextView sankya;
    CheckBox kodi, geedha, pandi, olivesOnions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_order);
        sankya = findViewById(R.id.sankaychupu);
        peru = findViewById(R.id.naperu);
        kodi = findViewById(R.id.kodibox);
        geedha= findViewById(R.id.sasubox);
        pandi = findViewById(R.id.pandibox);
        olivesOnions = findViewById(R.id.akulabox);
    }

    private boolean isUserEmpty(){
        // Checking If username is present or not
        String userName = peru.getText().toString();
        if(userName == null || userName.isEmpty()){
            Context context = getApplicationContext();
            String upperLimitToast = "User Name is Required";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return true;
        }
        return false;
    }



    private String fetchDetails() {
        boolean chickenFlag = kodi.isChecked();
        boolean baconFlag = geedha.isChecked();
        boolean pepperoniFlag = pandi.isChecked();
        boolean opFlag = olivesOnions.isChecked();


        totalPrice = calculatePrice(chickenFlag, baconFlag, pepperoniFlag, opFlag, quantity);

        return fetchOrderSummary(peru.getText().toString(), chickenFlag, baconFlag, pepperoniFlag, opFlag, totalPrice);
    }


    public void orderSummary(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent intent = new Intent(PizzaOrder.this, OrderSummary.class);
            intent.putExtra("orderSummary", orderSummary);
            startActivity(intent);
        }
    }

    // OnClick of Order
    public void orderPizzaMain(View view) {
        if (!isUserEmpty()) {
            orderSummary = fetchDetails();
            Intent emailIntent = new Intent(Intent.ACTION_SEND);

            emailIntent.setType("plain/text");

            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"vickyspizza@gmail.com"});

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, " Vicky's Pizza House Order Summary");

            emailIntent.putExtra(Intent.EXTRA_TEXT, orderSummary);

            startActivity(Intent.createChooser(emailIntent, ""));
        }
    }



    private String fetchOrderSummary(String userName, boolean chickenFlag, boolean baconFlag,
                                     boolean pepperoniFlag, boolean opFlag, float totalPrice) {
        return getString(R.string.summary_name,userName) +"\n"+
                getString(R.string.summary_chicken, BooleanUtils.toStringYesNo(chickenFlag))+"\n"+
                getString(R.string.summary_bacon,BooleanUtils.toStringYesNo(baconFlag)) +"\n"+
                getString(R.string.summary_pepperoni,BooleanUtils.toStringYesNo(pepperoniFlag)) +"\n"+
                getString(R.string.summary_op,BooleanUtils.toStringYesNo(opFlag)) +"\n"+
                getString(R.string.summary_quantity,quantity)+"\n"+
                getString(R.string.summary_total_price,totalPrice) +"\n"+
                getString(R.string.thank_you);
    }


    private float calculatePrice(boolean chicken, boolean bacon, boolean pepperoni, boolean op, Integer quantity) {
        int basePrice = base;
        if (chicken) {
            basePrice += chickenprice;
        }
        if (bacon) {
            basePrice += sausageprice;
        }
        if (pepperoni){
            basePrice += pepperoniprice;
        }
        if(op){
            basePrice += opprice;
        }
        return quantity * base;
    }

    public void more(View view) {
        if (quantity < 20) {
            quantity = quantity + 1;
            display(quantity);
        } else {
            Log.i("PizzaOrder", "Select less than 20 Pizzas");
            Context context = getApplicationContext();
            String lowerLimitToast = " select less 20 Pizzas";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, lowerLimitToast, duration);
            toast.show();
            return;
        }
    }

    public void less(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            display(quantity);
        } else {
            Log.i("PizzaOrder", "Please select atleast one Pizza");
            Context context = getApplicationContext();
            String upperLimitToast = "Please select atleast 1 Pizza";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, upperLimitToast, duration);
            toast.show();
            return;
        }
    }

    private void display(int number) {
        sankya.setText("" + number);
    }

}