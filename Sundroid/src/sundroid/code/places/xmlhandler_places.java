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

package sundroid.code.places;

import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * SAXHandler for extracting information out of the xml file returned by the Google Places API.
 */

public class xmlhandler_places extends DefaultHandler{
	
	private result_set places_resultset = null;
	private boolean in_result = false;
	private boolean in_inner =false;
	private boolean in_inner_name=false;
	ArrayList<String> Vicinity_List= new ArrayList<String> (50);
	ArrayList<String> placename_List= new ArrayList<String> (50);
	
	
	///Getter and Setter Methods///////////
	
	/**
	 * @return the in_inner_name
	 */
	public boolean isIn_inner_name() {
		return in_inner_name;
	}

	/**
	 * @param in_inner_name the in_inner_name to set
	 */
	public void setIn_inner_name(boolean in_inner_name) {
		this.in_inner_name = in_inner_name;
	}

	/**
	 * @return the placename_List
	 */
	public ArrayList<String> getPlacename_List() {
		return placename_List;
	}

	/**
	 * @param placename_List the placename_List to set
	 */
	public void setPlacename_List(ArrayList<String> placename_List) {
		this.placename_List = placename_List;
	}

	/**
	 * @return the vicinity_List
	 */
	public ArrayList<String> getVicinity_List() {
		return Vicinity_List;
	}

	/**
	 * @param vicinity_List the vicinity_List to set
	 */
	public void setVicinity_List(ArrayList<String> vicinity_List) {
		Vicinity_List = vicinity_List;
	}

	public result_set getPlaces_resultset() {
		//System.out.println("In getter xmlhandler - "+places_resultset.getMy_Places_result().getVicinity());
		return places_resultset;
	}

	public void setPlaces_resultset(result_set places_resultset) {
		this.places_resultset = places_resultset;
	}

	public boolean isIn_result() {
		return in_result;
	}

	public void setIn_result(boolean in_result) {
		this.in_result = in_result;
	}

	/**
	 * @return the in_inner
	 */
	public boolean isIn_inner() {
		return in_inner;
	}

	/**
	 * @param in_inner the in_inner to set
	 */
	public void setIn_inner(boolean in_inner) {
		this.in_inner = in_inner;
	}
		
	/////////////////////Methods//////////////////////////////////
	
	

	@Override
	public void startDocument() throws SAXException {
		this.places_resultset = new result_set();
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
		if (localName.equals("result")) {
			this.places_resultset.setMy_Places_result(new places_result());
			this.in_result = true;
			
		} 
		
		else 
		{
			String dataAttribute = atts.getValue(0);
			// 'Inner' Tags of "<result>"
			if (localName.equals("name")) 
			{
				if (this.in_result) 
				{
					this.in_inner_name = true;
					this.places_resultset.getMy_Places_result().setName(dataAttribute);
					//this.in_inner = false;
				} 
			} 
			else if (localName.equals("type")) 
			{
				this.in_inner=false;
			} 
			else if (localName.equals("geometry")) 
			{
				this.in_inner=false;
			}
			else if (localName.equals("location")) 
			{
				this.in_inner=false;
				
			}
			else if (localName.equals("lat")) 
			{
				this.in_inner=false;
				
			}
			else if (localName.equals("lng")) 
			{
				this.in_inner=false;		
			}
			else if (localName.equals("reference")) 
			{
				this.in_inner=false;
			} 
			else if (localName.equals("id")) 
			{
				this.in_inner=false;
			} 
			else if (localName.equals("icon")) 
			{
				this.in_inner=false;
			} 
			else if (localName.equals("location")) 
			{
				this.in_inner=false;			
			}
			else if (localName.equals("vicinity")) 
			{
				if (this.in_result) 
				{
					this.in_inner = true;
					this.places_resultset.getMy_Places_result().setVicinity(dataAttribute);
				} 
			
			} 				
		}
			
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("result")) {
			this.in_result = false;
		}
		
	}
	
	@Override
	public void characters(char ch[], int start, int length) throws SAXException{
		/*
		 * Would be called on the following structure: <element>characters</element>
		 */
		String textBetween = new String(ch, start, length);
		
			if (this.in_result && this.in_inner) {
				
				this.places_resultset.getMy_Places_result().setVicinity(textBetween);
				
				this.Vicinity_List.add(textBetween);
				
				this.in_inner = false;		
			} 
			if (this.in_result && this.in_inner_name) {
				
				this.places_resultset.getMy_Places_result().setName(textBetween);
				
				this.placename_List.add(textBetween);
				
				this.in_inner_name = false;		
			} 
		
		
		
}
}
