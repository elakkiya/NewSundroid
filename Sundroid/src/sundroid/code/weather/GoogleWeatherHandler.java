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
//	private boolean in_forecast_information = false;
	private boolean in_current_conditions = false;
	//private boolean in_forecast_conditions = false;

	private boolean usingSITemperature = false; // false means Fahrenheit

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public WeatherSet getWeatherSet() {
		return this.myWeatherSet;
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
			String qName, Attributes atts) throws SAXException {
		// 'Outer' Tags
		if (localName.equals("forecast_information")) {
		//	this.in_forecast_information = true;
		} else if (localName.equals("current_conditions")) {
			this.myWeatherSet
					.setWeatherCurrentCondition(new WeatherCurrentCondition());
			this.in_current_conditions = true;
		} 
		
	
	else {
			String dataAttribute = atts.getValue("data");
			// 'Inner' Tags of "<forecast_information>"
			if (localName.equals("city")) {
			} else if (localName.equals("postal_code")) {
			} else if (localName.equals("latitude_e6")) {
				/* One could use this to convert city-name to Lat/Long. */
			} else if (localName.equals("longitude_e6")) {
				/* One could use this to convert city-name to Lat/Long. */
			} else if (localName.equals("forecast_date")) {
			} else if (localName.equals("current_date_time")) {
			} else if (localName.equals("unit_system")) {
				if (dataAttribute.equals("SI"))
					this.usingSITemperature = true;
			}
			// SHARED(!) 'Inner' Tags within "<current_conditions>" AND
			// "<forecast_conditions>"
			else if (localName.equals("place")) {
				if (this.in_current_conditions) {
					this.myWeatherSet.getWeatherCurrentCondition()
							.setplace(dataAttribute);
				} 
			
			} else if (localName.equals("icon")) {
				if (this.in_current_conditions) {
					this.myWeatherSet.getWeatherCurrentCondition().setIconURL(
							dataAttribute);
				} 
			
			} else if (localName.equals("condition")) {
				if (this.in_current_conditions) {
					this.myWeatherSet.getWeatherCurrentCondition()
							.setCondition(dataAttribute);
				} 
				
			}
			// 'Inner' Tags within "<current_conditions>"
			else if (localName.equals("temp_f")) {
				this.myWeatherSet.getWeatherCurrentCondition()
						.setTempFahrenheit(Integer.parseInt(dataAttribute));
			} else if (localName.equals("temp_c")) {
				this.myWeatherSet.getWeatherCurrentCondition().setTempCelcius(
						Integer.parseInt(dataAttribute));
			} else if (localName.equals("humidity")) {
				this.myWeatherSet.getWeatherCurrentCondition().setHumidity(
						dataAttribute);
			} else if (localName.equals("wind_condition")) {
				this.myWeatherSet.getWeatherCurrentCondition()
						.setWindCondition(dataAttribute);
			}
		}
	}

	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("forecast_information")) {
		//	this.in_forecast_information = false;
		} else if (localName.equals("current_conditions")) {
			this.in_current_conditions = false;
		} else if (localName.equals("forecast_conditions")) {
			//this.in_forecast_conditions = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		/*
		 * Would be called on the following structure: <element>characters</element>
		 */
	}
}
