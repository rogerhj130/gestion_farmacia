package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.entities.Empleado;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.EmpleadoRepository;

@Service
public class EmpleadoServiceImp  implements EmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Transactional(readOnly = true)
    @Override
    public List<Empleado> listarTodo() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Empleado> paginarTodo(Pageable pageable) {
        return this.empleadoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Empleado> buscarPorId(Long id) {
       return empleadoRepository.findById(id);
    }

    @Transactional
    @Override
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Transactional
    @Override
    public Optional<Empleado> eliminar(Long id) {
        Optional<Empleado> eOptional = empleadoRepository.findById(id);
        eOptional.ifPresent(empDB -> {
            empleadoRepository.delete(empDB);
        });
        return eOptional;
    }

    @Transactional
    @Override
    public Optional<Empleado> actualizar(Long id, Empleado empleado) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(id);
        if (empleadoOptional.isPresent()) {
            Empleado empleadoDb = empleadoOptional.orElseThrow();
    
            // Actualizar todos los campos
            empleadoDb.setNombre(empleado.getNombre());
            empleadoDb.setMaterno(empleado.getMaterno());
            empleadoDb.setPaterno(empleado.getPaterno());
            empleadoDb.setTelefono(empleado.getTelefono());
    
            return Optional.of(empleadoRepository.save(empleadoDb));
        }
        return empleadoOptional;
    }
}
