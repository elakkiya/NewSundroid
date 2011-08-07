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

public class places_result {
	
		// ===========================================================
		// Fields
		// ===========================================================

		private String name = null;
		private String vicinity = null;
		private String types[] = null;
		private Geometry geo = null;
		private String icon_URL = null;
		private String reference = null;
		private String id = null;
		
		// ===========================================================
		// Constructors
		// ===========================================================

		public places_result() {
			//super();
		}

		// ===========================================================
		// Getter and Setter methods
		// ===========================================================

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getVicinity() {
			return vicinity;
		}

		public void setVicinity(String vicinity) {
			System.out.println("Setting vicinity" + vicinity);
			this.vicinity = vicinity;
		}

		public String[] getTypes() {
			return types;
		}

		public void setTypes(String[] types) {
			this.types = types;
		}

		public Geometry getGeo() {
			return geo;
		}

		public void setGeo(Geometry geo) {
			this.geo = geo;
		}

		public String getIcon_URL() {
			return icon_URL;
		}

		public void setIcon_URL(String icon_URL) {
			this.icon_URL = icon_URL;
		}

		public String getReference() {
			return reference;
		}

		public void setReference(String reference) {
			this.reference = reference;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
			
}
