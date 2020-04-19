package com.rigby.jazzit.domain.request;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class LoginRequest {

    @NotNull @Email
    private String email;

    @NotNull @Min(4)
    private String password;

}
