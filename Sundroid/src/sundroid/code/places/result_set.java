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

public class result_set {
	private places_result my_Places_result = null;
	
	///Getter and Setter Methods
	
	public result_set() {
		//super();
		// TODO Auto-generated constructor stub
	}

	public places_result getMy_Places_result() {
		return my_Places_result;
	}

	public void setMy_Places_result(places_result my_Places_result) {
		this.my_Places_result = my_Places_result;
	}

	

}
