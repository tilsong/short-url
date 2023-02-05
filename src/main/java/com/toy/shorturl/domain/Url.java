package com.toy.shorturl.domain;

public class Url {
    String url;
    String encodedUrl;

    public Url(String url) {
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEncodedUrl(String encodedUrl) {
        this.encodedUrl = encodedUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getEncodedUrl() {
        return encodedUrl;
    }

    @Override
    public String toString() {
        return "url='" + url + ", encodedUrl='" + encodedUrl;
    }


}
