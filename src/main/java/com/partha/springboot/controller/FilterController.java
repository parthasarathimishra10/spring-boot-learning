package com.partha.springboot.controller;

import com.partha.springboot.bean.StaticFilterBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    @GetMapping("/values")
    public StaticFilterBean getValue(){
        return new StaticFilterBean("value1","value2","value3");
    }
}
