package com.codevalor.demo.freeads.dto.request;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
}
