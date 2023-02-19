package com.toy.shorturl.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder @Getter @Setter
public class Url {
    String url;
    @Builder.Default String encodedUrl = "";
    @Builder.Default long viewCount = 0;

    @Override
    public String toString() {
        return "url='" + url + ", encodedUrl='" + encodedUrl;
    }
}
