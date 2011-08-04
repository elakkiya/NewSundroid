/*
 * Sundroid - An open source project for the Android platform, gets top 5 tourist locations to visit on a day based on weather conditions
 * Application written in Java
 * Application uses Google Places API and Google Weather API Powered by GOOGLE
 * 
 * Copyright (C) 2011 Elakkiya Pandian and Shweta Ojha
*
* Please see the file License in this distribution for license terms. Below is the link to the file License.
* https://github.com/elakkiya/NewSundroid/blob/master/License
*
* Following is the link for the repository- https://github.com/elakkiya/NewSundroid
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

package sundroid.code.views;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
//import java.util.Map;

import sundroid.code.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/*** The View shows icon and weather info*/

public class SingleWeatherInfoView extends LinearLayout 
{

     // ===========================================================
     // Fields
     // ===========================================================

     private ImageView myWeatherImageView = null;
     private TextView myTempTextView = null;
     private TextView TempPlace =null;

     // ===========================================================
     // Constructors
     // ===========================================================

     public SingleWeatherInfoView(Context context) {
          super(context);
     }

     public SingleWeatherInfoView(Context context, AttributeSet attrs) {
          super(context, attrs);
          
          this.myWeatherImageView = new ImageView(context);
          this.myWeatherImageView.setImageDrawable(getResources().getDrawable(
                    R.drawable.dunno));
          this.myTempTextView = new TextView(context);
          this.myTempTextView.setText("?");
          this.myTempTextView.setTextSize(10);
          this.myTempTextView.setTextColor(0xFF000000);
          this.myTempTextView.setTypeface(Typeface
                    .create("Tahoma", Typeface.BOLD));
          this.TempPlace = new TextView(context);
          this.TempPlace.setText("");
          this.TempPlace.setTextSize(10);	
          this.TempPlace.setTextColor(0xFF000000);
          this.TempPlace.setTypeface(Typeface
                    .create("Tahoma", Typeface.BOLD));
          /* Add child views to this object. */
          this.addView(this.myWeatherImageView, new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));								
          this.addView(this.myTempTextView, new LinearLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
          this.addView(this.TempPlace, new LinearLayout.LayoutParams(
        		  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
     }

     // ===========================================================
     // Getter & Setter
     // ===========================================================

     public void reset() {
          this.myWeatherImageView.setImageDrawable(getResources().getDrawable(
                    R.drawable.dunno));
          this.myTempTextView.setText("?");
          this.TempPlace.setText("");
     }

     /** Sets the Child-ImageView of this to the URL passed. */
     public void setRemoteImage(URL aURL) {
          try {
               URLConnection conn = aURL.openConnection();
               conn.connect();
               InputStream is = conn.getInputStream();
               BufferedInputStream bis = new BufferedInputStream(is);
               Bitmap bm = BitmapFactory.decodeStream(bis);
               bis.close();
               is.close();
               this.myWeatherImageView.setImageBitmap(bm);
          } catch (IOException e) {
               /* Reset to 'Dunno' on any error. */
               this.myWeatherImageView.setImageDrawable(getResources()
                         .getDrawable(R.drawable.dunno));
          }
     }

     public void setTempCelcius(int aTemp) {
          this.myTempTextView.setText("" + aTemp + " °C");
     }

     public void setTempFahrenheit(int aTemp) {
          this.myTempTextView.setText("" + aTemp + " °F");
     }

     public void setTempFahrenheitMinMax(int aMinTemp, int aMaxTemp) {
          this.myTempTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °F");
     }

     public void setTempCelciusMinMax(int aMinTemp, int aMaxTemp) {
          this.myTempTextView.setText("" + aMinTemp + "/" + aMaxTemp + " °C");
     }

     public void setTempString(String aTempString) {
          this.myTempTextView.setText(aTempString);
     }
     
     public void setPlace(String Place) {
 		this.TempPlace.setText("" +Place);
 	}
    
}