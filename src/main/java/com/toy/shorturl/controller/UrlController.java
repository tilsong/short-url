package com.toy.shorturl.controller;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.shorturl.dto.EncodedUrlRequest;
import com.toy.shorturl.dto.UrlRequest;
import com.toy.shorturl.dto.UrlResponse;
import com.toy.shorturl.dto.ViewcountRequest;
import com.toy.shorturl.dto.ViewcountResponse;
import com.toy.shorturl.service.UrlService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/url")
public class UrlController {
	UrlService urlService;

	@Autowired
	UrlController(UrlService url) {
		this.urlService = url;
	}

	@PostMapping("/new")
	public ResponseEntity<UrlResponse> createUrl(@RequestBody UrlRequest urlRequest) {
		log.info("create Url.");

		String encodedUrl = urlService.addUrl(urlRequest.url());
		UrlResponse dto = new UrlResponse(encodedUrl);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping // www.localhost:8080/url , { "encodedUrl": "1s" }
	public ResponseEntity redirectUrlByEncodedUrl(@RequestBody EncodedUrlRequest encodedUrlRequest) throws URISyntaxException {
		log.info("get Url.");

		String decodedUrl = urlService.findUrlByEncodedUrl(encodedUrlRequest.encodedUrl());
		URI newUrl = new URI(decodedUrl);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(newUrl);
		
		return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping("/viewcount/{encodedUrl}")
	public ResponseEntity<ViewcountResponse> getViewCountByEncodedUrl(@RequestBody ViewcountRequest viewcountRequest) {
		log.info("get ViewCountByEncodedUrl.");

		String encodedUrl = viewcountRequest.encodedUrl();
		int viewCount = urlService.getViewCount(encodedUrl);
		ViewcountResponse dto = new ViewcountResponse(encodedUrl, viewCount);

		return new ResponseEntity(dto, HttpStatus.OK);
	}

}
