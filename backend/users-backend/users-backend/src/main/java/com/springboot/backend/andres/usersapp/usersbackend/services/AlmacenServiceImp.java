package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.dto.AlmacenMedicamentosDTO;
import com.springboot.backend.andres.usersapp.usersbackend.dto.TraspasoRequest;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Almacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacenId;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.AlmacenRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DetalleAlmacenRepository;

@Service
public class AlmacenServiceImp implements AlmacenService {

    @Autowired
    private AlmacenRepository almacenRepository;

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Almacen> listarTodo() {
        return (List<Almacen>) almacenRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Almacen> paginarTodo(Pageable pageable) {
        return this.almacenRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Almacen> buscarPorId(Long id) {
        return almacenRepository.findById(id);
    }

    @Transactional
    @Override
    public Almacen guardar(Almacen almacen) {
        if (existeElAlmacen(almacen.getNombre())) {
            throw new RuntimeException(
                    "El almacen con nombre: " + almacen.getNombre() + " ya existe en la base de datos");
        }
        return almacenRepository.save(almacen);
    }

    @Transactional
    @Override
    public Optional<Almacen> actualizar(Long id, Almacen almacen) {
        Optional<Almacen> productOptional = almacenRepository.findById(id);
        if (productOptional.isPresent()) {
            Almacen almacenDB = productOptional.orElseThrow();

            // Actualizar todos los campos
            almacenDB.setNombre(almacen.getNombre());
            almacenDB.setDireccion(almacen.getDireccion());

            // Guardar los cambios en la base de datos
            return Optional.of(almacenRepository.save(almacenDB));
        }
        return productOptional;
    }

    @Transactional
    @Override
    public Optional<Almacen> eliminar(Long id) {
        Optional<Almacen> aOptional = almacenRepository.findById(id);
        aOptional.ifPresent(alDB -> {
            almacenRepository.delete(alDB);
        });
        return aOptional;
    }

    @Transactional
    public List<String> traspasarMedicamentos(List<TraspasoRequest> traspasos) {
        List<String> mensajes = new ArrayList<>();

        // Buscar los dos almacenes por ID
        Almacen almacen1 = almacenRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Almacén 1 no encontrado."));
        Almacen almacen2 = almacenRepository.findById(2L)
                .orElseThrow(() -> new RuntimeException("Almacén 2 no encontrado."));

        for (TraspasoRequest traspaso : traspasos) {
            Long medicamentoId = traspaso.getMedicamentoId();
            Integer cantidadSolicitada = traspaso.getCantidad();

            try {
                // Buscar el medicamento en el almacén 2
                DetalleAlmacen detalleAlmacen2 = detalleAlmacenRepository
                        .findById(new DetalleAlmacenId(2L, medicamentoId))
                        .orElseThrow(() -> new RuntimeException(
                                "El medicamento con ID " + medicamentoId + " no existe en el almacén 2."));

                // Determinar la cantidad a traspasar (lo que haya disponible si es menor a la
                // solicitada)
                int cantidadDisponible = detalleAlmacen2.getStock();
                int cantidadATraspasar = Math.min(cantidadSolicitada, cantidadDisponible);

                if (cantidadATraspasar <= 0) {
                    throw new RuntimeException("No hay stock disponible del medicamento con ID " + medicamentoId
                            + " en el almacén 2.");
                }

                // Buscar el medicamento en el almacén 1
                DetalleAlmacen detalleAlmacen1 = detalleAlmacenRepository
                        .findById(new DetalleAlmacenId(1L, medicamentoId))
                        .orElse(null);

                // Si el medicamento no existe en el almacén 1, crearlo
                if (detalleAlmacen1 == null) {
                    detalleAlmacen1 = new DetalleAlmacen();
                    detalleAlmacen1.setId(new DetalleAlmacenId(1L, medicamentoId));
                    detalleAlmacen1.setStock(0); // Inicialmente sin stock
                    detalleAlmacen1.setAlmacen(almacen1);
                    detalleAlmacen1.setMedicamento(detalleAlmacen2.getMedicamento());
                    detalleAlmacen1.setEstado("DISPONIBLE");
                }

                // Actualizar el stock en ambos almacenes
                detalleAlmacen2.setStock(cantidadDisponible - cantidadATraspasar);
                detalleAlmacen1.setStock(detalleAlmacen1.getStock() + cantidadATraspasar);

                // Si el stock del almacén 2 llega a 0, cambiar su estado a "NO DISPONIBLE"
                if (detalleAlmacen2.getStock() == 0) {
                    detalleAlmacen2.setEstado("NO DISPONIBLE");
                }

                // Si hay stock disponible en el almacén 1, asegurarse de que su estado sea
                // "DISPONIBLE"
                if (detalleAlmacen1.getStock() > 0) {
                    detalleAlmacen1.setEstado("DISPONIBLE");
                }

                // Guardar los cambios
                detalleAlmacenRepository.save(detalleAlmacen2);
                detalleAlmacenRepository.save(detalleAlmacen1);

                // Agregar mensaje de éxito
                Medicamento medicamento = detalleAlmacen2.getMedicamento();
                mensajes.add("Se traspasaron " + cantidadATraspasar + " unidades del medicamento "
                        + medicamento.getNombre() + " (ID: " + medicamentoId + ") de la BODEGA al ESTANTE.");
            } catch (RuntimeException e) {
                // Capturar errores específicos de cada traspaso y agregar al mensaje
                mensajes.add("Error con el medicamento ID " + medicamentoId + ": " + e.getMessage());
            }
        }

        return mensajes;
    }

    public boolean existeElAlmacen(String almacenNombre) {
        return almacenRepository.existsByNombre(almacenNombre);
    }

    @Transactional(readOnly = true)
    public AlmacenMedicamentosDTO listarMedicamentosPorAlmacen(Long almacenId) {
        // Buscar el almacén por su ID
        Almacen almacen = almacenRepository.findById(almacenId)
                .orElseThrow(() -> new RuntimeException("Almacén no encontrado con ID: " + almacenId));

        // Convertir los DetalleAlmacen a MedicamentoDTO
        List<AlmacenMedicamentosDTO.MedicamentoDTO> medicamentosDTO = new ArrayList<>();
        for (DetalleAlmacen detalle : almacen.getDetalleAlmacenes()) {
            Medicamento medicamento = detalle.getMedicamento();
            medicamentosDTO.add(new AlmacenMedicamentosDTO.MedicamentoDTO(medicamento.getNombre(), detalle.getStock()));
        }

        // Retornar el DTO con el nombre del almacén y la lista de medicamentos con su
        // stock
        return new AlmacenMedicamentosDTO(almacen.getNombre(), medicamentosDTO);
    }
}
