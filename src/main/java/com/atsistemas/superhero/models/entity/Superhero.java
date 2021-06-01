package com.atsistemas.superhero.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Superhero implements Serializable {

    private int id;

    private String name;

    private LocalDate dateOfBirth;

    private String country;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
