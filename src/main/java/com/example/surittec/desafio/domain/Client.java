package com.example.surittec.desafio.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Getter
@Setter
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Size(min = 3, message = "O nome é muito curto")
    @Size(max = 100, message = "O nome é muito longo")
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9À-ÖØ-öø-ÿ\\-.'\\s]+", message = "Só é permitido letras, espaços e números para Nome")
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
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinColumn(name = "main_phone", nullable = false)
    private Phone phone;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    @JoinTable(name = "client_phones")
    private Set<Phone> phones = new HashSet<>();


    public void setDocument(String document) {
        this.document = document.replaceAll("\\D+", "");
    }

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
