package com.rigby.jazzit.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
public class Message {

    @Id                                                 // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private Long id;
    private String body;
    private ZonedDateTime createdAt;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;

}
