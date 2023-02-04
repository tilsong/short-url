package com.toy.shorturl.repository;

import java.util.ArrayList;
import java.util.List;

import com.toy.shorturl.domain.Url;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class InmemoryUrlRepository implements UrlRepository{
	Object insertLock = new Object();
	List<Url> urlList = new ArrayList<>();

	public int insert(String newUrl) {
		int id = 0;

		Url url = new Url(newUrl);
		String defaultUrl = url.getUrl();

		synchronized (insertLock) {
			for (int i = 0; i < urlList.size(); i++) {
				if (urlList.get(i).getUrl().equals(defaultUrl)) {
					throw new RuntimeException("이미 존재하는 URL입니다.");
				}
			}

			urlList.add(url);
			id = urlList.size() - 1;
		}

		log.info("Url inserted. " + "id: " + id + " url: " + newUrl);

		return id;
	}

	public void update(int index, String encodedUrl) {
		Url url = urlList.get(index);

		if (url.getEncodedUrl() != null) {
			url.setEncodedUrl(encodedUrl);
		}

		log.info("Url updated. " + "id: " + index + " url: " + encodedUrl);
	}

	 public Url findOneByIndex(int index) throws RuntimeException {
		int listSize = urlList.size();
		if (index >= listSize) {
			throw new RuntimeException("존재하지 않는 URL입니다.");
		}

		 return urlList.get(index);
	 }

	public Url findOneByUrl(String url) throws RuntimeException {
		for (int i = 0; i < urlList.size(); i++) {
			if (urlList.get(i).getUrl().equals(url)) {
				return urlList.get(i);
			}
		}

		throw new RuntimeException("존재하지 않는 URL입니다.");
	}
}
