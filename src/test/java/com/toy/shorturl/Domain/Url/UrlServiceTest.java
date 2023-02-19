package com.toy.shorturl.Domain.Url;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.toy.shorturl.module.Base62Converter;
import com.toy.shorturl.domain.Url;
import com.toy.shorturl.repository.Url.InmemoryUrlRepository;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.service.UrlService;
import com.toy.shorturl.service.ViewCountService;

public class UrlServiceTest {
	UrlRepository urlRepository = new InmemoryUrlRepository();
	ViewCountService viewCountService = new ViewCountService(urlRepository);
	UrlService urlService = new UrlService(viewCountService, urlRepository);

	@AfterEach
	void afterEach() {
		urlRepository.clearStore();
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
		long countByEncodedUrl = urlService.getViewCount(encodedUrl);

		assertThat(countByEncodedUrl).isEqualTo(viewCounting);
	}
}
