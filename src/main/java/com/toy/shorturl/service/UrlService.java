package com.toy.shorturl.service;

import java.util.Arrays;

import com.toy.shorturl.domain.Url;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.toy.shorturl.exception.type.DuplicateUrlException;
import com.toy.shorturl.exception.type.NoSuchUrlException;
import com.toy.shorturl.repository.Url.UrlRepository;
import com.toy.shorturl.module.Base62Converter;

@Slf4j
@RequiredArgsConstructor
@Service
public class UrlService {
	private final ViewCountService viewCountService;
	private final UrlRepository urlRepository;

	private static Object[] locks;
	{
		locks = new Object[1000];
		Arrays.fill(locks, new Object());
	}

	@Async("threadPoolTaskExecutor")
	public void addViewCount(int index) {
		try {
			synchronized (locks[index % locks.length]) {
				Url url = urlRepository.findOneByIndex(index);
				long currentViewCount = url.getViewCount();
				urlRepository.updateViewCount(index, ++currentViewCount);
			}
		} catch (NoSuchUrlException e) {
			log.error(e.getMessage());
			log.error("id-" + index +"에 대한 추가 조회 수가 기록되지 않았습니다.");
			// 로그만 기록하고 에러를 던지지 않음.
		}
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
		int index = -1;

		try {
			index = Base62Converter.base62ToInt(encodedUrl);
			url = urlRepository.findOneByIndex(index);
		} catch (NoSuchUrlException e) {
			log.error(e.getMessage());
			throw e;
		}

		viewCountService.addViewCount(index);

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
			log.error(e.getMessage());
			throw e;
		}

		return viewCount;
	}
}
