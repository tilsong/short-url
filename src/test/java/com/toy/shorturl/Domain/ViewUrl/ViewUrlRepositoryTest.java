package com.toy.shorturl.Domain.ViewUrl;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.toy.shorturl.domain.ViewUrl;
import com.toy.shorturl.repository.ViewUrl.InmemoryViewUrlRepository;
import com.toy.shorturl.repository.ViewUrl.ViewUrlRepository;

public class ViewUrlRepositoryTest {
	ViewUrlRepository viewUrlRepository = new InmemoryViewUrlRepository();

	@AfterEach
	void afterEach() { viewUrlRepository.clearStore();}

	@Test
	void saveAndCountById() {
		int insertCount = 10000;
		int expectCounting = 100;

		for (int i = 0; i < insertCount; i++) {
			ViewUrl viewUrl = new ViewUrl(i % 100); // id = 0~99, 각각 100개씩 save
			viewUrlRepository.save(viewUrl);
		}

		int findCounting = viewUrlRepository.countById(10); // 0~99 중 하나의 id

		assertThat(findCounting).isEqualTo(expectCounting);
	}
}
