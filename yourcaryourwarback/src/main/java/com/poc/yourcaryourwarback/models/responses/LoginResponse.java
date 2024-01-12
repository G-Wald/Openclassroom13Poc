package com.poc.yourcaryourwarback.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String username;
    private String email;
    private String error;
}
