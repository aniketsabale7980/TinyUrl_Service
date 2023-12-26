/**
 * 
 */
package com.smfs.microservices.shorturl;

import org.springframework.stereotype.Component;

/**
 * @author AniketSabale
 *
 */
@Component
public class LongURLConfiguration {

		private String longURL;
		
		public String getLongURL() {
			return longURL;
		}

		public void setLongURL(String longURL) {
			this.longURL = longURL;
		}		
}
