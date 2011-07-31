/*
 * Sundroid - An open source project for the Android platform, gets top 5 tourist locations to visit on a day based on weather conditions
 * Application written in Java
 * Application uses Google Places API and Google Weather API Powered by GOOGLE
 * 
 * Copyright (C) 2011 Elakkiya Pandian and Shweta Ojha
 * https://github.com/elakkiya/NewSundroid
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *  
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Written by Elakkiya Pandian <elakkiya@gmail.com> and Shweta Ojha <shojha@pdx.edu>
 * 
 * Weather API Reference - Tutorial on Weather API anddev.org
*/

package sundroid.code.weather;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAXHandler capable of extracting information out of the xml-data returned by
 * the Google Weather API.
 */
public class GoogleWeatherHandler extends DefaultHandler {

	// ===========================================================
	// Fields
	// ===========================================================

	private WeatherSet myWeatherSet = null;
	private boolean in_error_information = false;
	private boolean in_current_conditions = false;
	//private boolean in_forecast_conditions = false;

	
	// ===========================================================
	// Constructors
	// ===========================================================
	
	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public WeatherSet getWeatherSet() {
		return this.myWeatherSet;
	}

	/**
	 * @return the in_error_information
	 */
	public boolean isIn_error_information() {
		return in_error_information;
	}

	/**
	 * @param in_error_information the in_error_information to set
	 */
	public void setIn_error_information(boolean in_error_information) {
		this.in_error_information = in_error_information;
	}

	// ===========================================================
	// Methods
	// ===========================================================
	@Override
	public void startDocument() throws SAXException {
		this.myWeatherSet = new WeatherSet();
	}

	@Override
	public void endDocument() throws SAXException {
		// Nothing
	}

	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException 
	{
		// 'Outer' Tags
		
		if (localName.equals("forecast_information")) 
		{
			//Do nothing
		} 
		else if (localName.equals("problem_cause")) 
		{
			//Setting a flag for indicating error retrieval
			in_error_information = true;
		} 
		else if (localName.equals("current_conditions")) 
		{
			this.myWeatherSet
					.setWeatherCurrentCondition(new WeatherCurrentCondition());
			this.in_current_conditions = true;
		} 
		
		else 
		{
		
			String dataAttribute= new String();
			if (this.in_current_conditions)
			{
				dataAttribute = atts.getValue("data");
			}
			if (localName.equals("city")) {} 
			else if (localName.equals("postal_code")) {}
			else if (localName.equals("latitude_e6")) {}
			else if (localName.equals("longitude_e6")) {}
			else if (localName.equals("forecast_date")) {}
			else if (localName.equals("current_date_time")) {}
			else if (localName.equals("unit_system")) {}
			else if (localName.equals("place")) 
			{
				if (this.in_current_conditions) 
				{
					this.myWeatherSet.getWeatherCurrentCondition()
							.setplace(dataAttribute);
				} 
			
			} 
			else if (localName.equals("icon")) 
			{
				if (this.in_current_conditions) 
				{
					this.myWeatherSet.getWeatherCurrentCondition().setIconURL(
							dataAttribute);
				} 
			} 
			else if (localName.equals("condition")) 
			{
				if (this.in_current_conditions) 
				{
					this.myWeatherSet.getWeatherCurrentCondition()
							.setCondition(dataAttribute);
					System.out.println("Setter Condition -"+dataAttribute);
				} 
			}
			// 'Inner' Tags within "<current_conditions>"
			else if (localName.equals("temp_f")) 
			{
				if (this.in_current_conditions)
				{
					this.myWeatherSet.getWeatherCurrentCondition()
						.setTempFahrenheit(Integer.parseInt(dataAttribute));
				}
			} 
			else if (localName.equals("temp_c")) 
			{
				if (this.in_current_conditions)
				{
					this.myWeatherSet.getWeatherCurrentCondition().setTempCelcius(
						Integer.parseInt(dataAttribute));
				}
			} 
			else if (localName.equals("humidity")) 
			{
				if (this.in_current_conditions)
				{
					this.myWeatherSet.getWeatherCurrentCondition().setHumidity(
						dataAttribute);
				}
			} 
			else if (localName.equals("wind_condition")) 
			{
				if (this.in_current_conditions)
				{
					this.myWeatherSet.getWeatherCurrentCondition()
						.setWindCondition(dataAttribute);
				}
			}
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException 
	{
		if (localName.equals("forecast_information")) 
		{
		//	this.in_forecast_information = false;
		} 
		else if (localName.equals("current_conditions")) 
		{
			this.in_current_conditions = false;
		} 
		else if (localName.equals("forecast_conditions")) 
		{
			//this.in_forecast_conditions = false;
		}
		else if (localName.equals("problem_cause")) 
		{
			//System.out.println("Problem cause ends");
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException{
		/*
		 * Would be called on the following structure: <element>characters</element>
		 */
	}
}
