package com.ibm.academia.apirest.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarreraDTO {

    private Integer id;
    @NotNull( message = "nombre no puede ser nulo")
    @NotEmpty(message = "nombre no puede estar vacio")
    @Size( min = 5, max = 80, message = "nombre debe tener entre 5 y 80 caracteres")
    private String nombre;

    @Positive(message = "cantidadMaterias debe ser mayor a cero")
    private Integer cantidadMaterias;

    @Positive(message = "cantidadAnios debe ser mayor a cero")
    private Integer cantidadAnios;
}
