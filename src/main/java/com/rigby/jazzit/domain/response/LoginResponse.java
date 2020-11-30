package com.rigby.jazzit.domain.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LoginResponse {

    private Long id;
    private String token;
    private String email;
    private String name;

}
