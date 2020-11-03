package com.rigby.jazzit.domain.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MessageRequest {

    @NotNull
    private Long receiverId;

    @NotBlank
    private String body;
}
