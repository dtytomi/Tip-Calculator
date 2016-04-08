package com.example.tipcalculator;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

public class TipCalculator extends AppCompatActivity {

	private static  final String BILL_TOTAL = "BILL_TOTAL";
	private  static  final  String CUSTOM_PERCENT = "CUSTOM_PERCENT";

	private  double currentBillTotal; // bill  amount  entered by the user
	private  int currentCustomPercent; // tip % set with the SeekBar
	private  EditText tenTotalEdit;
	private  EditText tenEditText;
	private  EditText billEditText;
	private  EditText fifteenEditText;
	private  EditText fifteenTotalEdit;
	private  EditText twentyEditText;
	private  EditText twentyTotalEdit;

	private  TextView customTipTextView;
	private  EditText tipCustomEditText;
	private  EditText totalCustomEditText;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		//check if app just started or is being  restored from memory
		if (savedInstanceState == null ) // app just started running
		{
			currentBillTotal = 0.0; // initilize the bill amount
			currentCustomPercent = 18; //initialize custom to 18
		}
		else {
			currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL);

			currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
		}

		tenEditText = (EditText) findViewById(R.id.tenEditText);
		fifteenEditText = (EditText) findViewById(R.id.fifteenEditText);
		twentyEditText = (EditText) findViewById(R.id.twentyEditText);

		tenTotalEdit = (EditText) findViewById(R.id.tenTotalEdit);
		fifteenTotalEdit = (EditText) findViewById(R.id.fifteenTotalEdit);
		twentyTotalEdit = (EditText) findViewById(R.id.twentyTotalEdit);

		customTipTextView = (TextView) findViewById(R.id.customTipTextView);

		tipCustomEditText = (EditText) findViewById(R.id.tipCustomEditText);
		totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);

		billEditText = (EditText) findViewById(R.id.billEditText);
		billEditText.addTextChangedListener(billEditTextWatcher);

		//get the seekBar used  to set the custom tip custom
		SeekBar customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
		customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);

	}

	public void updateStandard() {

		//calculate bill total with a ten percent tip
		double tenPercentTip = currentBillTotal * .1;
		double tenPercentTotal = currentBillTotal + tenPercentTip;

		//set tipTenEditText's text to tenPercentTip
		tenEditText.setText(String.format("%.02f", tenPercentTip));
		//set totalTenEditText's text to tenPercentTotal
		tenTotalEdit.setText(String.format("%.02f",tenPercentTotal));

		//calculate bill total with a fifteen percent tip
		double fifteenPercentTip = currentBillTotal * .15;
		double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;

		//set tipTenEditText's text to fifteenPercentTip
		fifteenEditText.setText(String.format("%.02f", fifteenPercentTip));
		//set totalTenEditText's text to fifteenPercentTotal
		fifteenTotalEdit.setText(String.format("%.02f",fifteenPercentTotal));

		//calculate bill total with a tweenty percent tip
		double tweentyPercentTip = currentBillTotal * .20;
		double tweentyPercentTotal = currentBillTotal + tweentyPercentTip;

		//set tipTenEditText's text to tweentyPercentTip
		fifteenEditText.setText(String.format("%.02f", tweentyPercentTip));
		//set totalTenEditText's text to tweentyPercentTotal
		fifteenTotalEdit.setText(String.format("%.02f", tweentyPercentTotal));
	}

	private  void updateCustom() {

		// set customTipTextView's text to match the position of  the SeekBar
		customTipTextView.setText(currentCustomPercent + "%");

		// calculate  the custom tip amount
		double customTipAmount =
				currentBillTotal * currentCustomPercent * .01;

		// calculate the total bill, including the custom tip
		double customTotalAmount = currentBillTotal + customTipAmount;

		// display the tip and total bill amounts
		tipCustomEditText.setText(String.format("%.02f", customTipAmount));
		totalCustomEditText.setText(
				String.format("%.02f", customTotalAmount));

	} // end method updateCustom


	@Override
	protected void onSaveInstanceState( Bundle outState){
		super.onSaveInstanceState(outState);

		outState.putDouble(BILL_TOTAL, currentBillTotal);
		outState.putInt( CUSTOM_PERCENT, currentCustomPercent);
	} // end method onSaveInstanceState

	private OnSeekBarChangeListener customSeekBarListener =
			new OnSeekBarChangeListener() {
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

					// sets currentCustomPercent, then call updateCustom
					currentCustomPercent = seekBar.getProgress();
					updateCustom();
				}

				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {

				}

				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {

				}
			};

	private  TextWatcher billEditTextWatcher = new TextWatcher() {

		// call when user enters a number
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			// convert billEditText's text to a double
			try {
				currentBillTotal = Double.parseDouble(s.toString());
			} // try end
			catch (NumberFormatException e) {

				currentBillTotal = 0.0; // default if an exception occurs
			} // end catch

			updateStandard();
			updateCustom();

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void afterTextChanged(Editable s) {

		}
	};

	@Nullable
	@Override
	public View findViewById(int id) {
		return super.findViewById(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tip_calculator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
