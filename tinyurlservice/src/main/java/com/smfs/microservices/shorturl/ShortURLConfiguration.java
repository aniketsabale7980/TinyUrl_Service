package com.smfs.microservices.shorturl;

public class ShortURLConfiguration {

	private String shortURL;
	
	public ShortURLConfiguration(String shortURL){
		this.shortURL = shortURL;
	}

	public String getShortURL() {
		return shortURL;
	}

	public void setShortURL(String shortURL) {
		this.shortURL = shortURL;
	}
	
	
}
