package com.toy.shorturl.Domain.Url;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.toy.shorturl.Module.Base62Converter;
import com.toy.shorturl.domain.Url;
import com.toy.shorturl.repository.Url.InmemoryUrlRepository;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.repository.ViewUrl.InmemoryViewUrlRepository;
import com.toy.shorturl.repository.ViewUrl.ViewUrlRepository;
import com.toy.shorturl.service.UrlService;

public class UrlServiceTest {
	UrlRepository urlRepository = new InmemoryUrlRepository();
	ViewUrlRepository viewUrlRepository = new InmemoryViewUrlRepository();
	UrlService urlService = new UrlService(urlRepository, viewUrlRepository);

	@AfterEach
	void afterEach() {
		urlRepository.clearStore();
		viewUrlRepository.clearStore();
	}

	@Test
	void addUrl() {
		// given
		String inputUrl = "http://www.naver.com";

		// when
		String encodedUrl = urlService.addUrl(inputUrl);

		// then
		Url findUrl = urlRepository.findOneByUrl(inputUrl);

		assertThat(findUrl.getEncodedUrl()).isEqualTo(encodedUrl);
	}

	@Test
	void getViewCount() {
		// given
		for (int i = 0; i <= 10; i++) {
			urlService.addUrl("www.naver.com");
		}

		int id = 10;
		String encodedUrl = Base62Converter.intTobase62(id);

		// when
		int viewCounting = 100;
		for (int i = 0; i < viewCounting; i++) {
			urlService.findUrlByEncodedUrl(encodedUrl);
		}

		// then
		int countByEncodedUrl = urlService.getViewCount(encodedUrl);

		assertThat(countByEncodedUrl).isEqualTo(viewCounting);
	}



}
