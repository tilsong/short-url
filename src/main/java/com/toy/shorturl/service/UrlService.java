package com.toy.shorturl.service;

import com.toy.shorturl.domain.Url;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.shorturl.exception.type.DuplicateUrlException;
import com.toy.shorturl.exception.type.NoSuchUrlException;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.module.Base62Converter;

@Slf4j
@Service
public class UrlService {
	UrlRepository urlRepository;

	@Autowired
	public UrlService(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	public String addUrl(String url) {
		log.info("add Url.");

		Url newUrl;

		try {
			var savingUrl = new Url(url);
			int index = urlRepository.save(savingUrl);

			var encodedUrl = Base62Converter.intTobase62(index);
			urlRepository.update(index, encodedUrl);

			newUrl = urlRepository.findOneByUrl(url);
		} catch (DuplicateUrlException | NoSuchUrlException e) {
			log.error(e.getMessage());
			throw e;
		}

		return newUrl.getEncodedUrl();
	}

	public String findUrlByEncodedUrl(String encodedUrl) {
		log.info("find url.");

		Url url = null;

		try {
			int index = Base62Converter.base62ToInt(encodedUrl);

			url = urlRepository.findOneByIndex(index);

			urlRepository.UpdateViewCount(index);
		} catch (NoSuchUrlException e) {
			log.info(e.getMessage());
			throw e;
		}

		return url.getUrl();
	}

	public long getViewCount(String encodedUrl) {
		log.info("get viewCount.");

		long viewCount = 0;
		int index = Base62Converter.base62ToInt(encodedUrl);

		try {
			Url url = urlRepository.findOneByIndex(index);
			viewCount = url.getViewCount();
		} catch (NoSuchUrlException e) {
			log.info(e.getMessage());
			throw e;
		}

		return viewCount;
	}
}
