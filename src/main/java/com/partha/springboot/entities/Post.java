package com.partha.springboot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Employee employee;
}
