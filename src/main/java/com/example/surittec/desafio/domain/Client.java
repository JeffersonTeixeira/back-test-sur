package com.example.surittec.desafio.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

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

    @Transient
    private int version;

    @Size(min = 3, message = "O nome é muito curto")
    @Size(max = 100, message = "O nome é muito longo")
    @Pattern(regexp = "[a-zA-Z0-9À-ÖØ-öø-ÿ\\-.'\\s]+", message = "Só é permitido letras, espaços e números para Nome")
    @Column(nullable = false)
    private String name;


    @NotBlank(message = "CPF é obrigatório")
    @Column(nullable = false)
    @CPF(message = "CPF inválido")
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
    private Phone phone;


    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "client_phone")
    private Set<Phone> phones = new HashSet<>();


    public void setDocument(String document) {
        this.document = document.replaceAll("\\D+", "");
    }

    public Client setVersion(int version) {
        this.version = version;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client c = (Client) o;
        return Objects.equals(id, c.id) &&
                name.equals(c.name) &&
                document.equals(c.document) &&
                email.equals(c.document) &&
                Objects.equals(emails, c.emails) &&
                address.equals(c.address) &&
                phone.equals(c.phone) &&
                Objects.equals(phones, c.phones);
    }

    @Override
    public int hashCode() {
        int hash = 13;

        hash = hash * 7 + (id == null ? 0 : id.hashCode());
        hash = hash * 7 + name.hashCode();
        hash = hash * 7 + document.hashCode();
        hash = hash * 7 + email.hashCode();
        hash = hash * 7 + (emails == null ? 0 : emails.hashCode());
        hash = hash * 7 + address.hashCode();
        hash = hash * 7 + phone.hashCode();
        hash = hash * 7 + (phones == null ? 0 : phones.hashCode());

        return hash;
    }
}
