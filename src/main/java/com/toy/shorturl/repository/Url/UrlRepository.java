package com.toy.shorturl.repository.Url;

import com.toy.shorturl.domain.Url;

public interface UrlRepository {

    int save(Url url);

    void update(int index, String encodedUrl);

    void UpdateViewCount(int index);

    Url findOneByIndex(int index);

    Url findOneByUrl(String url);

    void clearStore();
}
