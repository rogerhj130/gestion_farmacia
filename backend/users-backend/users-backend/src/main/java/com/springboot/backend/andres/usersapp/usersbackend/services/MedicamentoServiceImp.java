package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.dto.MedicamentoStockDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DetalleAlmacenRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.MedicamentoRepository;

@Service
public class MedicamentoServiceImp implements MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;
    
    @Transactional(readOnly = true)
    @Override
    public List<Medicamento> listarTodo() {
        return (List<Medicamento>) medicamentoRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Medicamento> paginarTodo(Pageable pageable) {
        return this.medicamentoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Medicamento> buscarPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Transactional
    @Override
    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Transactional
    @Override
    public Optional<Medicamento> actualizar(Long id, Medicamento medicamento) {
        Optional<Medicamento> productOptional = medicamentoRepository.findById(id);
        if (productOptional.isPresent()) {
            Medicamento medicamentDb = productOptional.orElseThrow();

            // Actualizar todos los campos
            medicamentDb.setNombre(medicamento.getNombre());
            medicamentDb.setPrecio(medicamento.getPrecio());
            medicamentDb.setFechaVencimiento(medicamento.getFechaVencimiento());
            medicamentDb.setViaAdministracion(medicamento.getViaAdministracion());
            medicamentDb.setCategoria(medicamento.getCategoria());

            // Guardar los cambios en la base de datos
            return Optional.of(medicamentoRepository.save(medicamentDb));
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Medicamento> eliminar(Long id) {
        Optional<Medicamento> mOptional = medicamentoRepository.findById(id);
        mOptional.ifPresent(medDB -> {
            medicamentoRepository.delete(medDB);
        });
        return mOptional;
    }

    @Transactional(readOnly = true)
    public List<MedicamentoStockDto> listarMedicamentosConStockAlmacen1() {
        List<DetalleAlmacen> detalles = detalleAlmacenRepository.findByAlmacenId(1L);

        return detalles.stream()
                .map(detalle -> new MedicamentoStockDto(
                        detalle.getMedicamento().getId(),
                        detalle.getMedicamento().getNombre(),
                        detalle.getStock()))
                .collect(Collectors.toList());
    }
}
