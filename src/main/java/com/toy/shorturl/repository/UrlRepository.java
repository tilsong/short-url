package com.toy.shorturl.repository;

import com.toy.shorturl.domain.Url;

public interface UrlRepository {
    int insert(String newUrl);

    void update(int index, String encodedUrl);

    Url findOneByIndex(int index);

    Url findOneByUrl(String url);
}
