/*
 * Sundroid - An open source project for the Android platform, gets top 5 tourist locations to visit on a day based on weather conditions
 * Application written in Java
 * Application uses Google Places API, Google Geocode API and Google Weather API Powered by GOOGLE
 * 
 * Copyright (C) 2011 Elakkiya Pandian and Shweta Ojha
 *
 * Please see the file License in this distribution for license terms. Below is the link to the file License.
 * https://github.com/elakkiya/NewSundroid/blob/master/License
 *
 * Following is the link for the repository- https://github.com/elakkiya/NewSundroid
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or (at
 * your option) any later version.
 *  
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 * Written by Elakkiya Pandian <elakkiya@gmail.com> and Shweta Ojha <shojha@pdx.edu>
 * 
 * Weather API Reference - Tutorial on Weather API anddev.org
*/

package sundroid.code.places;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class xmlhandler_latlong extends DefaultHandler{
	
	
		private Latitude_longitude latlng_resultset = null;
		private boolean in_location = false;
		private boolean in_result = false;
		private boolean in_lat=false;
		private boolean in_lng =false;
		
		
		////////////////////////Getter and Setter Methods//////////////////////////
		
		/**
		 * @return the in_lat
		 */
		public boolean isIn_lat() {
			return in_lat;
		}
		/**
		 * @param in_lat the in_lat to set
		 */
		public void setIn_lat(boolean in_lat) {
			this.in_lat = in_lat;
		}
		/**
		 * @return the in_lng
		 */
		public boolean isIn_lng() {
			return in_lng;
		}
		/**
		 * @param in_lng the in_lng to set
		 */
		public void setIn_lng(boolean in_lng) {
			this.in_lng = in_lng;
		}
		/**
		 * @return the in_result
		 */
		public boolean isIn_result() {
			return in_result;
		}
		/**
		 * @param in_result the in_result to set
		 */
		public void setIn_result(boolean in_result) {
			this.in_result = in_result;
		}
		/**
		 * @return the latlng_resultset
		 */
		public Latitude_longitude getLatlng_resultset() {
			return latlng_resultset;
		}
		/**
		 * @param latlng_resultset the latlng_resultset to set
		 */
		public void setLatlng_resultset(Latitude_longitude latlng_resultset) {
			this.latlng_resultset = latlng_resultset;
		}
		/**
		 * @return the in_location
		 */
		public boolean isIn_location() {
			return in_location;
		}
		/**
		 * @param in_location the in_location to set
		 */
		public void setIn_location(boolean in_location) {
			this.in_location = in_location;
		}
		
		
		////////////////////////Parsing Methods//////////////////////////////
		
		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException 
				{

					if (localName.equals("result")) 
					{
						this.latlng_resultset.setLat_lng_pair(new Geometry());
						this.in_result = true;
						
					} 
							
					if (localName.equals("location")) 
					{
						 if (this.in_result) 
						 {
							this.in_location = true;
									
						 } 
					} 
					
					if (localName.equals("lat")) 
					{
						 if (this.in_result) 
						 {
							this.in_lat = true;
									
						 } 
					} 
					
					if (localName.equals("lng")) 
					{
						 if (this.in_result) 
						 {
							this.in_lng = true;
									
						 } 
					} 
						
					
				}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			
			
			String textBetween = new String(ch, start, length);
			
			if (this.in_result && this.in_location && this.in_lat) 
			{
				
				this.latlng_resultset.getLat_lng_pair().setLat(Double.parseDouble(textBetween));
				
			} 
			if (this.in_result && this.in_location && this.in_lng) 
			{
				
				this.latlng_resultset.getLat_lng_pair().setLon(Double.parseDouble(textBetween));
				
			} 
						
			
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (localName.equals("result")) 
			{
				this.in_result = false;
			} 
			if (localName.equals("location")) 
			{
				this.in_location = false;
			} 
			if (localName.equals("lat")) 
			{
				this.in_lat = false;
			} 
			if (localName.equals("lng")) 
			{
				this.in_lng = false;
			} 
		}
		
		
		@Override
		public void endDocument() throws SAXException {
			// TODO Auto-generated method stub
			//super.endDocument();
		}
		
		
		@Override
		public void startDocument() throws SAXException {
			// TODO Auto-generated method stub
			this.latlng_resultset = new Latitude_longitude();
		}
		
		
		
		
		
					
			
	
		
		
		
		
		
		
		
		

}
