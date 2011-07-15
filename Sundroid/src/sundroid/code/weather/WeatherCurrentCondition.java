package sundroid.code.weather;

import android.util.Log;

/**
 * Holds the information between the <current_conditions>-tag of what the Google
 * Weather API returned.
 */
public class WeatherCurrentCondition {

	// ===========================================================
	// Fields
	// ===========================================================

	private String place = null;
	private Integer tempCelcius = null;
	private Integer tempFahrenheit = null;
	private String iconURL = null;
	private String condition = null;
	private String windCondition = null;
	private String humidity = null;

	// ===========================================================
	// Constructors
	// ===========================================================

	public WeatherCurrentCondition() {

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public String getplace() {
		
		return this.place;
	}

	public void setplace(String place) {
		this.place = place;
		Log.i("day","week day"+place);
	}

	public Integer getTempCelcius() {
		return this.tempCelcius;
	}

	public void setTempCelcius(Integer temp) {
		this.tempCelcius = temp;
	}

	public Integer getTempFahrenheit() {
		return this.tempFahrenheit;
	}

	public void setTempFahrenheit(Integer temp) {
		this.tempFahrenheit = temp;
	}

	public String getIconURL() {
		return this.iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getCondition() {
		return this.condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getWindCondition() {
		return this.windCondition;
	}

	public void setWindCondition(String windCondition) {
		this.windCondition = windCondition;
	}

	public String getHumidity() {
		return this.humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
}
