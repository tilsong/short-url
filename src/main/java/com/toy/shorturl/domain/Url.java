package com.toy.shorturl.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Url {
    String url;
    String encodedUrl;
    long viewCount;

    public Url(String url) {
        this.url = url;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    @Override
    public String toString() {
        return "url='" + url + ", encodedUrl='" + encodedUrl;
    }
}
