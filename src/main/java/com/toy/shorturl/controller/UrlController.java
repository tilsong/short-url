package com.toy.shorturl.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.toy.shorturl.dto.UrlRequest;
import com.toy.shorturl.dto.UrlResponse;
import com.toy.shorturl.dto.ViewcountResponse;
import com.toy.shorturl.service.UrlService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping
public class UrlController {
	UrlService urlService;

	@Autowired
	UrlController(UrlService urlService) {
		this.urlService = urlService;
	}

	@PostMapping("/url") // www.localhost:8080/url , { "encodedUrl": "1s" }
	public ResponseEntity<UrlResponse> createUrl(@Valid @RequestBody UrlRequest urlRequest) {
		log.info("create Url. request url: " + urlRequest.url());

		String encodedUrl = urlService.addUrl(urlRequest.url());
		UrlResponse dto = new UrlResponse(encodedUrl);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/url/{encodedUrl}") // www.localhost:8080/url/{encodedUrl}
	public ResponseEntity redirectUrlByEncodedUrl(
		@PathVariable("encodedUrl") @NotBlank(message = "입력 값이 존재하지 않습니다.") String encodedUrl
	) throws URISyntaxException {
		log.info("get Url. request encodedUrl: " + encodedUrl);

		String decodedUrl = urlService.findUrlByEncodedUrl(encodedUrl);
		if (decodedUrl != null) {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setLocation(new URI(decodedUrl));

			return new ResponseEntity(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
		}

		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/viewcount/{encodedUrl}") // www.localhost:8080/viewcount/{encodedUrl}
	public ResponseEntity<ViewcountResponse> getViewCountByEncodedUrl(
		@PathVariable("encodedUrl") @NotBlank(message = "입력 값이 존재하지 않습니다.") String encodedUrl
	) {
		log.info("get ViewCountByEncodedUrl. request encodedUrl: " + encodedUrl);

		int viewCount = urlService.getViewCount(encodedUrl);
		if(viewCount != -1) {
			ViewcountResponse dto = new ViewcountResponse(encodedUrl, viewCount);
			return new ResponseEntity(dto, HttpStatus.OK);
		};

		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/")
	public ResponseEntity mainPage() {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
