package com.rigby.jazzit.controller;

import com.rigby.jazzit.domain.Message;
import com.rigby.jazzit.domain.request.MessageRequest;
import com.rigby.jazzit.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {

    @Autowired private MessageService messageService;


    @PostMapping("/api/users/{userId}/messages")
    public ResponseEntity<Message> postMessage(
            @PathVariable Long userId,
            @RequestBody MessageRequest messageRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(messageService.create(userId, messageRequest.getContactId(), messageRequest.getBody()));
    }

    @GetMapping("/api/users/{userId}/messages")
    public ResponseEntity<List<Message>> getMessages(
            @PathVariable Long userId,
            @RequestParam Long contactId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.findByReceiverId(userId, contactId));
    }

}
