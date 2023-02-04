package com.toy.shorturl.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class UrlRepository {

	List<String> urlList = new ArrayList<>();

	// 생성
	public void insertUrl(String newUrl) {
		urlList.add(newUrl);
	}

	// 찾기
	// public String findOneUrl(String hashedUrl) {
		// return
	// }
}
