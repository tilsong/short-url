package com.toy.shorturl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UrlRequest(
	@NotBlank(message = "입력 값이 존재하지 않습니다.")
	@Pattern(regexp = "^(?:https?:\\/\\/)(?:www\\.)?[a-zA-Z0-9./]+$", message = "잘못된 입력 값입니다.")
	String url
){}


