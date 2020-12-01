package com.rigby.jazzit.service;

import com.rigby.jazzit.config.exception.BadRequestException;
import com.rigby.jazzit.config.exception.ForbiddenException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.config.exception.UnauthorizedException;
import com.rigby.jazzit.domain.Message;
import com.rigby.jazzit.repository.MessageRepository;
import com.rigby.jazzit.security.SecurityAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

        if (senderId.longValue() != securityAspect.getUserId().longValue()) {
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

    public List<Message> findByReceiverId(Long userId, Long contactId) {
        if (!userService.existsById(userId) || !userService.existsById(contactId)) {
            throw new NotFoundException("Sender or receiver doesn't exist");
        }

        // Cuando un usuario solicita los datos de otro usuario
        if (userId.longValue() != securityAspect.getUserId().longValue()) {
            throw new ForbiddenException("Invalid operation"); // o BadRequestException
        }

        List<Message> messages = messageRepository.findBySender_IdAndReceiver_Id(userId, contactId);
        messages.addAll(messageRepository.findBySender_IdAndReceiver_Id(contactId, userId));
        return messages.stream().sorted(Comparator.comparing(Message::getCreatedAt)).collect(Collectors.toList());
    }
}
