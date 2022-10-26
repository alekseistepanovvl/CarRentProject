package com.rent.carrent.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;
}
