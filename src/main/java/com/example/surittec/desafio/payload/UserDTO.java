package com.example.surittec.desafio.payload;

import com.example.surittec.desafio.reference.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDTO {

    private Long Id;
    private String name;
    private List<Role> roles;

}
