package com.toy.shorturl.service;

import com.toy.shorturl.domain.Url;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.toy.shorturl.exception.type.DuplicateUrlException;
import com.toy.shorturl.exception.type.NoSuchUrlException;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.module.Base62Converter;

@Slf4j
@RequiredArgsConstructor
@Service
public class UrlService {
	private final UrlRepository urlRepository;

	public String addUrl(String url) {
		log.info("add Url.");

		Url newUrl = null;

		try {
			var savingUrl = Url
				.builder()
				.url(url)
				.build();

			int index = urlRepository.save(savingUrl);
			var encodedUrl = Base62Converter.intTobase62(index);

			var updatingUrl = Url
				.builder()
				.url(url)
				.encodedUrl(encodedUrl)
				.build();

			urlRepository.update(index, updatingUrl);

			newUrl = urlRepository.findOneByIndex(index);
		} catch (DuplicateUrlException | NoSuchUrlException e) {
			log.info(e.getMessage());
			throw e;
		}

		return newUrl.getEncodedUrl();
	}

	public String findUrlByEncodedUrl(String encodedUrl) {
		log.info("find url.");

		// find url
		Url url = null;

		try {
			url = urlRepository.findOneByEncodedUrl(encodedUrl);
		} catch (NoSuchUrlException e) {
			log.info(e.getMessage());
			throw e;
		}

		// update viewcount
		int index = Base62Converter.base62ToInt(encodedUrl);
		var updatingUrl = Url
			.builder()
			.url(url.getUrl())
			.encodedUrl(url.getEncodedUrl())
			.viewCount((url.getViewCount()+1))
			.build();

		urlRepository.update(index, updatingUrl);

		return url.getUrl();
	}

	public long getViewCount(String encodedUrl) {
		log.info("get viewCount.");

		long viewCount = 0;

		try {
			int index = Base62Converter.base62ToInt(encodedUrl);

			Url url = urlRepository.findOneByIndex(index);
			viewCount = url.getViewCount();
		} catch (NoSuchUrlException e) {
			log.info(e.getMessage());
			throw e;
		}

		return viewCount;
	}
}
