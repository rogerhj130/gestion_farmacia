package com.springboot.backend.andres.usersapp.usersbackend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Cliente;

@Component
public class ClienteValidation implements Validator{

    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        // Validar si el nombre está vacío o es solo espacios en blanco
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "nombre",
             "nombre.requerido",
             "El nombre es requerido");

        // Validar si el apellido (paterno) está vacío
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "paterno",
             "apellido.requerido",
             "El apellido es requerido");
    }
}
