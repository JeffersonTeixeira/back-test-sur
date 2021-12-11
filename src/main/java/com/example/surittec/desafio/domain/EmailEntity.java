//package com.example.surittec.desafio.domain;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import javax.validation.constraints.Email;
//import java.io.Serializable;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "client_email", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "client_id"})})
//public class EmailEntity implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;
//
//    @Email
//
//    String email;
//
//    @ManyToOne
//    @JoinColumn(name = "client_id")
//    Cliente client;
//
//}
