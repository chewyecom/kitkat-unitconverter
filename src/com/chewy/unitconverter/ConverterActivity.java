package com.chewy.unitconverter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConverterActivity extends Activity {
	
	private static final int CASE_INCH_CM = 0;
	private static final int CASE_FAH_CEL = 1;
	private static final int CASE_KM_MI = 2;
	private static final int CASE_MTR_FT = 3;
	
	private int selectedItemInt = 0;
	private float conversionResult = 0f;
	
	private TextView tvConvertFrom;
	private TextView tvConvertTo;
	private TextView tvMessage;
	private String messageConvert, messageReverse; 
	private boolean isReverse=false;
	private String[] unitFromArray = new String[] {"INCH", "FAH", "KM", "MTR"};
	private String[] unitToArray = new String[] {"CM", "CEL", "MI", "FT"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lo_convert_me);

		Bundle bundle = this.getIntent().getExtras();
		messageConvert = bundle.getString (ConverterChoicesActivity.MSG_CONVERT);
		messageReverse = bundle.getString(ConverterChoicesActivity.MSG_REVERSE);
		selectedItemInt = bundle.getInt(ConverterChoicesActivity.SELECTED_ITEM);
		
		TextView tvConvertType = (TextView)findViewById(R.id.text_convert_type);
		tvConvertType.setText(messageConvert);
		
		tvConvertFrom = (TextView)findViewById(R.id.edit_convert_from);
		tvConvertFrom.setText("");
		tvConvertTo = (TextView)findViewById(R.id.text_convert_to);
		tvConvertTo.setText("0.00");
		
		tvMessage = (TextView)findViewById(R.id.text_message);
		tvMessage.setText("--");

		//addListenerOnButtons();
		addListenerOnConvertMeButton();
		addListenerOnReverseConversionButton();
		addListenerOnSaveMeButton();
		addListenerOnClearMeButton();		
	}
	
	private void addListenerOnReverseConversionButton() {
        Button reverseButton = (Button) findViewById(R.id.button_reverse);
        reverseButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // Perform action on click
        		TextView tvConvertType = (TextView)findViewById(R.id.text_convert_type);
        		
        		isReverse=!isReverse;
        		if (isReverse) {
            		tvConvertType.setText(messageReverse);
        		} else {
            		tvConvertType.setText(messageConvert);
        		}
        		
        		conversionResult = getConversionResult(tvConvertFrom.getText().toString());
        		tvConvertTo.setText(String.valueOf(conversionResult));
            } // end onclick
        }); // end convertButton

	}
	private void addListenerOnConvertMeButton() {
        Button convertMeButton = (Button) findViewById(R.id.button_convert);
        convertMeButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // Perform action on click
        		conversionResult = getConversionResult(tvConvertFrom.getText().toString());
        		tvConvertTo.setText(String.valueOf(conversionResult));
            } // end onclick
        }); // end convertButton
        		
	}
	private void addListenerOnSaveMeButton() {
		Button saveMeButton = (Button) findViewById(R.id.button_save);
		saveMeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				conversionResult = getConversionResult(tvConvertFrom.getText().toString());
        		tvConvertTo.setText(String.valueOf(conversionResult));
        		
        		ConverterHistory saveHist = new ConverterHistory();
        		if (isReverse) {
	        		saveHist.addInformationIntoDB(v, 
	        				unitToArray[selectedItemInt], 
	        				unitFromArray[selectedItemInt], 
	        				String.valueOf( tvConvertTo.getText()), 
	        				String.valueOf( tvConvertFrom.getText()) );
        		} else {
	        		saveHist.addInformationIntoDB(v, 
	        				unitFromArray[selectedItemInt], 
	        				unitToArray[selectedItemInt], 
	        				String.valueOf( tvConvertFrom.getText()), 
	        				String.valueOf( tvConvertTo.getText()) );
        		}
        		// display the number of records saved in db
        		int recordSaved = saveHist.getNumberOfRecordsFromDB(v);
        		tvMessage.setText("Records saved is " + String.valueOf(recordSaved));

			} // end onClick
		}); // end savebutton
		
	}
	private void addListenerOnClearMeButton() {
		Button clearMeButton = (Button) findViewById(R.id.button_clear);
		clearMeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
        		
        		ConverterHistory clearHist = new ConverterHistory();
        		int recordRemoved = clearHist.removeAllRecordsFromDB(v);

        		tvMessage.setText("Records removed is " + String.valueOf(recordRemoved));

			} // end onClick
		}); // end clearbutton
		
	}
	private float getConversionResult(String convertValue) {
		if (isReverse) {
    		switch (selectedItemInt) {
    		case CASE_INCH_CM:
    			conversionResult = Converter.convertCmToInch(Float.valueOf(convertValue));
    			break;
    		case CASE_FAH_CEL:
    			conversionResult = Converter.convertCelsiusToFehrenheit(Float.valueOf(convertValue));
    			break;
    		case CASE_KM_MI:
    			conversionResult = Converter.convertMileToKilometer(Float.valueOf(convertValue));
    			break;
    		case CASE_MTR_FT:
    			conversionResult = Converter.convertFeetToMeter(Float.valueOf(convertValue));
    			break;
    		} // end switch
    	} else {
    		switch (selectedItemInt) {
    		case CASE_INCH_CM:
    			conversionResult = Converter.convertInchToCm(Float.valueOf(convertValue));
    			break;
    		case CASE_FAH_CEL:
    			conversionResult = Converter.convertFehrenheitToCelsius(Float.valueOf(convertValue));
    			break;
    		case CASE_KM_MI:
    			conversionResult = Converter.convertKilometerToMile(Float.valueOf(convertValue));
    			break;
    		case CASE_MTR_FT:
    			conversionResult = Converter.convertMeterToFeet(Float.valueOf(convertValue));
    			break;
    		} // end switch
		}
		return conversionResult;
	}
	/*
	private void addListenerOnButtons() {
        Button convertMeButton = (Button) findViewById(R.id.button_convert);
        convertMeButton.setOnClickListener(new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                // Perform action on click
        		float result =0f;
        		switch (selectedItemInt) {
        		case CASE_INCH_CM:
        			result = Converter.convertInchToCm(Float.valueOf(tvConvertFrom.getText().toString()));
        			break;
        		case CASE_FAH_CEL:
        			result = Converter.convertFehrenheitToCelsius(Float.valueOf(tvConvertFrom.getText().toString()));
        			break;
        		case CASE_KM_MI:
        			result = Converter.convertKilometerToMile(Float.valueOf(tvConvertFrom.getText().toString()));
        			break;
        		case CASE_MTR_FT:
        			result = Converter.convertMeterToFeet(Float.valueOf(tvConvertFrom.getText().toString()));
        			break;
        		} // end switch
        		tvConvertTo.setText(String.valueOf(result));
            } // end onclick
        }); // end convertButton
        
		Button saveMeButton = (Button) findViewById(R.id.button_save);
		saveMeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
        		float result =0f;
        		String unitFrom="", unitTo="";
        		switch (selectedItemInt) {
        		case CASE_INCH_CM:
        			result = Converter.convertInchToCm(Float.valueOf(tvConvertFrom.getText().toString()));
        			unitFrom = "INCH";
        			unitTo = "CM";
        			break;
        		case CASE_FAH_CEL:
        			result = Converter.convertFehrenheitToCelsius(Float.valueOf(tvConvertFrom.getText().toString()));
        			unitFrom = "FAH";
        			unitTo = "CEL";
        			break;
        		case CASE_KM_MI:
        			result = Converter.convertKilometerToMile(Float.valueOf(tvConvertFrom.getText().toString()));
        			unitFrom = "KM";
        			unitTo = "MI";
        			break;
        		case CASE_MTR_FT:
        			result = Converter.convertMeterToFeet(Float.valueOf(tvConvertFrom.getText().toString()));
        			unitFrom = "Meter";
        			unitTo = "Feet";
        			break;
        		} // end switch
        		tvConvertTo.setText(String.valueOf(result));
        		
        		ConverterHistory saveHist = new ConverterHistory();
        		saveHist.addInformationIntoDB(v, unitFrom, unitTo, 
        				String.valueOf( tvConvertFrom.getText()), 
        				String.valueOf( tvConvertTo.getText()) );

        		// display the number of records saved in db
        		int recordSaved = saveHist.getNumberOfRecordsFromDB(v);
        		tvMessage.setText("Records saved is " + String.valueOf(recordSaved));

			} // end onClick
		}); // end savebutton
	
		Button clearMeButton = (Button) findViewById(R.id.button_clear);
		clearMeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
        		
        		ConverterHistory clearHist = new ConverterHistory();
        		int recordRemoved = clearHist.removeAllRecordsFromDB(v);

        		tvMessage.setText("Records removed is " + String.valueOf(recordRemoved));

			} // end onClick
		}); // end clearbutton
		
	}
	*/

	
}
