package com.toy.shorturl.repository.Url;

import com.toy.shorturl.domain.Url;

public interface UrlRepository {

    int save(Url url);

    void update(int index, Url url);

    Url findOneByEncodedUrl(String encodedUrl);

    Url findOneByIndex(int index);

    void clearStore();
}
