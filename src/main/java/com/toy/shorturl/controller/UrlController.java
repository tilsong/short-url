package com.toy.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.shorturl.service.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController {
	UrlService urlService;

	@Autowired
	UrlController(UrlService url) {
		this.urlService = new UrlService();
	}

	@PostMapping("/{url}")
	public String createUrl(@PathVariable String url) {
		if (url != null) {
			return urlService.addUrl(url);
		}
		return "잘못된 url입니다.";
	}
}
