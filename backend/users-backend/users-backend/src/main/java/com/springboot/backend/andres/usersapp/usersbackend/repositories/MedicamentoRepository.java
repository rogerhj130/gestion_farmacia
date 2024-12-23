package com.springboot.backend.andres.usersapp.usersbackend.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;

public interface MedicamentoRepository extends CrudRepository<Medicamento, Long> {
    Page<Medicamento> findAll(Pageable pageable);
}
