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
		this.urlService = url;
	}

	@PostMapping("/{newUrl}")
	public String createUrl(@PathVariable String newUrl) {
		// filter 처리하기
//		if (newUrl != null) {
//			return "잘못된 url입니다.";
//		}

		return urlService.addUrl(newUrl);
	}

	@GetMapping("{encodedUrl}")
	public String getUrl(@PathVariable String encodedUrl) {
		// filter 처리하기 -> localhost:8080/{encodedUrl}

		return urlService.findUrlByEncodedUrl(encodedUrl);
	}

}
