package com.partha.springboot.controller;

import com.partha.springboot.bean.HelloWorldBean;
import com.partha.springboot.bean.User;
import com.partha.springboot.exception.UserNotFoundException;
import com.partha.springboot.model.Employee;
import com.partha.springboot.repository.EmployeeRepository;
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
import java.util.Optional;

@Api(value = "Employee Controller")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

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
            throw new UserNotFoundException("User not found for Id : " + id);
    }
}