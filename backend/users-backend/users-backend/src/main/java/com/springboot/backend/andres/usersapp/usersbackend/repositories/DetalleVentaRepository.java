package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVenta;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVentaId;

public interface DetalleVentaRepository extends CrudRepository<DetalleVenta, DetalleVentaId> {

}
