package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacenId;

public interface DetalleAlmacenRepository extends CrudRepository<DetalleAlmacen, DetalleAlmacenId> {
    @Query("SELECT d FROM DetalleAlmacen d WHERE d.medicamento.id = :medicamentoId")
    List<DetalleAlmacen> findAllByMedicamentoId(@Param("medicamentoId") Long medicamentoId);

    @Query("SELECT d FROM DetalleAlmacen d WHERE d.almacen.id = :almacenId")
    List<DetalleAlmacen> findByAlmacenId(@Param("almacenId") Long almacenId);

}
