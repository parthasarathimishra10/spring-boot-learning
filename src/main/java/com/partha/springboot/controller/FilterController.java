package com.partha.springboot.controller;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.partha.springboot.bean.DynamicFilterBean;
import com.partha.springboot.bean.StaticFilterBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterController {

    //User static filtering
    @GetMapping("/values")
    public StaticFilterBean getValue(){
        return new StaticFilterBean("value1","value2","value3");
    }

    //Uses dynamic filtering approach
    @GetMapping("/values-dynamic")
    public MappingJacksonValue getDynamicValue(){

        DynamicFilterBean dynamicFilterBean = new DynamicFilterBean("a","b","c");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("value1","value2");

        FilterProvider filters = new SimpleFilterProvider().addFilter("DynamicFilterBean",filter);

        MappingJacksonValue mapping = new MappingJacksonValue(dynamicFilterBean);
        mapping.setFilters(filters);

        return mapping;
    }
}
