package com.toy.shorturl.service;

import org.springframework.stereotype.Service;

@Service
public class UrlService {

	public String addUrl(String url) {
		System.out.println("addUrl");

		return url;
	}
}
