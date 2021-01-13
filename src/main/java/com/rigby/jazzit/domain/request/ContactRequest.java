package com.rigby.jazzit.domain.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class ContactRequest {

    @Email
    private String email;
}