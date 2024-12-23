package com.springboot.backend.andres.usersapp.usersbackend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Empleado;

@Component
public class EmpleadoValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Empleado.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Empleado empleado = (Empleado) target;

        // Validar si el nombre está vacío o solo tiene espacios
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "nombre",
              "nombre.requerido",
               "El nombre es requerido");

        // Validar si el apellido paterno está vacío
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "paterno",
              "apellido.requerido",
               "El apellido paterno es requerido");

        // Validar si el apellido materno está vacío
        ValidationUtils.rejectIfEmptyOrWhitespace(
            errors,
             "materno",
              "apellido.requerido",
               "El apellido materno es requerido");

                // Validar si el teléfono no es null, no es negativo y tiene un máximo de 15 dígitos
        // Validar si el teléfono no es null, no es negativo y tiene entre 8 y 15 dígitos
        if (empleado.getTelefono() != null) {
            // Verificar que el teléfono sea un número positivo con entre 8 y 15 dígitos
            if (!empleado.getTelefono().matches("^[0-9]{8,15}$")) {
                errors.rejectValue(
                    "telefono",
                    "telefono.invalido",
                    "El teléfono debe ser un número positivo de entre 8 y 15 dígitos.");
            }
        }


    }
}
