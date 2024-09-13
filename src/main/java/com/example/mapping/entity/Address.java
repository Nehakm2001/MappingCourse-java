package com.example.mapping.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String street;
    private String city;
    private String country;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "address")
    private Student student;
}
