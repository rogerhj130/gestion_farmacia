package com.springboot.backend.andres.usersapp.usersbackend.validation;


import com.springboot.backend.andres.usersapp.usersbackend.entities.Proveedor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class ProveedorValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // Indicar que esta clase de validación es para el objeto Proveedor
        return Proveedor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Proveedor proveedor = (Proveedor) target;

        // Validar nombre
        if (proveedor.getNombre() == null || proveedor.getNombre().isEmpty()) {
            errors.rejectValue("nombre", "nombre.requerido", "El nombre es requerido");
        } else if (proveedor.getNombre().length() < 3 || proveedor.getNombre().length() > 100) {
            errors.rejectValue("nombre", "nombre.longitud", "El nombre debe tener entre 3 y 100 caracteres");
        }

        // Validar dirección
        if (proveedor.getDireccion() == null || proveedor.getDireccion().isEmpty()) {
            errors.rejectValue("direccion", "direccion.requerida", "La dirección es requerida");
        } else if (proveedor.getDireccion().length() < 5 || proveedor.getDireccion().length() > 200) {
            errors.rejectValue("direccion", "direccion.longitud", "La dirección debe tener entre 5 y 200 caracteres");
        }

        // Validar teléfono
        if (proveedor.getTelefono() == null || proveedor.getTelefono().isEmpty()) {
            errors.rejectValue("telefono", "telefono.requerido", "El teléfono es requerido");
        } else if (proveedor.getTelefono().length() < 8 || proveedor.getTelefono().length() > 15) {
            errors.rejectValue("telefono", "telefono.longitud", "El teléfono debe tener entre 8 y 15 caracteres");
        } else if (!isValidPhoneNumber(proveedor.getTelefono())) {
            errors.rejectValue("telefono", "telefono.formato", "El teléfono debe tener un formato válido (solo dígitos o con guiones)");
        }

        // Validar email
        if (proveedor.getEmail() == null || proveedor.getEmail().isEmpty()) {
            errors.rejectValue("email", "email.requerido", "El correo electrónico es requerido");
        } else if (!isValidEmail(proveedor.getEmail())) {
            errors.rejectValue("email", "email.formato", "El correo electrónico debe ser válido");
        }
    }

    // Validar formato del correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(emailRegex, email);
    }

    // Validar formato del teléfono (solo dígitos o con guiones)
    private boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\d{8,15}$|^(\\d{3}-\\d{3}-\\d{4})$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }
}
