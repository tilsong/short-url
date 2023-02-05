package com.toy.shorturl.repository.ViewUrl;

import com.toy.shorturl.domain.ViewUrl;

public interface ViewUrlRepository {
	void save(ViewUrl viewurl);

	int countById(int id);

	void clearStore();
}
