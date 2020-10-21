package com.rigby.jazzit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {

    @Id                                                         // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // autoincrement
    private Long id;

    @NotNull @NotEmpty @NotBlank @Email @Column(unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)      // este campo es ignorado al devolver la respuesta
    @NotNull @NotEmpty @NotBlank @Size(min = 4)
    private String password;

    @Past // "1986-07-11"
    private LocalDate birthday;

    @JsonIgnore
    @ManyToMany
    private List<User> contacts = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender")                             // se hace una relacion bidireccional con la tabla message
    private List<Message> senderMessages = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Message> receiverMessages = new ArrayList<>();

    public void addContact(User user){
        contacts.add(user);
    }

}
