package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearDetalleVentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.CrearVentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.DetalleVentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaConTotalDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentaDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.VentasReporteDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Cliente;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacenId;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVenta;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVentaId;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Medicamento;
import com.springboot.backend.andres.usersapp.usersbackend.entities.User;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.ClienteRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DetalleAlmacenRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.MedicamentoRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.UserRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.VentaRepository;

@Service
public class VentaServiceImp implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private DetalleAlmacenRepository detalleAlmacenRepository;

    @Autowired // probando
    private ClienteRepository clienteRepository;

    @Autowired // probando
    private UserRepository usuarioRepository;

    @Override
    public List<Venta> listarTodo() {
        return (List<Venta>) ventaRepository.findAll();
    }

    @Override
    public Page<Venta> paginarTodo(Pageable pageable) {
        return this.ventaRepository.findAll(pageable);

    }

    @Override
    public Optional<Venta> buscarPorId(Long id) {
        return ventaRepository.findById(id);
    }

    @Transactional
    @Override
    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }

    @Transactional
    public List<String> crearVentaConDetalle(CrearVentaDto crearVentaDTO) {
        Venta venta = new Venta();
        double montoTotal = 0.0;
        int cantidadTotal = 0;

        // Lista para mensajes de operación
        List<String> mensajes = new ArrayList<>();

        // Asignar cliente y usuario
        Cliente cliente = clienteRepository.findById(crearVentaDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        User usuario = usuarioRepository.findById(crearVentaDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        venta.setCliente(cliente);
        venta.setUsuario(usuario);

        // Lista para verificar duplicados
        Set<Long> medicamentosProcesados = new HashSet<>();

        // Procesar detalles de la venta
        if (crearVentaDTO.getDetalleVentas() != null) {
            for (CrearDetalleVentaDto detalleDTO : crearVentaDTO.getDetalleVentas()) {
                Long medicamentoId = detalleDTO.getMedicamentoId();

                // Verificar duplicado
                if (medicamentosProcesados.contains(medicamentoId)) {
                    mensajes.add("El medicamento con ID " + medicamentoId + " ya fue procesado en esta venta.");
                    continue;
                }

                Medicamento medicamento = medicamentoRepository.findById(medicamentoId)
                        .orElseThrow(() -> new RuntimeException(
                                "El medicamento con ID " + medicamentoId + " no está registrado en la base de datos"));

                // Buscar stock en el almacén 1
                DetalleAlmacenId detalleAlmacenId1 = new DetalleAlmacenId(1L, medicamentoId);
                DetalleAlmacen detalleAlmacen1 = detalleAlmacenRepository.findById(detalleAlmacenId1)
                        .orElse(null);

                // Si no hay registro de stock
                if (detalleAlmacen1 == null || detalleAlmacen1.getStock() == 0) {
                    mensajes.add("El medicamento " + medicamento.getNombre() + " (ID: " + medicamentoId
                            + ") no tiene stock disponible.");
                    continue;
                }

                // Calcular cantidad vendida (hasta donde permita el stock)
                int cantidadDisponible = detalleAlmacen1.getStock();
                int cantidadAVender = Math.min(cantidadDisponible, detalleDTO.getCantidadTipo());

                // Actualizar stock en el almacén 1
                detalleAlmacen1.setStock(detalleAlmacen1.getStock() - cantidadAVender);

                // Si el stock queda en 0, actualizar estado a "NO DISPONIBLE"
                if (detalleAlmacen1.getStock() == 0) {
                    detalleAlmacen1.setEstado("NO DISPONIBLE");
                }
                detalleAlmacenRepository.save(detalleAlmacen1);

                // Crear detalle de venta
                DetalleVenta detalle = new DetalleVenta();
                detalle.setId(new DetalleVentaId(venta.getId(), medicamentoId)); // Crear ID compuesto
                detalle.setMedicamento(medicamento);
                detalle.setVenta(venta);
                detalle.setCantidadTipo(cantidadAVender);
                detalle.setMontoTipo(medicamento.getPrecio() * cantidadAVender);

                venta.getDetalleVentas().add(detalle);

                // Agregar a la lista de medicamentos procesados
                medicamentosProcesados.add(medicamentoId);

                // Actualizar totales
                montoTotal += detalle.getMontoTipo();
                cantidadTotal += cantidadAVender;

                // Mensaje dependiendo de la cantidad vendida
                if (cantidadAVender < detalleDTO.getCantidadTipo()) {
                    mensajes.add("Solo se vendieron " + cantidadAVender + " unidades del medicamento "
                            + medicamento.getNombre()
                            + " (ID: " + medicamentoId + ") debido a stock limitado.");
                } else {
                    mensajes.add(
                            "Se vendieron " + cantidadAVender + " unidades del medicamento " + medicamento.getNombre()
                                    + " (ID: " + medicamentoId + ").");
                }
            }
        }

        // Establecer totales
        venta.setMontoTotal(montoTotal);
        venta.setCantidadTotal(cantidadTotal);

        // Guardar venta
        guardar(venta);

        // Agregar mensaje final si no se procesaron medicamentos
        if (venta.getDetalleVentas().isEmpty()) {
            mensajes.add("No se pudo procesar la venta: ningún medicamento tenía stock disponible.");
        } else {
            mensajes.add("Venta procesada con éxito. Total vendido: " + cantidadTotal + " unidades por un monto de "
                    + montoTotal + ".");
        }

        return mensajes;
    }

    @Override
    public Optional<Venta> eliminar(Long id) {
        Optional<Venta> vOptional = ventaRepository.findById(id);
        vOptional.ifPresent(vDB -> {
            ventaRepository.delete(vDB);
        });
        return vOptional;
    }

    // --------------------------------probando------------------------
    public VentaDto convertirAVentaDTO(Venta venta) {
        List<DetalleVentaDto> detallesDTO = venta.getDetalleVentas().stream()
                .map(detalle -> new DetalleVentaDto(
                        detalle.getMedicamento().getNombre(),
                        detalle.getMedicamento().getPrecio(),
                        detalle.getCantidadTipo(),
                        detalle.getMontoTipo()))
                .toList();

        return new VentaDto(
                venta.getId(),
                venta.getMontoTotal(),
                venta.getCantidadTotal(),
                venta.getFechaVenta(),
                venta.getCliente().getNombre(),
                venta.getUsuario().getName(),
                detallesDTO);

    }

    /*
     * private void reabastecerAlmacen(DetalleAlmacen detalleAlmacen1, Long
     * medicamentoId) {
     * // Buscar stock en el almacén 2
     * DetalleAlmacenId detalleAlmacenId2 = new DetalleAlmacenId(2L, medicamentoId);
     * DetalleAlmacen detalleAlmacen2 =
     * detalleAlmacenRepository.findById(detalleAlmacenId2)
     * .orElseThrow(() -> new RuntimeException(
     * "No hay stock disponible para el medicamento con ID: " + medicamentoId +
     * " en el almacén 2"));
     * 
     * // Determinar la cantidad a reabastecer (50 unidades)
     * int cantidadReabastecer = 50;
     * 
     * // Verificar si hay suficiente stock en el almacén 2
     * if (detalleAlmacen2.getStock() >= cantidadReabastecer) {
     * // Transferir 50 unidades del almacén 2 al almacén 1
     * detalleAlmacen1.setStock(detalleAlmacen1.getStock() + cantidadReabastecer);
     * detalleAlmacen2.setStock(detalleAlmacen2.getStock() - cantidadReabastecer);
     * detalleAlmacen1.setEstado("DISPONIBLE"); // Actualizamos el estado
     * if (detalleAlmacen2.getStock() == 0) {
     * detalleAlmacen2.setEstado("NO DISPONIBLE");
     * }
     * } else if (detalleAlmacen2.getStock() > 0) {
     * // Si hay algo de stock pero no suficiente, transferir todo lo que quede
     * detalleAlmacen1.setStock(detalleAlmacen1.getStock() +
     * detalleAlmacen2.getStock());
     * detalleAlmacen2.setStock(0);
     * detalleAlmacen1.setEstado("DISPONIBLE");
     * } else {
     * // Si no hay nada de stock en el almacén 2
     * detalleAlmacen1.setEstado("NO DISPONIBLE");
     * }
     * 
     * // Guardar cambios en ambos almacenes
     * detalleAlmacenRepository.save(detalleAlmacen1);
     * detalleAlmacenRepository.save(detalleAlmacen2);
     * }
     */

    @Transactional
    public VentasReporteDto getVentasPorUsuarioYFecha(Long usuarioId, LocalDate fecha) {
        // Convertir la fecha a LocalDateTime para el inicio y final del día
        LocalDateTime fechaInicio = fecha.atStartOfDay(); // 00:00:00
        LocalDateTime fechaFin = fecha.atTime(23, 59, 59, 999999); // 23:59:59

        // Obtener las ventas para ese usuario y esa fecha, considerando el rango
        // completo del día
        List<Venta> ventas = ventaRepository.findByUsuarioIdAndFechaVentaBetween(usuarioId, fechaInicio, fechaFin);

        // Log para verificar las ventas obtenidas
        System.out.println("Ventas encontradas: " + ventas.size());

        // Obtener el nombre del usuario (opcionalmente usando un repositorio de
        // usuarios)
        String usuarioNombre = usuarioRepository.findById(usuarioId)
                .map(User::getName) // Suponiendo que Usuario tiene un método getNombre()
                .orElse("Usuario no encontrado");

        // Si no se encuentran ventas, devolver un objeto vacío
        if (ventas.isEmpty()) {
            return new VentasReporteDto(usuarioNombre, 0.0, new ArrayList<>());
        }

        // Sumar el monto total de todas las ventas para ese usuario en esa fecha
        Double totalVendido = ventas.stream()
                .mapToDouble(Venta::getMontoTotal)
                .sum();

        // Crear una lista de objetos VentaConTotalDto con los detalles de las ventas
        List<VentaConTotalDto> ventasDetalles = ventas.stream()
                .map(venta -> new VentaConTotalDto(
                        venta.getId(),
                        venta.getMontoTotal()))
                .collect(Collectors.toList());

        // Retornar el nombre del usuario, el total vendido y la lista de ventas
        return new VentasReporteDto(usuarioNombre, totalVendido, ventasDetalles);
    }

    @Transactional
    public VentasReporteDto getVentasPorUsuarioYRangoFechas(Long usuarioId, LocalDate fechaInicio, LocalDate fechaFin) {
        // Convertir las fechas a LocalDateTime
        LocalDateTime fechaInicioDateTime = fechaInicio.atStartOfDay();
        LocalDateTime fechaFinDateTime = fechaFin.atTime(23, 59, 59, 999999);

        // Obtener las ventas del usuario en el rango de fechas
        List<Venta> ventas = ventaRepository.findByUsuarioIdAndFechaVentaBetween(usuarioId, fechaInicioDateTime,
                fechaFinDateTime);

        // Log para verificar las ventas obtenidas
        System.out.println("Ventas encontradas: " + ventas.size());

        // Si no se encuentran ventas, devolver un objeto vacío
        if (ventas.isEmpty()) {
            return new VentasReporteDto(null, 0.0, new ArrayList<>());
        }

        // Obtener el nombre del usuario (opcionalmente usando un repositorio de
        // usuarios)
        String usuarioNombre = usuarioRepository.findById(usuarioId)
                .map(User::getName) // Suponiendo que Usuario tiene un método getNombre()
                .orElse("Usuario no encontrado");

        // Calcular el monto total de las ventas
        Double totalVendido = ventas.stream()
                .mapToDouble(Venta::getMontoTotal)
                .sum();

        // Crear una lista de VentaConTotalDto con los detalles de las ventas
        List<VentaConTotalDto> ventasDetalles = ventas.stream()
                .map(venta -> new VentaConTotalDto(
                        venta.getId(),
                        venta.getMontoTotal()))
                .collect(Collectors.toList());

        // Retornar el nombre del usuario, el total de las ventas y los detalles
        return new VentasReporteDto(usuarioNombre, totalVendido, ventasDetalles);
    }

}
