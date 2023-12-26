/**
 * 
 */
package com.smfs.microservices.shorturl.dao;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author AniketSabale
 *
 */
@Component
public class ShortURLDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * @apiNote 
	 * @param inputData 
	 * @return String ()
	 * @throws Exception ()
	 * @author AniketSabale
	 */
	public Integer insertData(JSONObject inputData) throws Exception{
		System.out.println("started insertion : "+inputData);
		
		String sql = "INSERT INTO tinyurl (encodedKey, longURL, shortURL, currentdate, expirydate) VALUES ("
                + "'" + inputData.get("encodeKey") + "'" + ",'" + inputData.get("longURL") + "'," + "'" + inputData.get("shortURL") + "'," +  "'" + inputData.get("currentdate") + "'," +"'" + inputData.get("expirydate") + "') ";
         
		System.out.println("SQL Query : "+ sql );
        int rows = jdbcTemplate.update(sql);
        
        
        System.out.println("completed insertion : ");
		return rows;
	}
	
	@SuppressWarnings("deprecation")
	public String getData(String encodedKey) throws Exception{
		
		String query = " SELECT longURL "
							+ " FROM tinyurl "
							+ " WHERE encodedKey = ?";
		return (String)jdbcTemplate.queryForObject(query, new Object[]{encodedKey}, String.class);
		
	}
	
	@SuppressWarnings("deprecation")
	public String getURLData(String encodedKey) throws Exception{
		System.out.println("started getData encodedkey:  "+encodedKey);
		
		String query = " SELECT longurl "
							+ "FROM tinyurl "
							+ " WHERE encodedKey = ?";							
		System.out.println("select query : "+query);
		return (String)jdbcTemplate.queryForObject(query, new Object[]{encodedKey}, String.class);
		
	}
}
