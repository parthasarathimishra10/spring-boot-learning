package com.partha.springboot.controller;

import com.partha.springboot.versioning.PersonV1;
import com.partha.springboot.versioning.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    // Example of URI Versioning
    @GetMapping("/v1/person")
    public PersonV1 getPersonV1(){
        return new PersonV1("Partha Mishra");
    }

    @GetMapping("/v2/person")
    public PersonV2 getPersonV2(){
        return new PersonV2("Partha", "Mishra");
    }

    //Example of RequestParam Versioning
    @GetMapping(value = "/person", params = "version=1")
    public PersonV1 getPersonV1Param(){
        System.out.println("Inside Request Param API 1");
        return new PersonV1("Partha Mishra");
    }

    @GetMapping(value = "/person", params = "version=2")
    public PersonV2 getPersonV2Param(){
        System.out.println("Inside Request Param API 2");
        return new PersonV2("Partha", "Mishra");
    }

    //Example of Header Versioning
    @GetMapping(value = "/person", headers = "X-API-VERSION=1")
    public PersonV1 getPersonV1Header(){
        System.out.println("Inside Header API 1");
        return new PersonV1("Partha Mishra");
    }

    @GetMapping(value = "/person", headers = "X-API-VERSION=2")
    public PersonV2 getPersonV2Header(){
        System.out.println("Inside Header API 2");
        return new PersonV2("Partha", "Mishra");
    }

    //Example of Media Type/ Content Negotiation/ Produces versioning
    @GetMapping(value = "/person", produces = "application/partha-v1+json")
    public PersonV1 getPersonV1Produces(){
        System.out.println("Inside Produces API 1");
        return new PersonV1("Partha Mishra");
    }

    @GetMapping(value = "/person", produces = "application/partha-v2+json")
    public PersonV2 getPersonV2Produces(){
        System.out.println("Inside Produces API 2");
        return new PersonV2("Partha", "Mishra");
    }
}
