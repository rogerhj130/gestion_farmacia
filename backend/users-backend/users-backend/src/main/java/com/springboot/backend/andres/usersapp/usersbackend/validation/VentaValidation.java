package com.springboot.backend.andres.usersapp.usersbackend.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;

@Component
public class VentaValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // Indica si esta clase es compatible con la validación de Venta
        return Venta.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Venta venta = (Venta) target;

        // Validación de montoTotal: No debe ser nulo ni negativo
        if (venta.getMontoTotal() == null) {
            errors.rejectValue("montoTotal", "montoTotal.null", "El monto total no puede ser nulo.");
        } else if (venta.getMontoTotal() < 0) {
            errors.rejectValue("montoTotal", "montoTotal.negative", "El monto total no puede ser negativo.");
        }

        // Validación de cantidadTotal: No debe ser nulo y debe ser un valor positivo
        if (venta.getCantidadTotal() == null) {
            errors.rejectValue("cantidadTotal", "cantidadTotal.null", "La cantidad total no puede ser nula.");
        } /* else if (venta.getCantidadTotal() <= 0) {
            errors.rejectValue("cantidadTotal", "cantidadTotal.negative", "La cantidad total debe ser un valor positivo.");
        } */
    }
}
