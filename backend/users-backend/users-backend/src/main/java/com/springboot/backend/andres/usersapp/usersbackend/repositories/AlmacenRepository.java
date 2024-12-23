package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Almacen;

public interface AlmacenRepository extends CrudRepository<Almacen, Long>{
    Page<Almacen> findAll(Pageable pageable);
    boolean existsByNombre(String nombre);
}
