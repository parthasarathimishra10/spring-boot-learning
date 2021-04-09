package com.partha.springboot.controller;

import com.partha.springboot.bean.HelloWorldBean;
import com.partha.springboot.bean.User;
import com.partha.springboot.dao.UserDao;
import com.partha.springboot.exception.UserNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
@Api(value = "UserAppController")
@RestController
public class AppController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MessageSource messageSource;

    @ApiOperation(value = "Get Hello World with Name", response = String.class, tags = "hello-world-swagger-doc")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/hello-world/{name}")
    public HelloWorldBean helloWorld(@PathVariable String name){
        return new HelloWorldBean("Hello, "+ name + "!!!!");
    }

    @GetMapping("/hello-world-i18n")
    public String helloWorld(){
        return messageSource.getMessage("good.morning", null, LocaleContextHolder.getLocale());
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

        //HATEOAS Implementation
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