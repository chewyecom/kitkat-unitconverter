package com.chewy.unitconverter;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ConverterChoicesActivity extends ListActivity {
	
	public static final String MSG_CONVERT = "messageConvert";
	public static final String MSG_REVERSE = "messageReverse";
	public static final String SELECTED_ITEM = "selectedItem";
	private ListAdapter mStaticAdapter;

//	int selectedMenuItem;
	int selectedItemInt;
	TextView selectedItem;
	String[] items, reverseItems;
	TextView displaySelection;
	String messageConvert, messageReverse; // for clarity, create two string for each type of message
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lo_converter_choices);

		Resources res = this.getResources();
		items = new String[] {
				res.getString(R.string.item_inch_cm),
				res.getString(R.string.item_fahrenheit_celsius),
				res.getString(R.string.item_km_mi),
				res.getString(R.string.item_mtr_ft)
				};
		reverseItems = new String[] {
				res.getString(R.string.item_cm_inch),
				res.getString(R.string.item_celsius_fahrenheit),
				res.getString(R.string.item_mi_km),
				res.getString(R.string.item_ft_mtr)
				};


		mStaticAdapter = new ArrayAdapter<String>(
				this,  //getActivity(),
				R.layout.lo_converter_choices, 
				R.id.item,
				items);
		setListAdapter(mStaticAdapter);
		
		selectedItem=(TextView)findViewById(R.id.item);
		displaySelection = (TextView)findViewById(R.id.display_selection);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		messageConvert = " position:" + position + "  " + items[position];
		messageReverse = " position:" + position + "  " + reverseItems[position];
		this.displaySelection.setText(messageConvert);
		selectedItemInt = position;
		
		this.loadConverter(v);
	}
		
	private void loadConverter(View view) {
		Intent converterIntent = new Intent(this, ConverterActivity.class);
		
		 Bundle bundle = new Bundle();
		 bundle.putString (MSG_CONVERT, messageConvert);
		 bundle.putString (MSG_REVERSE, messageReverse);
		 bundle.putInt(SELECTED_ITEM, selectedItemInt);
		 converterIntent.putExtras(bundle);
		
		this.startActivity(converterIntent);
	}
	
	
	// assume there is no option menu
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.unit_converter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		selectedMenuItem = item.getItemId();
		if (selectedMenuItem == R.id.action_linear) {
			setContentView(R.layout.lo_unit_converter);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
*/
	
}
