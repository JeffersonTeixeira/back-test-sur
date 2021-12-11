package com.example.surittec.desafio.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 3, message = "O nome é muito curto")
    @Size(max = 100, message = "O nome é muito longo")
    @NotBlank
    @Column(nullable = false)
    private String name;


    @NotBlank
    @Column(nullable = false)
    private String document;


    @NotBlank
    @JoinColumn(name = "main_email", nullable = false)
    @Email
    private String email;


    @ElementCollection
    @CollectionTable(name = "client_emails")
    @Column(name = "email")
    private Set<@Email String> emails = new HashSet<>();


    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address", nullable = false)
    private Endereco address;


    @NotNull
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "main_phone", nullable = false)
    @JsonManagedReference
    private Phone phone;


    @OneToMany(mappedBy = "client",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private Set<Phone> phones = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        return this == o ||
                o instanceof Client && ((Client) o).getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
