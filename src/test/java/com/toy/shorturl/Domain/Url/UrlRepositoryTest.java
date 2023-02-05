package com.toy.shorturl.Domain.Url;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.toy.shorturl.Module.Base62Converter;
import com.toy.shorturl.domain.Url;
import com.toy.shorturl.repository.Url.InmemoryUrlRepository;
import com.toy.shorturl.repository.Url.UrlRepository;

public class UrlRepositoryTest {
	UrlRepository urlRepository = new InmemoryUrlRepository();

	@AfterEach
	void afterEach() {
		urlRepository.clearStore();
	}

	@Test
	void save() {
		//given
		Url url = new Url("www.url1.com");

		//when
		int savedId = urlRepository.save(url);

		//then
		Url findUrl = urlRepository.findOneByIndex(savedId);

		assertThat(findUrl.getUrl()).isEqualTo(url.getUrl());
	}

	@Test
	void update() {
		//given
		String urlString = "www.url1.com";
		Url url = new Url(urlString);
		int savedId = urlRepository.save(url);
		String encodedUrlString = Base62Converter.intTobase62(savedId);

		//when
		urlRepository.update(savedId, encodedUrlString);

		//then
		Url findUrl = urlRepository.findOneByIndex(savedId);

		assertThat(findUrl.getEncodedUrl()).isEqualTo(encodedUrlString);
	}
}
