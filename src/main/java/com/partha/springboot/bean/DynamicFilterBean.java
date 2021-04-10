package com.partha.springboot.bean;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonFilter("DynamicFilterBean") //Id of the FilterProvider for dynamic filtering
public class DynamicFilterBean {

    private String value1;
    private String value2;
    private String value3;
}
