package com.toy.shorturl.dto;

import jakarta.validation.constraints.NotBlank;

public record ViewcountResponse(@NotBlank String encodedUrl, long viewCount) {}
