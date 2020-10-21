package com.rigby.jazzit.domain.request;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class LoginRequest {

    @NotBlank @Email
    private String email;

    @NotBlank @Size(min = 4)
    private String password;

}
