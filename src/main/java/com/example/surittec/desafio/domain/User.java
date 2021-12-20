package com.example.surittec.desafio.domain;

import com.example.surittec.desafio.reference.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String password;

    @ElementCollection
    @CollectionTable(name = "user_roles")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(String name, String password, Set<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User() {
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                o instanceof User &&
                        ((User) o).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
