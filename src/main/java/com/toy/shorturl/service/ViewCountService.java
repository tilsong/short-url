package com.toy.shorturl.service;

import java.util.Arrays;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.toy.shorturl.domain.Url;
import com.toy.shorturl.exception.type.NoSuchUrlException;
import com.toy.shorturl.repository.Url.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ViewCountService {
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
}
