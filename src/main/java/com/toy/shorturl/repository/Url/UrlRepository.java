package com.toy.shorturl.repository.Url;

import com.toy.shorturl.domain.Url;

public interface UrlRepository {

    int save(Url url);

    void update(int index, String encodedUrl);

    void updateViewCount(int index, long currentViewCount);

    Url findOneByIndex(int index);

    Url findOneByUrl(String url);

    void clearStore();
}
