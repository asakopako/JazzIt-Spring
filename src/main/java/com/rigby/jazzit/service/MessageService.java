package com.rigby.jazzit.service;

import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.domain.Message;
import com.rigby.jazzit.repository.MessageRepository;
import com.rigby.jazzit.security.SecurityAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Transactional
public class MessageService {

    @Autowired private MessageRepository messageRepository;
    @Autowired private UserService userService;
    @Autowired private SecurityAspect securityAspect;


    public Message create(Long senderId, Long receiverId, String body) {
        if (!userService.existsById(senderId) || !userService.existsById(receiverId)) {
            throw new NotFoundException("Sender or receiver doesn't exist");
        }

        if (senderId.longValue() != securityAspect.getUserId().longValue()){
            throw new BadRequestException();
        }

        Message message = new Message();
        message.setBody(body);
        message.setCreatedAt(ZonedDateTime.now());
        message.setSender(userService.findById(senderId));
        message.setReceiver(userService.findById(receiverId));
        message = messageRepository.save(message);

        // todo implement firebase send message /topic/user_1 /topic/user_2

        return message;
    }

    public List<Message> findByReceiverId(Long senderId, Long receiverId) {
        if (!userService.existsById(senderId) || !userService.existsById(receiverId)) {
            throw new NotFoundException("Sender or receiver doesn't exist");
        }
        return messageRepository.findBySender_IdAndReceiver_Id(senderId, receiverId);
    }
}
