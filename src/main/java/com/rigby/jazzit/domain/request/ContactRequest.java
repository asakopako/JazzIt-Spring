package com.rigby.jazzit.domain.request;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Deprecated
public class ContactRequest {

    @NotNull
    private String token;

    @NotNull
    @Email
    private String email;

}