package com.toy.shorturl.dto;

import jakarta.validation.constraints.NotBlank;

public record ViewcountRequest (@NotBlank String encodedUrl){}
