package com.joe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @JsonIgnore
    @OneToMany (mappedBy = "assignee", cascade = CascadeType.ALL)
    private List<Issue> assignedIssues;

    private int projectSize;
}
