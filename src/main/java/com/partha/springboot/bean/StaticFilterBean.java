package com.partha.springboot.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value = {"value2"}) //used for static filtering on class level. Not the best practice.
public class StaticFilterBean {

    private String value1;
    private String value2;
    @JsonIgnore //used for static filtering. Best practice as you don't need to bother if the property name changes.
    private String value3;
}
