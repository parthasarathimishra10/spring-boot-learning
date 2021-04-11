package com.partha.springboot.controller;

import com.partha.springboot.entities.Post;
import com.partha.springboot.exception.UserNotFoundException;
import com.partha.springboot.entities.Employee;
import com.partha.springboot.repository.EmployeeRepository;
import com.partha.springboot.repository.PostRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Api(value = "Employee Controller")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    @ApiOperation(value = "GetEmployeeById")
    @GetMapping("/employees/{id}")
    public EntityModel<Employee> getEmployeeById(@PathVariable Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
            throw new UserNotFoundException("Employee not found for Id : "+ id);

        //HATEOAS Implementation
        EntityModel<Employee> resource = EntityModel.of(employee.get());
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).getAllEmployees());
        resource.add(linkTo.withRel("all-employees"));
        return resource;
    }

    @PostMapping("/employees")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee){
        Employee savedEmployee = employeeRepository.save(employee);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedEmployee.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        if(employeeRepository.findById(id).isPresent())
            employeeRepository.deleteById(id);
        else
            throw new UserNotFoundException("Employee not found for Id : " + id);
    }

    @GetMapping("/employees/{id}/posts")
    public List<Post> getAllPostsByEmployeeId(@PathVariable Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
            throw new UserNotFoundException("Employee not found for Id : " + id);
        return employee.get().getPosts();
    }

    @PostMapping("/employees/{id}/posts")
    public ResponseEntity<Object> createPostsByEmployeeId(@PathVariable Integer id, @RequestBody Post post){
        Optional<Employee> employee = employeeRepository.findById(id);
        if(!employee.isPresent())
            throw new UserNotFoundException("Employee not found for Id : " + id);
        post.setEmployee(employee.get());
        Post savedPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}