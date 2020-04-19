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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull @Min(4)
    private String password;

    @Past // "1986-07-11"
    private LocalDate birthday;

    @JsonIgnore
    @ManyToMany
    private List<User> contacts = new ArrayList<>();

    public void addContact(User user){
        contacts.add(user);
    }

}
