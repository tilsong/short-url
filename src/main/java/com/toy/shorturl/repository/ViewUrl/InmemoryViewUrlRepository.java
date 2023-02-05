package com.toy.shorturl.repository.ViewUrl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.toy.shorturl.domain.ViewUrl;

@Repository
public class InmemoryViewUrlRepository implements ViewUrlRepository {
	List<ViewUrl> viewcountList = new ArrayList<>();

	@Override
	public void save(ViewUrl viewUrl) {
		viewcountList.add(viewUrl);
	}

	@Override
	public int countById(int id) {
		return (int) viewcountList.stream()
			.filter(e -> e.id() == id)
			.count();
	}

	@Override
	public void clearStore() {
		viewcountList.clear();
	}
}
