package com.example.surittec.desafio.domain;

import com.example.surittec.desafio.reference.Telefone_Tipo;
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
        this.number = number.replaceAll("\\D+", "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;

        Phone p = (Phone) o;
        return Objects.equals(id, p.id) &&
                number.equals(p.number);
    }

    @Override
    public int hashCode() {
        int hash = 13;

        hash = hash * 7 + (id == null ? 0 : id.hashCode());
        hash = hash * 7 + number.hashCode();

        return hash;
    }
}
