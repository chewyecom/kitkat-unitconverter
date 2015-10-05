package com.chewy.unitconverter;

public class Converter {

	public Converter() {
		// TODO Auto-generated constructor stub
	}

	public static float convertInchToCm(float inch){
		float cm = Float.valueOf( inch*2.54f );
		return cm;
	}
	public static float convertCmToInch(float cm){
		float inch = Float.valueOf( cm*0.393701f );
		return inch;
	}
	public static float convertFehrenheitToCelsius(float fehrenheit){
		float cel = Float.valueOf( (fehrenheit-32f)*(5f/9f) );
		return cel;
	}
	public static float convertCelsiusToFehrenheit(float cel){
		float fehrenheit = Float.valueOf( (cel*(9f/5f))+32f );
		return fehrenheit;
	}
	public static float convertKilometerToMile(float km) {
		float mile = Float.valueOf( km*0.621371f );
		return mile;
	}
	public static float convertMileToKilometer(float mile) {
		float km = Float.valueOf( mile*1.60934f );
		return km;
	}
	public static float convertMeterToFeet(float meter) {
		float feet = Float.valueOf( meter*3.28084f );
		return feet;
	}
	public static float convertFeetToMeter(float feet) {
		float meter = Float.valueOf( feet*0.3048f );
		return meter;
	}

}
