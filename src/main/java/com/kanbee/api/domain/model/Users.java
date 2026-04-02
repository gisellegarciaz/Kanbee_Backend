package com.kanbee.api.domain.model;

import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class Users {

    private UUID id;
    private String name;

}
