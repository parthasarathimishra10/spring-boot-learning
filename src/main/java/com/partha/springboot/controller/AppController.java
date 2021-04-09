package com.partha.springboot.controller;

import com.partha.springboot.bean.HelloWorldBean;
import com.partha.springboot.bean.User;
import com.partha.springboot.dao.UserDao;
import com.partha.springboot.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class AppController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/hello-world/{name}")
    public HelloWorldBean helloWorld(@PathVariable String name){
        return new HelloWorldBean("Hello, "+ name + "!!!!");
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userDao.getUsers();
    }

    @GetMapping("/users/{userId}")
    public EntityModel<User> getUserById(@PathVariable Integer userId){
        User user = userDao.getUserById(userId);
        if(user == null)
            throw new UserNotFoundException("User not found for Id : "+userId);

        EntityModel<User> resource = EntityModel.of(user);
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllUsers());
        resource.add(linkTo.withRel("all-users"));
        return resource;
    }

    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        userDao.addUser(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Integer id){
        if(userDao.getUserById(id) != null)
            userDao.removeUser(id);
        else
            throw new UserNotFoundException("User not found for Id : " + id);
    }
}