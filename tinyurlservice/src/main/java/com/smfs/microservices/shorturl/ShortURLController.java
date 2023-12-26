package com.smfs.microservices.shorturl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class ShortURLController {
	@Autowired
	private URLShortenerService urlShortenerService;
	
	@PostMapping("/getShortURL")
	public ShortURLConfiguration getShortURL(@RequestBody LongURLConfiguration longURLConfiguration) throws Exception {
		String shortURL = urlShortenerService.shortenURL(longURLConfiguration.getLongURL());
		System.out.println("shortURL>>>>>>>>>>>>>"+shortURL);
		return new ShortURLConfiguration(shortURL);
	}
	
	@GetMapping("eU/{shortURL}")
	public ModelAndView getLongURL(@PathVariable("shortURL") String shortURL) throws Exception {
		System.out.println("Get mapping started");
		String longURL = urlShortenerService.expandURL(shortURL);
		return new ModelAndView("redirect:" + longURL);
	}
	
	
}