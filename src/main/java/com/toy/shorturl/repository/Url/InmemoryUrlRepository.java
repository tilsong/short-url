package com.toy.shorturl.repository.Url;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.toy.shorturl.domain.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class InmemoryUrlRepository implements UrlRepository{
	Object insertLock = new Object();
	List<Url> urlList = new ArrayList<>();

	public int save(Url url) throws RuntimeException {
		int id = 0;

		String defaultUrl = url.getUrl();

		synchronized (insertLock) {
			// url 중복이면 throw
			boolean isPresent = urlList.stream()
				.filter(e -> e.getUrl().equals(defaultUrl))
				.findFirst() // 있으면 에러 방출
				.isPresent();
			if (isPresent) {
				throw new RuntimeException("이미 존재하는 URL입니다.");
			}

			urlList.add(url);
			id = urlList.size() - 1;
		}

		log.info("Url saved. " + "id: " + id + " " + url);

		return id;
	}

	public void update(int index, String encodedUrl) {
		Url url = findOneByIndex(index);

		if (url.getEncodedUrl() == null) {
			url.setEncodedUrl(encodedUrl);
		}

		log.info("Url updated. " + "id: " + index + " " + url);
	}

	 public Url findOneByIndex(int index) throws RuntimeException {
		if (index >= urlList.size()) {
			throw new RuntimeException("존재하지 않는 URL입니다.");
		}

		Url url = urlList.get(index);
		if (url.getUrl().equals("")) {
			throw new RuntimeException("존재하지 않는 URL입니다.");
		}

		return url;
	 }

	public Url findOneByUrl(String url) throws RuntimeException {
		Url findUrl = urlList.stream()
			.filter(e -> e.getUrl().equals(url))
			.findFirst() // empty -> orElseThrow, isPresent -> return value;
			.orElseThrow(() -> new RuntimeException("존재하지 않는 URL입니다."));
		return findUrl;
	}

	public List<Url> findAll() {
		return urlList;
	}

	public void delete(int index) throws RuntimeException {
		Url url;

		url = findOneByIndex(index);

		url.setEncodedUrl("");
		url.setUrl("");
	}

	public void clearStore() {
		urlList.clear();
	}
}
