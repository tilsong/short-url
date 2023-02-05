package com.toy.shorturl.dto;

import jakarta.validation.constraints.NotBlank;

public record EncodedUrlRequest(
	@NotBlank(message = "입력 값이 존재하지 않습니다.")
	String encodedUrl
) {}
