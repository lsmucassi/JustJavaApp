package com.exmplem.android.justjava3;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;




public class MainActivity extends AppCompatActivity {

    /** setting initial value for the number of coffee cups **/
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * increment button
     * increments the number of coffee cups ordered
     **/
    public void increment(View view) {

        /** validating the order **/
        if(quantity == 100){
            Toast.makeText(getBaseContext(), " You cannot have less than 1 cup(s) of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity + 1;
            display(quantity);
    }


    /**
     * decrement
     * decrementing the number of coffees added
     **/
    public void decrement(View view) {

        /** validating the number of coffee cups that can be chosen **/
        if(quantity == 1){

            Toast.makeText(this, " You cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
            quantity = quantity - 1;
            display(quantity);

    }

    /** submit order button to return all the details of the order **/
    public void submitOrder(View view) {

        /** creating a name field variable to save the entered name **/
        EditText nameField = (EditText)findViewById(R.id.name);
        String name = nameField.getText().toString(); //converting to a string

        /** check boxes for choosing choc or cream **/
        CheckBox whippedCream = (CheckBox)findViewById(R.id.whipped_checkBox);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox)findViewById(R.id.chocolate_checkBox);
        boolean hasChoc = chocolate.isChecked();


        /**
         * printing out the value on the android monitor to check if value is correct
         * Log.v("MainActivity,","Has whipped cream: " + hasWhippedCream);
        **/

        int pricePerCup = 10; // price per cup
        int price = calculatePrice(quantity, pricePerCup, hasChoc, hasWhippedCream);//initializing the total price by calling a method
        String mailMessage = createOrder(price, hasWhippedCream, hasChoc, name);

        // sending the order data to an email
        // evoking the mail app
        Intent sendMail = new Intent(Intent.ACTION_SENDTO);
        sendMail.setData(Uri.parse("mailto:")); //only mail app could handle this
        //sendMail.putExtra(Intent.EXTRA_EMAIL, address);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for" + name);// subject of the mail
        sendMail.putExtra(Intent.EXTRA_TEXT, mailMessage);// saves in the text field of the mail app

        // send the data if the activity exists
        if (sendMail.resolveActivity(getPackageManager()) != null){
            startActivity(sendMail); // starts the mail activity
        }
      //  displayMessage(createOrder(price, hasWhippedCream, hasChoc, name));
    }


    // methods to calculate the total price
    // display information

    private int calculatePrice(int quantity, int pricePerCup, boolean atChoc, boolean atWhipped) {

        if ( atWhipped){
            pricePerCup += 3;
        }

        if ( atChoc){
            pricePerCup += 5;
        }

        return quantity * pricePerCup;
    }

    private String createOrder(int price, boolean atWHipped, boolean atChoc, String name)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + atWHipped;
        priceMessage += "\nAdd chocolate? " + atChoc;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: R" + price;
        priceMessage += "\nThank you";

        return priceMessage;
    }
    /**
     * displays the quantity to the screen
     **/
    public void display(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_amnt);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * displays the price value

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(price);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            priceTextView.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(number)));
        } else {

            DecimalFormat precision = new DecimalFormat(" 0.00");
            priceTextView.setText(precision.format(number));
        }

    }
     **/

    /**
     * displays given text
     **/
//   public void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(OrderSummary);
//        orderSummaryTextView.setText(message);
//
//    }
}

