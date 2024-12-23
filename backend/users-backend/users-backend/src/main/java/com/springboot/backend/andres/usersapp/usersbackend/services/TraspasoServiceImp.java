package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Almacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Traspaso;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.AlmacenRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.TraspasoRepository;

@Service
public class TraspasoServiceImp implements TraspasoService {

    @Autowired
    private TraspasoRepository traspasoRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    

    @Override
    public List<Traspaso> listarTodo() {
        return (List<Traspaso>) traspasoRepository.findAll();
    }

    @Override
    public Page<Traspaso> paginarTodo(Pageable pageable) {
        return traspasoRepository.findAll(pageable);
    }

    @Override
    public Optional<Traspaso> buscarPorId(Long id) {
        return traspasoRepository.findById(id);
    }

    @Override
    public Traspaso guardar(Traspaso traspaso) {
        
        // Validar que el origen y destino existen en la base de datos
        if (traspaso.getAlmacenOrigen() == null || traspaso.getAlmacenDestino() == null) {
            throw new IllegalArgumentException("Almacén Origen o Destino no puede ser null");
        }

        // Buscar los almacenes en la base de datos
        Almacen origen = almacenRepository.findById(traspaso.getAlmacenOrigen().getId())
            .orElseThrow(() -> new IllegalArgumentException("Almacén Origen no encontrado"));
        
        Almacen destino = almacenRepository.findById(traspaso.getAlmacenDestino().getId())
            .orElseThrow(() -> new IllegalArgumentException("Almacén Destino no encontrado"));

        traspaso.setAlmacenOrigen(origen);
        traspaso.setAlmacenDestino(destino);

        // Guardar el traspaso con sus detalles
        return traspasoRepository.save(traspaso);
    }

    @Override
    public Optional<Traspaso> actualizar(Long id, Traspaso traspaso) {
        return traspasoRepository.findById(id).map(existingTraspaso -> {
            // Actualizar los campos necesarios
            existingTraspaso.setFechaTraspaso(traspaso.getFechaTraspaso());
            existingTraspaso.setAlmacenOrigen(traspaso.getAlmacenOrigen());
            existingTraspaso.setAlmacenDestino(traspaso.getAlmacenDestino());
            return traspasoRepository.save(existingTraspaso);
        });
    }

    @Override
    public Optional<Traspaso> eliminar(Long id) {
        return traspasoRepository.findById(id).map(traspaso -> {
            traspasoRepository.delete(traspaso);
            return traspaso;
        });
    }
}
