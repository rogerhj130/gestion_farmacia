package com.springboot.backend.andres.usersapp.usersbackend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Almacen;

@Component
public class AlmacenValidation implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Almacen.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Almacen almacen = (Almacen) target;

        // Validar nombre
        if (almacen.getNombre() == null || almacen.getNombre().isEmpty()) {
            errors.rejectValue(
                "nombre",
                "nombre.requerido",
                "El nombre del almacén es requerido"
            );
        }

        // Validar dirección
        if (almacen.getDireccion() == null || almacen.getDireccion().isEmpty()) {
            errors.rejectValue(
                "direccion",
                "direccion.requerida",
                "La dirección del almacén es requerida"
            );
        }
}
}