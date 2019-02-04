/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view){
//        display(quantity);
//        String priceMessage = "Item count "+quantity;
//        String priceMessage = "Total Item Count "+quantity+" coffee.";
//        String Message = "Thanks";
//        displayMessage(Message);
//        String priceMessage = "Thanks\nPrice $:"+(quantity*10);
//        displayMessage(priceMessage);
//        displayPrice(quantity*10);
//        int price = quantity * 10;
//        String priceMessage = "That would be $"+price+" dollars";
//        String priceMessage = "Amount due $"+price;
//        String drinkOfTheDay = "Green Tea";
//        String storeHours = "Open today from 8am to 5pm";
//        String drinksOrdered = "1 mocha";
//        drinkOfTheDay = drinksOrdered + "1 cappuccina";
//        String priceMessage = "$"+price + " Dollars for "+quantity+" cups of coffee. pay up.";
//        displayMessage(priceMessage);
//        String priceMsg = "Total: $"+price;
//        priceMsg = priceMsg + "\nThank You!";
//        displayMessage(priceMsg);

        EditText editText = (EditText)findViewById(R.id.editname);
        String name = editText.getText().toString();

        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = whippedCream.isChecked();
        Log.v("show", "has whipped cream: "+hasWhippedCream);

        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();
        int price = calculatePrice(hasChocolate, hasWhippedCream);

        String priceMsg = createOrderSummary(price, name, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMsg);
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivity(intent);
        }

        displayMessage(priceMsg);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:47.6, -122.3"));
//        if(intent.resolveActivity(getPackageManager()) != null ){
//            startActivity(intent);
//        }
    }
    private int calculatePrice(boolean addwhippedcream, boolean addchocolate){
        int basePrice = 10;
        if (addchocolate) {
            basePrice += 3;
        }
        if (addwhippedcream) {
            basePrice += 2;

        }
        return basePrice*quantity;
    }
    private String createOrderSummary(int price,String name, boolean cream, boolean chocolates){
        String priceMsg = getString(R.string.order_summary_name, name);
        priceMsg +=  "\n"+getString(R.string.add) +" whipped cream? "+cream+"\n"+getString(R.string.add)+" chocolate? "+chocolates;
        priceMsg += "\nQuantity: "+quantity;
        priceMsg+="\nTotal: $"+price;
        priceMsg = priceMsg + "\n"+getString(R.string.thanks);
        return priceMsg;
    }
    private void display(int number){
//        int number = 0;
        quantity = number;
        TextView quantityTextView = (TextView)findViewById(R.id.quantity_text_view);
        Log.d("show", "display: quantity"+quantityTextView);
        quantityTextView.setText(""+(quantity));
    }
    private void displayPrice(int number){
        TextView priceTextView = (TextView)findViewById(R.id.price_text_view);
        Log.d("show", "display: quantity"+priceTextView);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    private void displayMessage(String msg){
        TextView priceTextView = (TextView)findViewById(R.id.price_text_view);
        priceTextView.setText(msg);
    }

    public void increment(View view) {
        if(quantity >= 100){
            Toast.makeText(this, "you cannot have more than 100 coffee\'s", Toast.LENGTH_SHORT).show();
            quantity = 100;
        }
        display(++quantity);
    }

    public void decrement(View view) {
        if(quantity <= 1) {
            Toast.makeText(this, "you cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            quantity = 1;
        }
        display(--quantity);
    }
}
