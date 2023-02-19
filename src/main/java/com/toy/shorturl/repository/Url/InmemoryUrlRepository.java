package com.toy.shorturl.repository.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.toy.shorturl.domain.Url;
import com.toy.shorturl.exception.type.DuplicateUrlException;
import com.toy.shorturl.exception.type.NoSuchUrlException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class InmemoryUrlRepository implements UrlRepository {
	private final Object insertLock = new Object();
	private final List<Url> urlList = new ArrayList<>();

	public int save(Url url) throws DuplicateUrlException {
		int id = 0;

		var defaultUrl = url.getUrl();
			
		// 위치 수정 보류
		synchronized (insertLock) {
			// url 중복이면 throw
			urlList.stream()
				.filter(e -> e.getUrl().equals(defaultUrl))
				.findFirst()
				.ifPresent(m -> {throw new DuplicateUrlException("이미 존재하는 Url입니다");});

			urlList.add(url);
			id = urlList.size() - 1;
		}

		log.info("Url saved. " + "id: " + id + " " + url);

		return id;
	}


	public void update(int index, Url url) {
		var getUrl = findOneByIndex(index);

		getUrl.setUrl(url.getUrl());
		getUrl.setEncodedUrl(url.getEncodedUrl());
		getUrl.setViewCount(url.getViewCount());

		log.info("Url updated. " + "id: " + index + " " + url);
	}

	 public Url findOneByIndex(int index) throws NoSuchUrlException {
		if (index >= urlList.size()) {
			throw new NoSuchUrlException("존재하지 않는 URL입니다.");
		}

		var url = urlList.get(index);
		if (url.getUrl().equals("")) {
			throw new NoSuchUrlException("존재하지 않는 URL입니다.");
		}

		return url;
	 }

	public Url findOneByEncodedUrl(String encodedUrl) throws NoSuchElementException {
		return urlList.stream()
			.filter(e -> e.getEncodedUrl().equals(encodedUrl))
			.findFirst()
			.orElseThrow(() -> new NoSuchUrlException("존재하지 않는 URL입니다."));
	}

	public List<Url> findAll() {
		return urlList;
	}

	public void delete(int index) throws RuntimeException {
		Url url;

		url = findOneByIndex(index);

		url.setEncodedUrl("");
		url.setUrl("");

		log.info("Url deleted. " + "id: " + index);
	}

	public void clearStore() {
		urlList.clear();
	}
}
