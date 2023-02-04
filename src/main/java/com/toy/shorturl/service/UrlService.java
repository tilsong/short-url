package com.toy.shorturl.service;

import com.toy.shorturl.domain.Url;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toy.shorturl.repository.UrlRepository;
import com.toy.shorturl.Module.Base62Converter;

@Slf4j
@Service
public class UrlService {
	UrlRepository urlRepository;

	@Autowired
	public UrlService(UrlRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	// TODO: addUrl, findUrl 추가
	// @Transactional
	public String addUrl(String url) {
		log.info("add Url");

		String encodedUrl = null;
		// 2개의 트랜잭션 -> insertUrl, findUrl

		// insert
		try {
			int index = urlRepository.insert(url);

			encodedUrl = Base62Converter.intTobase62(index);

			urlRepository.update(index, encodedUrl);
		} catch (RuntimeException re) {
			log.error(re.getMessage());
		}

		// findUrl
		Url newUrl = urlRepository.findOneByUrl(url);

		return newUrl.getEncodedUrl();
	}

	public String findUrlByEncodedUrl(String encodedUrl) {
		log.info("findurl");

		Url url = null;

		try {
			int index = Base62Converter.base62ToInt(encodedUrl);

			url = urlRepository.findOneByIndex(index);
		} catch (RuntimeException re) {
			log.info(re.getMessage());
			return null;
		}

		// 조회 수 모듈 넣기

		return url.getUrl();
	}

}
