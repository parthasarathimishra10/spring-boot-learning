package com.partha.springboot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@ApiModel(description = "This is the Employee model")
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name should contain minimum 2 characters")
    @ApiModelProperty(notes = "Name should contain minimum 2 characters")
    private String name;

    @Past(message = "Date of Birth should be past date")
    @ApiModelProperty(notes = "Date of Birth should be past date")
    private Date dob;
}