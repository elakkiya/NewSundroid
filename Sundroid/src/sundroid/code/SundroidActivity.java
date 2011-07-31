
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

package sundroid.code;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import sundroid.code.places.Latitude_longitude;
import sundroid.code.places.xmlhandler_latlong;
import sundroid.code.places.xmlhandler_places;
import sundroid.code.views.SingleWeatherInfoView;
import sundroid.code.weather.GoogleWeatherHandler;
import sundroid.code.weather.WeatherCurrentCondition;
import sundroid.code.weather.WeatherSet;
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
                               	   
                    try {
                    	
                    	 ///////////////// Code to get weather conditions for entered place ///////////////////////////////////////////////////
                         String cityParamString = ((EditText) findViewById(R.id.edit_input))
                                   .getText().toString();
                         String queryString = "https://www.google.com/ig/api?weather="
                                   + cityParamString;
                         queryString.replace("#", "");
                      
                         /* Parsing the xml file*/
                         SAXParserFactory spf = SAXParserFactory.newInstance();
                         SAXParser sp = spf.newSAXParser();
                         XMLReader xr = sp.getXMLReader();

                         GoogleWeatherHandler gwh = new GoogleWeatherHandler();
                         xr.setContentHandler(gwh);
                         
                         HttpClient httpclient = new DefaultHttpClient(); 
                         HttpGet httpget = new HttpGet(queryString.replace(" ", "%20")); 
                         ResponseHandler<String> responseHandler = new BasicResponseHandler();
                         String responseBody = httpclient.execute(httpget, responseHandler); 
                         ByteArrayInputStream is = new ByteArrayInputStream(responseBody.getBytes()); 
                         xr.parse(new InputSource(is)); 
                         Log.d("Sundroid", "parse complete"); 
                         
                         WeatherSet ws = gwh.getWeatherSet();
                                                  
                         newupdateWeatherInfoView(R.id.weather_today, ws
                                   .getWeatherCurrentCondition()," "+cityParamString);
                         
                         ///////////////// Code to get weather conditions for entered place ends /////////////////////////////////////////////////// 
                         
                         ///////////////// Code to get latitude and longitude using zipcode starts ///////////////////////////////////////////////////
                        
                         String latlng_querystring = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + cityParamString.replace(" ", "%20") + "&sensor=false";
                         URL url_latlng = new URL(latlng_querystring);
                         spf = SAXParserFactory.newInstance();
                         sp = spf.newSAXParser();
                            
                         xr = sp.getXMLReader();
                         xmlhandler_latlong xll = new xmlhandler_latlong();
                         xr.setContentHandler(xll);
                         xr.parse(new InputSource(url_latlng.openStream()));
                         	
                         Latitude_longitude ll = xll.getLatlng_resultset();
                         double selectedLat = ll.getLat_lng_pair().getLat();
                         double selectedLng = ll.getLat_lng_pair().getLon();
                         
                         ///////////////// Code to get latitude and longitude using zipcode ends ///////////////////////////////////////////////////
                         
                         ///////////////// Code to get miles from text box & convert to meters for passing into the api link////////////////////////
                         EditText edt = (EditText) findViewById(R.id.edit_miles);
                         float miles = Float.valueOf(edt.getText().toString());
                         float meters =  (float) (miles * 1609.344);
                         
                         ///////////////// Code to get miles from text box & convert to meters for passing into the api link ends /////////////////
                         
                         ///////////////// Code to pass lat,long and radius and get destinations from places api starts////////// /////////////////
                         URL queryString_1 = new URL("https://maps.googleapis.com/maps/api/place/search/xml?location="+Double.toString(selectedLat) + ","+ Double.toString(selectedLng) +"&radius="+Float.toString(meters)+"&types=park|types=aquarium|types=point_of_interest|types=establishment&sensor=false&key=AIzaSyDmP0SB1SDMkAJ1ebxowsOjpAyeyiwHKQU");
                         spf = SAXParserFactory.newInstance();
                         sp = spf.newSAXParser();
                         xr = sp.getXMLReader();
                         xmlhandler_places xhp = new xmlhandler_places();
                         xr.setContentHandler(xhp);
                         xr.parse(new InputSource(queryString_1.openStream()));
                         int arraysize = xhp.getVicinity_List().size();
                         String[] place = new String[25];
                         String[] place_name = new String[25];
                         int i;
                         //Getting name and vicinity tags from the xml file//
                         for(i=0;i<arraysize;i++)
                         {
                            	place[i]= xhp.getVicinity_List().get(i);
                            	place_name[i]=xhp.getPlacename_List().get(i);
                            	place[i]= place[i].replace("#","");
                            	
                         }
                            
                         ///////////////// Code to pass lat,long and radius and get destinations from places api ends////////// /////////////////
                            
                            
                         //////////////////////while loop for getting top 5 from the array////////////////////////////////////////////////
                            
                         int count = 0;
                         int while_ctr = 0;                         
                         String str_weathercondition;
                         str_weathercondition="";
                         WeatherCurrentCondition reftemp;
                         //Places to visit if none of places in the given radius are sunny/clear/partly cloudy
                         String[] rainy_place = {"Indoor Mall","Watch a Movie", "Go to a Restaurant","Shopping!"};
                            
                         while (count < 5)
                         {
                        	 	//Checking if xml vicinity value is empty
	                           	if  (place[while_ctr]==null || place[while_ctr].length()<2)
	                            	{
	                            		while_ctr = while_ctr+1;
	                               	}
		                       		  //First search for places that are sunny or clear 
		                       		 if (while_ctr<i-1)
		                       		 {
		                       			 queryString = "https://www.google.com/ig/api?weather=" + place[while_ctr];
		                       			
		   	                             spf = SAXParserFactory.newInstance();
		   	                             sp = spf.newSAXParser();
		   	
		   	                             xr = sp.getXMLReader();
		   	
		   	                             gwh = new GoogleWeatherHandler();
		   	                             xr.setContentHandler(gwh);
		   	                             httpclient = new DefaultHttpClient(); 
		   	                             httpget = new HttpGet(queryString.replace(" ", "%20")); 
		   	                             responseHandler = new BasicResponseHandler();
		   	                             responseBody = httpclient.execute(httpget, responseHandler); 
		   	                             is = new ByteArrayInputStream(responseBody.getBytes()); 
		   	                             xr.parse(new InputSource(is)); 
		   	                             if (gwh.isIn_error_information()) 
		   	                              {
		   	                            	  System.out.println("Error Info flag set");
		   	                              }
		   	                              else
		   	                              {
		   	                            	  ws = gwh.getWeatherSet();
		   	                             
		   	                            	  reftemp = ws.getWeatherCurrentCondition();
		   	                            	  str_weathercondition = reftemp.getCondition();
		   	                            	  
		   	                            	  //         Check if the condition is sunny or partly cloudy
		   	                            	  if(str_weathercondition.equals("Sunny") || str_weathercondition.equals("Mostly Sunny") || str_weathercondition.equals("Clear"))
		   	                            	  {
		   	                            		  System.out.println("Sunny Loop");
		   	                            	  
		   	                            		  //  Increment the count 
		   	                            		  ++count;
		   	                            	  
		   	                            		  //   Disply the place on the widget 
		   	                            		  if (count == 1)
		   	                            		  {
		   	                            			  newupdateWeatherInfoView(R.id.weather_1, reftemp, place_name[while_ctr]);                            		  
		   	                            		  }
		   	                            		  else if (count == 2)
		   	                            		  {
		   	                            			  newupdateWeatherInfoView(R.id.weather_2, reftemp, place_name[while_ctr]);                            		  
		   	                            		  }
			   	                            	  else if (count == 3)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_3, reftemp, place_name[while_ctr]);                            		  
			   	                            	  }
			   	                            	  else if (count == 4)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_4, reftemp, place_name[while_ctr]);                            		  
			   	                            	  }
			   	                            	  else if (count == 5)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_5, reftemp, place_name[while_ctr]);                            		  
			   	                            	  }
			   	                            	  else {}
		                                     }
		   	                              }
		                       		 }
                       		 
		                       		 //  If Five sunny places not found then search for partly cloudy places 
		                       		 
		                       		 else if (while_ctr>=i && while_ctr<i*2)
		                   			 {
		                       			 queryString = "https://www.google.com/ig/api?weather=" + place[while_ctr-i];
		                       			 queryString=queryString.replace("  ", " ");
		   	                              
		   	                             spf = SAXParserFactory.newInstance();
		   	                             sp = spf.newSAXParser();
		   	
		   	                             // Get the XMLReader of the SAXParser we created. 
		   	                             xr = sp.getXMLReader();
		   	
		   	                             gwh = new GoogleWeatherHandler();
		   	                             xr.setContentHandler(gwh);
		   	
		   	                              // Use HTTPClient to deal with the URL  
		   	                              httpclient = new DefaultHttpClient(); 
		   	                              httpget = new HttpGet(queryString.replace(" ", "%20")); 
		   	                              responseHandler = new BasicResponseHandler();
		   	                              responseBody = httpclient.execute(httpget, responseHandler); 
		   	                              is = new ByteArrayInputStream(responseBody.getBytes()); 
		   	                              xr.parse(new InputSource(is)); 
		   	                              Log.d(DEBUG_TAG, "parse complete"); 
		   	                               
		   	                              if (gwh.isIn_error_information()) {}
			                              else
			                              	{
			                            	  ws = gwh.getWeatherSet();
			                            	  reftemp = ws.getWeatherCurrentCondition();
			                            	  str_weathercondition = reftemp.getCondition();
		   	                             
			                            	  //    Check if the condition is sunny or partly cloudy
			                            	  if(str_weathercondition.equals("Partly Cloudy"))
			                            	  {
		   	                            	   
			                            		  count = count + 1; 
		   	                            	  
			                            		  //  Display the place 
			   	                            	  if (count == 1)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_1, reftemp, place_name[while_ctr-i]);                            		  
			   	                            	  }
			   	                            	  else if (count == 2)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_2, reftemp, place_name[while_ctr-i]);                            		  
			   	                            	  }
			   	                            	  else if (count == 3)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_3, reftemp, place_name[while_ctr-i]);                            		  
			   	                            	  }
			   	                            	  else if (count == 4)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_4, reftemp, place_name[while_ctr-i]);                            		  
			   	                            	  }
			   	                            	  else if (count == 5)
			   	                            	  {
			   	                            		  newupdateWeatherInfoView(R.id.weather_5, reftemp, place_name[while_ctr-i]);                            		  
			   	                            	  }
			   	                            	  else {}
		                                     }
			                              }
		                   			 }
		                       		////////////////////////////////  Give suggestions for a rainy day 
		                       		 else
		                           	 {
		                       			 queryString = "https://www.google.com/ig/api?weather="+cityParamString;
		                       			 queryString=queryString.replace("#", "");
		   	                             
		                       			 spf = SAXParserFactory.newInstance();
		   	                             sp = spf.newSAXParser();
		   	
		   	                             // Get the XMLReader of the SAXParser we created. 
		   	                             xr = sp.getXMLReader();
		   	                             gwh = new GoogleWeatherHandler();
		   	                             xr.setContentHandler(gwh);
		   	
		   	                             httpclient = new DefaultHttpClient(); 
		   	                          
		   	                             httpget = new HttpGet(queryString.replace(" ", "%20")); 
		   	                             // create a response handler 
		   	                             responseHandler = new BasicResponseHandler();
		   	                             responseBody = httpclient.execute(httpget, responseHandler); 
		   	                             is = new ByteArrayInputStream(responseBody.getBytes()); 
		   	                             xr.parse(new InputSource(is)); 
		   	                             if (gwh.isIn_error_information()) {}
				                           
			   	                         else
				                              {
			   	                        	   ws = gwh.getWeatherSet();
			   	                             
			   	                        	   reftemp = ws.getWeatherCurrentCondition();
			   	                        	   str_weathercondition = reftemp.getCondition();
			   	                              		                      
				                       			 if (count == 0)
				   	                           	  {
				                       				newupdateWeatherInfoView(R.id.weather_1, reftemp, rainy_place[0]);	  
				                       				newupdateWeatherInfoView(R.id.weather_2, reftemp, rainy_place[1]);
				                       				newupdateWeatherInfoView(R.id.weather_3, reftemp, rainy_place[2]);
				                       				newupdateWeatherInfoView(R.id.weather_4, reftemp, rainy_place[3]);
				                       				newupdateWeatherInfoView(R.id.weather_5, reftemp, rainy_place[1]);
				   	                           	  }
				   	                           	  else if (count == 1)
				   	                           	  {
					   	                           	newupdateWeatherInfoView(R.id.weather_2, reftemp, rainy_place[1]);
					                   				newupdateWeatherInfoView(R.id.weather_3, reftemp, rainy_place[2]);
					                   				newupdateWeatherInfoView(R.id.weather_4, reftemp, rainy_place[3]);
					                   				newupdateWeatherInfoView(R.id.weather_5, reftemp, rainy_place[1]);                         		  
				   	                           	  }
				   	                           	  else if (count == 2)
				   	                           	  {
				   	                           		  newupdateWeatherInfoView(R.id.weather_3, reftemp, rainy_place[2]);
				   	                           		  newupdateWeatherInfoView(R.id.weather_4, reftemp, rainy_place[0]);
				   	                           		  newupdateWeatherInfoView(R.id.weather_5, reftemp, rainy_place[1]);      	                           		  	                           		  
				   	                           	  }
				   	                           	  else if (count == 3)
				   	                           	  {
				   	                           		  newupdateWeatherInfoView(R.id.weather_4, reftemp, rainy_place[0]);
					                           		  newupdateWeatherInfoView(R.id.weather_5, reftemp, rainy_place[1]); 	                           		  
				   	                           	  }
				   	                           	  else if (count == 4)
				   	                           	  {
				   	                           		  newupdateWeatherInfoView(R.id.weather_5, reftemp, rainy_place[1]);	                           		  
				   	                           	  }
				   	                           	  else {}
				                       		      count =5;
				                              }              
			   	                           count=5;
			                           	 }
			                       		while_ctr++;
		                            }
		                            
		                             
		                            
                            
                    }
                     catch (Exception e) 
                    {
                         resetWeatherInfoViews();
                         Log.e(DEBUG_TAG, "WeatherQueryError", e);
                    }
               }
          });
     }

    private void newupdateWeatherInfoView(int aResourceID,
             WeatherCurrentCondition aWCIS, String loc) throws MalformedURLException {
       
        URL imgURL = new URL("http://www.google.com" + aWCIS.getIconURL());
        ((SingleWeatherInfoView) findViewById(aResourceID)).setRemoteImage(imgURL);
        	
        ((SingleWeatherInfoView) findViewById(aResourceID)).setPlace(loc);
        
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
          ((SingleWeatherInfoView)findViewById(R.id.weather_5)).reset();
     }
}