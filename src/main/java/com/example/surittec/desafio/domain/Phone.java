package com.example.surittec.desafio.domain;

import com.example.surittec.desafio.reference.Telefone_Tipo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "number"})})
public class Phone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    @JsonBackReference
    private Client client;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    private Telefone_Tipo type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return Objects.equals(
                this.client != null ? this.client.getId() : null,
                phone.client != null ? phone.client.getId() : null
        ) &&
                Objects.equals(number, phone.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.client != null ? this.client.getId() : null, this.number);
    }
}
