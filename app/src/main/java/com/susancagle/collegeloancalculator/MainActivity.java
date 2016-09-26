package com.susancagle.collegeloancalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
    private double billAmount = 0.0; // bill amount entered by the user
    private double percent = 0.05; // initial tip percentage
    private double term = 5.0; // initial length of the loan
    private TextView amountTextView; // shows formatted bill amount
    private TextView percentTextView; // shows tip percentage
    private TextView tipTextView; // shows calculated tip amount
    private TextView totalTextView; // shows calculated total bill amount
    private TextView monthlyPaymentTextView; // shows monthly payment

//called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        //get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        monthlyPaymentTextView = (TextView) findViewById(R.id.monthlyPaymentTextView);
        tipTextView.setText(currencyFormat.format(0)); //set text to 0
        totalTextView.setText(currencyFormat.format(0)); //set total text to 0 as well
        monthlyPaymentTextView.setText(currencyFormat.format(0)); // set monthly payment to 0

        //set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        //set percentSeekBar's OnSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarListener);

    }

    //calculate and display tip and total amounts
    private void calculate() {
        //format percent and display in percent TextView
        percentTextView.setText(percentFormat.format(percent));

        //calculate the tip and total

        double tip = billAmount* percent;
        double total = billAmount + tip;
        double monthly = (total / term) / 12;


        //display tip and total and monthly formatted as currency
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
        monthlyPaymentTextView.setText(currencyFormat.format(monthly));

    }



    // listener object for the SeekBar's progress changed events
    private final OnSeekBarChangeListener seekBarListener = new OnSeekBarChangeListener() {
                // update percent, then call calculate
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    percent = progress / 100.0; // set percent based on progress
                    calculate(); // calculate and display tip and total and monthly
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) { }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) { }
            };

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher(){

        //called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                // get bill amount and display currency formatted value
                billAmount = Double.parseDouble(s.toString()) /100.0;
                amountTextView.setText(currencyFormat.format(billAmount));
            }
            catch (NumberFormatException e) {
                //if it is empty or non-numeric
                amountTextView.setText("");
                billAmount = 0.0;
            }
            calculate(); //update the tip and total TextViews
        }

        @Override
        public void afterTextChanged(Editable s){

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after){

        }
    };


}
