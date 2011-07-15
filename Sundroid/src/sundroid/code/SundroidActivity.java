package sundroid.code;


import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import sundroid.code.views.SingleWeatherInfoView;
import sundroid.code.weather.GoogleWeatherHandler;
import sundroid.code.weather.WeatherCurrentCondition;
//import sundroid.code.weather.WeatherForecastCondition;
import sundroid.code.weather.WeatherSet;
//import sundroid.code.weather.WeatherUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SundroidActivity extends Activity {

     private final String DEBUG_TAG = "Sundroid";
     private CheckBox chk_usecelsius = null;

     /** Called when the activity is first created. */
     @Override
     public void onCreate(Bundle icicle) {
          super.onCreate(icicle);
          setContentView(R.layout.main);

          this.chk_usecelsius = (CheckBox) findViewById(R.id.chk_usecelsius);

          Button cmd_submit = (Button) findViewById(R.id.cmd_submit);
          cmd_submit.setOnClickListener(new OnClickListener() {

               public void onClick(View arg0) {
                   // URL url;
                    try {
                         /* Get what user typed to the EditText. */
                         String cityParamString = ((EditText) findViewById(R.id.edit_input))
                                   .getText().toString();
                         String queryString = "https://www.google.com/ig/api?weather="
                                   + cityParamString;
                         /* Replace blanks with HTML-Equivalent. */
                         //url = new URL(queryString.replace(" ", "%20"));

                         /* Get a SAXParser from the SAXPArserFactory. */
                         SAXParserFactory spf = SAXParserFactory.newInstance();
                         SAXParser sp = spf.newSAXParser();

                         /* Get the XMLReader of the SAXParser we created. */
                         XMLReader xr = sp.getXMLReader();

                         /*
                          * Create a new ContentHandler and apply it to the
                          * XML-Reader
                          */
                         GoogleWeatherHandler gwh = new GoogleWeatherHandler();
                         xr.setContentHandler(gwh);

                         /* Use HTTPClient to deal with the URL */ 
                         HttpClient httpclient = new DefaultHttpClient(); 
                         HttpGet httpget = new HttpGet(queryString.replace(" ", "%20")); 
                         Log.d(DEBUG_TAG, "executing request " + httpget.getURI()); 
                         // create a response handler 
                         ResponseHandler<String> responseHandler = new BasicResponseHandler();
                         Log.i("Respond Handler","Step 1");
                         String responseBody = httpclient.execute(httpget, responseHandler); 
                          Log.d(DEBUG_TAG, "response from httpclient:\n "+responseBody); 
                          
                         ByteArrayInputStream is = new ByteArrayInputStream(responseBody.getBytes()); 
                         xr.parse(new InputSource(is)); 
                         Log.d(DEBUG_TAG, "parse complete"); 
                         // parse complete
                         
                         
                         
                         /* Parse the xml-data our URL-call returned. */
                         //xr.parse(new InputSource(url.openStream()));

                         /* Our Handler now provides the parsed weather-data to us. */
                         WeatherSet ws = gwh.getWeatherSet();

                         /* Update the SingleWeatherInfoView with the parsed data. */
                         newupdateWeatherInfoView(R.id.weather_today, ws
                                   .getWeatherCurrentCondition()," Hillsboro");

                         
                         
                         ///New Code/////////////////////////////////////////
                         
                         
                         
                         int hightemp;
                         String bestplace;
                         hightemp=0;
                         bestplace="";
                         
                         queryString = "https://www.google.com/ig/api?weather=97219";
                       /* Replace blanks with HTML-Equivalent. */
                       //url = new URL(queryString.replace(" ", "%20"));

                       /* Get a SAXParser from the SAXPArserFactory. */
                       spf = SAXParserFactory.newInstance();
                       sp = spf.newSAXParser();

                       /* Get the XMLReader of the SAXParser we created. */
                       xr = sp.getXMLReader();

                       /*
                        * Create a new ContentHandler and apply it to the
                        * XML-Reader
                        */
                       gwh = new GoogleWeatherHandler();
                       xr.setContentHandler(gwh);

                       /* Use HTTPClient to deal with the URL */ 
                       httpclient = new DefaultHttpClient(); 
                       httpget = new HttpGet(queryString.replace(" ", "%20")); 
                       Log.d(DEBUG_TAG, "executing request " + httpget.getURI()); 
                       // create a response handler 
                       responseHandler = new BasicResponseHandler();
                       Log.i("Respond Handler","Step 1");
                       responseBody = httpclient.execute(httpget, responseHandler); 
                        Log.d(DEBUG_TAG, "response from httpclient:\n "+responseBody); 
                        
                       is = new ByteArrayInputStream(responseBody.getBytes()); 
                       xr.parse(new InputSource(is)); 
                       Log.d(DEBUG_TAG, "parse complete"); 
                         
                       ws = gwh.getWeatherSet();
                         
                       newupdateWeatherInfoView(R.id.weather_1, ws
                               .getWeatherCurrentCondition(),"Multnomah Falls");

                 
                       //To select Best place
                       WeatherCurrentCondition reftemp = ws.getWeatherCurrentCondition();
                       
                       if(reftemp.getTempCelcius() > hightemp)
                       {
                    	   hightemp = reftemp.getTempCelcius();
                    	   bestplace = "Multnomah Falls";
                       }
                         
                                            
                       queryString = "https://www.google.com/ig/api?weather=97301";
                       /* Replace blanks with HTML-Equivalent. */
                       //url = new URL(queryString.replace(" ", "%20"));

                       /* Get a SAXParser from the SAXPArserFactory. */
                       spf = SAXParserFactory.newInstance();
                       sp = spf.newSAXParser();

                       /* Get the XMLReader of the SAXParser we created. */
                       xr = sp.getXMLReader();

                       /*
                        * Create a new ContentHandler and apply it to the
                        * XML-Reader
                        */
                       gwh = new GoogleWeatherHandler();
                       xr.setContentHandler(gwh);

                       /* Use HTTPClient to deal with the URL */ 
                       httpclient = new DefaultHttpClient(); 
                       httpget = new HttpGet(queryString.replace(" ", "%20")); 
                       Log.d(DEBUG_TAG, "executing request " + httpget.getURI()); 
                       // create a response handler 
                       responseHandler = new BasicResponseHandler();
                       Log.i("Respond Handler","Step 1");
                       responseBody = httpclient.execute(httpget, responseHandler); 
                        Log.d(DEBUG_TAG, "response from httpclient:\n "+responseBody); 
                        
                       is = new ByteArrayInputStream(responseBody.getBytes()); 
                       xr.parse(new InputSource(is)); 
                       Log.d(DEBUG_TAG, "parse complete"); 
                         
                       ws = gwh.getWeatherSet();
                         
                       newupdateWeatherInfoView(R.id.weather_2, ws
                               .getWeatherCurrentCondition(),"Silver Falls");
                       
                       
                       //To select Best place
                       reftemp = ws.getWeatherCurrentCondition();
                       
                       if(reftemp.getTempCelcius() > hightemp)
                       {
                    	   hightemp = reftemp.getTempCelcius();
                    	   bestplace = "Silver Falls";
                       }
                       
                       
                       
                       
                       queryString = "https://www.google.com/ig/api?weather=97110";
                       /* Replace blanks with HTML-Equivalent. */
                       //url = new URL(queryString.replace(" ", "%20"));

                       /* Get a SAXParser from the SAXPArserFactory. */
                       spf = SAXParserFactory.newInstance();
                       sp = spf.newSAXParser();

                       /* Get the XMLReader of the SAXParser we created. */
                       xr = sp.getXMLReader();

                       /*
                        * Create a new ContentHandler and apply it to the
                        * XML-Reader
                        */
                       gwh = new GoogleWeatherHandler();
                       xr.setContentHandler(gwh);

                       /* Use HTTPClient to deal with the URL */ 
                       httpclient = new DefaultHttpClient(); 
                       httpget = new HttpGet(queryString.replace(" ", "%20")); 
                       Log.d(DEBUG_TAG, "executing request " + httpget.getURI()); 
                       // create a response handler 
                       responseHandler = new BasicResponseHandler();
                       Log.i("Respond Handler","Step 1");
                       responseBody = httpclient.execute(httpget, responseHandler); 
                        Log.d(DEBUG_TAG, "response from httpclient:\n "+responseBody); 
                        
                       is = new ByteArrayInputStream(responseBody.getBytes()); 
                       xr.parse(new InputSource(is)); 
                       Log.d(DEBUG_TAG, "parse complete"); 
                         
                       ws = gwh.getWeatherSet();
                         
                       newupdateWeatherInfoView(R.id.weather_3, ws
                               .getWeatherCurrentCondition(),"Canon Beach");
                       
                       
                     //To select Best place
                      reftemp = ws.getWeatherCurrentCondition();
                       
                       if(reftemp.getTempCelcius() > hightemp)
                       {
                    	   hightemp = reftemp.getTempCelcius();
                    	   bestplace = "Canon Beach";
                       }
                       
                       
                       queryString = "https://www.google.com/ig/api?weather=97604";
                     
                       /* Get a SAXParser from the SAXPArserFactory. */
                       spf = SAXParserFactory.newInstance();
                       sp = spf.newSAXParser();

                       /* Get the XMLReader of the SAXParser we created. */
                       xr = sp.getXMLReader();

                       /*
                        * Create a new ContentHandler and apply it to the
                        * XML-Reader
                        */
                       gwh = new GoogleWeatherHandler();
                       xr.setContentHandler(gwh);

                       /* Use HTTPClient to deal with the URL */ 
                       httpclient = new DefaultHttpClient(); 
                       httpget = new HttpGet(queryString.replace(" ", "%20")); 
                       Log.d(DEBUG_TAG, "executing request " + httpget.getURI()); 
                       // create a response handler 
                       responseHandler = new BasicResponseHandler();
                       Log.i("Respond Handler","Step 1");
                       responseBody = httpclient.execute(httpget, responseHandler); 
                        Log.d(DEBUG_TAG, "response from httpclient:\n "+responseBody); 
                        
                       is = new ByteArrayInputStream(responseBody.getBytes()); 
                       xr.parse(new InputSource(is)); 
                       Log.d(DEBUG_TAG, "parse complete"); 
                         
                       ws = gwh.getWeatherSet();
                         
                       newupdateWeatherInfoView(R.id.weather_4, ws
                               .getWeatherCurrentCondition(),"Crater Lake");
                       
                       
                       //To select Best place
                       reftemp = ws.getWeatherCurrentCondition();
                       
                       if(reftemp.getTempCelcius() > hightemp)
                       {
                    	   hightemp = reftemp.getTempCelcius();
                    	   bestplace = "Crater Lake";
                       }
                       
                       TextView p1 = (TextView)findViewById(R.id.txt_ans);
                       p1.setText("Today's Suggestion - " + bestplace);
                      
                         
                         ////////////////new code end///////////////////
                     

                    } catch (Exception e) {
                         resetWeatherInfoViews();
                         Log.e(DEBUG_TAG, "WeatherQueryError", e);
                    }
               }
          });
     }

    private void newupdateWeatherInfoView(int aResourceID,
             WeatherCurrentCondition aWCIS, String loc) throws MalformedURLException {
        
        /* Construct the Image-URL. */
        URL imgURL = new URL("http://www.google.com" + aWCIS.getIconURL());
        ((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);
        	
        ((SingleWeatherInfoView) findViewById(aResourceID)).setPlace(loc);
        
        /* Convert from Celsius to Fahrenheit if necessary. */
        if (this.chk_usecelsius.isChecked()){
             ((SingleWeatherInfoView) findViewById(aResourceID))
                       .setTempCelcius(aWCIS.getTempCelcius());
        }else{
             ((SingleWeatherInfoView) findViewById(aResourceID))
                       .setTempFahrenheit(aWCIS.getTempFahrenheit());
        }
   }
     

     private void resetWeatherInfoViews() {
          ((SingleWeatherInfoView)findViewById(R.id.weather_today)).reset();
          ((SingleWeatherInfoView)findViewById(R.id.weather_1)).reset();
          ((SingleWeatherInfoView)findViewById(R.id.weather_2)).reset();
          ((SingleWeatherInfoView)findViewById(R.id.weather_3)).reset();
          ((SingleWeatherInfoView)findViewById(R.id.weather_4)).reset();
     }
}