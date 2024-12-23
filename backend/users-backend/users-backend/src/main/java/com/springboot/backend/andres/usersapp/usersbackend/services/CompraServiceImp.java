package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.dto.CompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearCompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearDetalleCompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.DetalleCompraDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Compra;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacenId;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleCompra;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleCompraId;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Proveedor;
import com.springboot.backend.andres.usersapp.usersbackend.entities.User;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.CompraRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DetalleAlmacenRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.MedicamentoRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.ProveedorRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.UserRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.AlmacenRepository;

@Service
public class CompraServiceImp implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private UserRepository usuarioRepository;

    @Autowired
    private AlmacenRepository almacenRepository;

    @Override
    public List<Compra> listarTodo() {
        return (List<Compra>) compraRepository.findAll();
    }

    @Override
    public Page<Compra> paginarTodo(Pageable pageable) {
        return this.compraRepository.findAll(pageable);

    }

    @Override
    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    @Override
    public Compra guardar(Compra compra) {
        return compraRepository.save(compra);
    }

    @Transactional
    public Compra crearCompraConDetalle(CrearCompraDto crearCompraDTO) {
        Compra compra = new Compra();
        double montoTotal = 0.0;
        int cantidadTotal = 0;

        // Lista de mensajes para los medicamentos no encontrados
        List<String> mensajesNoRegistrados = new ArrayList<>();

        // Asignar proveedor y usuario
        Proveedor proveedor = proveedorRepository.findById(crearCompraDTO.getProveedorId())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
        User usuario = usuarioRepository.findById(crearCompraDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        compra.setProveedor(proveedor);
        compra.setUsuario(usuario);

        // Lista para verificar duplicados de medicamentos
        Set<Long> medicamentosProcesados = new HashSet<>();

        // Procesar detalles de la compra
        if (crearCompraDTO.getDetalleCompras() != null) {
            for (CrearDetalleCompraDto detalleDTO : crearCompraDTO.getDetalleCompras()) {
                Long medicamentoId = detalleDTO.getMedicamentoId();

                // Verificar duplicado
                if (medicamentosProcesados.contains(medicamentoId)) {
                    throw new RuntimeException(
                            "El medicamento con ID: " + medicamentoId + " ya está incluido en la compra");
                }

                // Buscar medicamento en la base de datos
                Optional<Medicamento> medicamentoOpt = medicamentoRepository.findById(medicamentoId);
                if (!medicamentoOpt.isPresent()) {
                    // Si no está registrado, agregar mensaje y continuar con el siguiente
                    // medicamento
                    mensajesNoRegistrados.add("El medicamento con ID: " + medicamentoId + " no está registrado.");
                    continue;
                }

                Medicamento medicamento = medicamentoOpt.get();

                // Buscar el medicamento en el Almacén 2
                DetalleAlmacenId detalleAlmacenId = new DetalleAlmacenId(2L, medicamentoId);
                Optional<DetalleAlmacen> detalleAlmacenOpt = detalleAlmacenRepository.findById(detalleAlmacenId);

                DetalleAlmacen detalleAlmacen;
                if (detalleAlmacenOpt.isPresent()) {
                    // Si está registrado, actualizar el stock en el almacén 2
                    detalleAlmacen = detalleAlmacenOpt.get();
                    detalleAlmacen.setStock(detalleAlmacen.getStock() + detalleDTO.getCantidadTipo());
                    detalleAlmacen.setEstado("DISPONIBLE");
                } else {
                    // Si no está registrado en el almacén 2, crearlo automáticamente
                    detalleAlmacen = new DetalleAlmacen();
                    detalleAlmacen.setId(detalleAlmacenId);
                    detalleAlmacen.setMedicamento(medicamento);
                    detalleAlmacen.setAlmacen(almacenRepository.findById(2L)
                            .orElseThrow(() -> new RuntimeException("Almacén 2 no encontrado")));
                    detalleAlmacen.setStock(detalleDTO.getCantidadTipo());
                    detalleAlmacen.setEstado("DISPONIBLE");
                }

                detalleAlmacenRepository.save(detalleAlmacen); // Guardar el cambio en el almacén 2

                // Crear detalle de compra
                DetalleCompra detalle = new DetalleCompra();
                detalle.setId(new DetalleCompraId(compra.getId(), medicamentoId)); // Crear ID compuesto
                detalle.setMedicamento(medicamento);
                detalle.setCompra(compra);
                detalle.setCantidadTipo(detalleDTO.getCantidadTipo());
                detalle.setMontoTipo(medicamento.getPrecio() * detalleDTO.getCantidadTipo());

                compra.getDetalleCompras().add(detalle);

                // Agregar a la lista de medicamentos procesados
                medicamentosProcesados.add(medicamentoId);

                montoTotal += detalle.getMontoTipo();
                cantidadTotal += detalleDTO.getCantidadTipo();
            }
        }

        // Establecer totales
        compra.setMontoTotal(montoTotal);
        compra.setCantidadTotal(cantidadTotal);

        // Guardar compra
        Compra compraGuardada = compraRepository.save(compra);

        // Mostrar los mensajes de medicamentos no registrados
        if (!mensajesNoRegistrados.isEmpty()) {
            System.out.println("Medicamentos no registrados:");
            mensajesNoRegistrados.forEach(System.out::println);
        }

        return compraGuardada;
    }

    @Override
    public Optional<Compra> eliminar(Long id) {
        Optional<Compra> cOptional = compraRepository.findById(id);
        cOptional.ifPresent(cDB -> {
            compraRepository.delete(cDB);
        });
        return cOptional;
    }

    public CompraDto convertirACompraDTO(Compra compra) {
        List<DetalleCompraDto> detallesDTO = compra.getDetalleCompras().stream()
                .map(detalle -> new DetalleCompraDto(
                        detalle.getMedicamento().getNombre(), // Solo el nombre del medicamento
                        detalle.getMedicamento().getPrecio(), // Precio del medicamento
                        detalle.getCantidadTipo(), // Cantidad comprada
                        detalle.getMontoTipo())) // Monto total del detalle
                .collect(Collectors.toList());

        return new CompraDto(
                compra.getId(),
                compra.getMontoTotal(),
                compra.getCantidadTotal(),
                compra.getFechaCompra(),
                compra.getProveedor().getNombre(), // Solo el nombre del proveedor
                compra.getUsuario().getName(), // Solo el nombre del usuario
                detallesDTO);
    }

}
