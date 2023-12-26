package com.smfs.microservices.shorturl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.smfs.microservices.shorturl.dao.ShortURLDAO;

/*
 * URL Shortener
 */
//@Configuration

@Component
public class URLShortenerService {

	@Autowired
	private Environment environment;

	@Autowired
	ShortURLDAO shorturldao;

	private String domain; 
	private char myChars[]; 
	private Random myRand; 	
	private int keyLength; 

	// Default Constructor
	URLShortenerService() {
		myRand = new Random();
		keyLength = 8;
		myChars = new char[62];
		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			myChars[i] = (char) j;
		}
	}

	// shortenURL
	// the public method which can be called to shorten a given URL
	public String shortenURL(String longURL) throws Exception {
		System.out.println("long url substring : >>>>>>>>>>>>>>>>" + longURL);
		String shortURL = "";
		domain = environment.getProperty("domain");
		String key = getKey();
		
		shortURL = domain + "/" + key;
		
		JSONObject objURLData = new JSONObject();
		objURLData.put("encodeKey", key);
		objURLData.put("longURL", longURL);
		objURLData.put("shortURL", shortURL);
		System.out.println("JSONObject ===> "+ objURLData);
		
		
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		System.out.println("ts : "+ts);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 2);
		Date dateAfter = cal.getTime();
		objURLData.put("currentdate",ts);
		objURLData.put("expirydate", dateAfter);
		System.out.println("expirydate"+ dateAfter);
		System.out.println("currentdate"+ dateAfter);

		
		System.out.println("long url substring : *************" + longURL);
		// Inserting data into table
		System.out.println("objURLData "+objURLData );
		Integer rows = shorturldao.insertData(objURLData);
		
		if (rows > 0) {
            System.out.println("A new row has been inserted.");
        }
		
		return shortURL;
	}
	 
	// expandURL
	// public method which returns back the original URL given the shortened url
	public String expandURL(String shortURL) throws Exception {
		System.out.println("Short URL " + shortURL);
		String longURL = null;
		
		try{
			longURL = shorturldao.getURLData(shortURL);
			System.out.println("long Url expand "+longURL);
		}catch (org.springframework.dao.EmptyResultDataAccessException e) {
			System.out.println("catch block");
			longURL = environment.getProperty("longURLMsg");
		}catch (Exception e) {
			throw e;
		}
		
		return longURL;
	}

	/*
	 * Get Key method
	 */
	private String getKey() throws Exception {
		String key;
		key = generateKey();
		return key;
	}

	// generateKey
	private String generateKey() throws Exception {
		String key = "";
		boolean flag = true;
		while (flag) {
			key = "";
			for (int i = 0; i <= keyLength; i++) {
				key += myChars[myRand.nextInt(62)];
			}
			
			String isExist = null;
					
			try{
				isExist = shorturldao.getData(key);
			}catch (org.springframework.dao.EmptyResultDataAccessException e) {
				isExist = null;
			}catch (Exception e) {
				throw e;
			}
			
			if (isExist==null) {
				flag = false;
			}
		}
		return key;
	}
}