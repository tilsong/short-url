package com.toy.shorturl.dto;

import jakarta.validation.constraints.NotBlank;

public record UrlResponse(@NotBlank String encodedUrl) {}
