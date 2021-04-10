package com.partha.springboot.versioning;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PersonV2 {
    private String firstName;
    private String lastName;
}
