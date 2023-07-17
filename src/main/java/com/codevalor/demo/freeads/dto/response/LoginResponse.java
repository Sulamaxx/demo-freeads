package com.codevalor.demo.freeads.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(String token) {
}
