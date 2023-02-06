package com.toy.shorturl.service;

import com.toy.shorturl.domain.Url;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.shorturl.domain.ViewUrl;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.Module.Base62Converter;
import com.toy.shorturl.repository.ViewUrl.ViewUrlRepository;

@Slf4j
@Service
public class UrlService {
	UrlRepository urlRepository;
	ViewUrlRepository viewUrlRepository;

	@Autowired
	public UrlService(UrlRepository urlRepository, ViewUrlRepository viewUrlRepository) {
		this.urlRepository = urlRepository;
		this.viewUrlRepository = viewUrlRepository;
	}

	public String addUrl(String url) {
		log.info("add Url.");

		String encodedUrl = null;

		// save transaction
		try {
			Url newUrl = new Url(url);
			int index = urlRepository.save(newUrl);

			encodedUrl = Base62Converter.intTobase62(index);

			urlRepository.update(index, encodedUrl);
		} catch (RuntimeException re) {
			log.error(re.getMessage());
		}

		// find transaction
		Url newUrl;
		try {
			newUrl = urlRepository.findOneByUrl(url);
		} catch (RuntimeException re) {
			log.error(re.getMessage());
			return null;
		}

		return newUrl.getEncodedUrl();
	}

	public String findUrlByEncodedUrl(String encodedUrl) {
		log.info("find url.");

		Url url = null;
		int index = 0;

		try {
			index = Base62Converter.base62ToInt(encodedUrl);

			url = urlRepository.findOneByIndex(index);
		} catch (RuntimeException re) {
			log.info(re.getMessage());
			return null;
		}

		// 조회 수 추가
		ViewUrl viewUrl = new ViewUrl(index);
		viewUrlRepository.save(viewUrl);

		return url.getUrl();
	}

	public int getViewCount(String encodedUrl) {
		log.info("get viewCount.");

		int index = Base62Converter.base62ToInt(encodedUrl);

		int count = viewUrlRepository.countById(index);

		return count;
	}
}
