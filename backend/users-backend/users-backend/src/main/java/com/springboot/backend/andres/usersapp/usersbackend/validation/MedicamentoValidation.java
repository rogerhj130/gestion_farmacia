package com.springboot.backend.andres.usersapp.usersbackend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;

import java.util.Date;

@Component
public class MedicamentoValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // Indica si esta clase es compatible con la validación de Medicamento
        return Medicamento.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Medicamento medicamento = (Medicamento) target;

        // Validación de nombre: No debe ser vacío ni tener menos de 3 caracteres
        if (medicamento.getNombre() == null || medicamento.getNombre().trim().isEmpty()) {
            errors.rejectValue("nombre", "nombre.empty", "El nombre del medicamento no puede estar vacío.");
        } else if (medicamento.getNombre().length() < 3) {
            errors.rejectValue("nombre", "nombre.short", "El nombre del medicamento debe tener al menos 3 caracteres.");
        }

        // Validación de precio: No puede ser nulo ni negativo
        if (medicamento.getPrecio() == null) {
            errors.rejectValue("precio", "precio.null", "El precio no puede ser nulo.");
        } else if (medicamento.getPrecio() < 0) {
            errors.rejectValue("precio", "precio.negative", "El precio no puede ser negativo.");
        }

        // Validación de fecha de vencimiento: No puede ser nula y debe ser una fecha futura
        if (medicamento.getFechaVencimiento() == null) {
            errors.rejectValue("fechaVencimiento", "fechaVencimiento.null", "La fecha de vencimiento no puede ser nula.");
        } else if (medicamento.getFechaVencimiento().before(new Date())) {
            errors.rejectValue("fechaVencimiento", "fechaVencimiento.past", "La fecha de vencimiento debe ser futura.");
        }

        // Validación de vía de administración: No puede ser nula ni vacía
        if (medicamento.getViaAdministracion() == null || medicamento.getViaAdministracion().trim().isEmpty()) {
            errors.rejectValue("viaAdministracion", "viaAdministracion.empty", "La vía de administración no puede estar vacía.");
        }

        // Validación de categoría: No puede ser nula ni vacía
        if (medicamento.getCategoria() == null || medicamento.getCategoria().trim().isEmpty()) {
            errors.rejectValue("categoria", "categoria.empty", "La categoría no puede estar vacía.");
        }
    }
}
