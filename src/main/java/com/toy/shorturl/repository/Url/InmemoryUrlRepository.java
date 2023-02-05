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

	public int save(Url url) {
		int id = 0;

		String defaultUrl = url.getUrl();

		synchronized (insertLock) { // url 중복 피하기
			urlList.stream()
				.filter(e -> e.getUrl().equals(defaultUrl))
				.collect(Collectors.toList());
				// .orElseThrow(() -> new RuntimeException("이미 존재하는 URL 입니다."));

			urlList.add(url);
			id = urlList.size() - 1;
		}

		log.info("Url inserted. " + "id: " + id + " " + url);

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
		return urlList.stream()
			.filter(e -> e.getUrl() == url)
			.findFirst()
			.orElseThrow(() -> new RuntimeException("존재하지 않는 URL입니다."));
	}

	public List<Url> findAll() {
		return urlList;
	}

	public void delete(int index) {
		Url url = findOneByIndex(index);

		url.setEncodedUrl("");
		url.setUrl("");
	}

	public void clearStore() {
		urlList.clear();
	}
}
