package com.springboot.backend.andres.usersapp.usersbackend.validation;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Laboratorio;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class LaboratorioValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // Indicar que esta clase de validación es para el objeto Laboratorio
        return Laboratorio.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Laboratorio laboratorio = (Laboratorio) target;

        // Validar nombre
        if (laboratorio.getNombre() == null || laboratorio.getNombre().isEmpty()) {
            errors.rejectValue(
                "nombre",
                 "nombre.requerido",
                  "El nombre es requerido");
        } else if (laboratorio.getNombre().length() < 3 || laboratorio.getNombre().length() > 100) {
            errors.rejectValue("nombre", "nombre.longitud", "El nombre debe tener entre 3 y 100 caracteres");
        }

        // Validar dirección
        if (laboratorio.getDireccion() == null || laboratorio.getDireccion().isEmpty()) {
            errors.rejectValue(
                "direccion",
                 "direccion.requerida",
                  "La dirección es requerida");
        } else if (laboratorio.getDireccion().length() < 5 || laboratorio.getDireccion().length() > 200) {
            errors.rejectValue("direccion", "direccion.longitud", "La dirección debe tener entre 5 y 200 caracteres");
        }

        // Validar email
        if (laboratorio.getEmail() == null || laboratorio.getEmail().isEmpty()) {
            errors.rejectValue(
                "email",
                 "email.requerido",
                  "El correo electrónico es requerido");
        } else if (!isValidEmail(laboratorio.getEmail())) {
            errors.rejectValue("email", "email.formato", "El correo electrónico debe ser válido");
        }

        // Validar teléfono
        if (laboratorio.getTelefono() == null || laboratorio.getTelefono().isEmpty()) {
            errors.rejectValue(
                "telefono",
                 "telefono.requerido",
                  "El teléfono es requerido");
        } else if (laboratorio.getTelefono().length() < 8 || laboratorio.getTelefono().length() > 15) {
            errors.rejectValue(
                "telefono",
                 "telefono.longitud",
                  "El teléfono debe tener entre 8 y 15 caracteres");
        }
    }

    // Validar formato del correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    // Validar formato del teléfono (por ejemplo, 123-456-7890)
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{3}-\\d{3}-\\d{4}$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }
}
