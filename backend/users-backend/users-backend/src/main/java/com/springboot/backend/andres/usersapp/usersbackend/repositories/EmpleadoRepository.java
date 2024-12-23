package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Long>{
    Page<Empleado> findAll(Pageable pageable);
}
