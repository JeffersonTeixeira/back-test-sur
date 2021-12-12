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
public class Phone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @Enumerated(EnumType.STRING)
    private Telefone_Tipo type;

    public void setNumber(String number) {
        this.number = number.replaceAll("\\D+", "");;
    }

    @Override
    public boolean equals(Object o) {
        return this == o ||
                o instanceof Phone &&
                        ((Phone) o).number.equals(this.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.number);
    }
}
