package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.backend.andres.usersapp.usersbackend.dto.MedicamentoReporteDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleVenta;
import com.springboot.backend.andres.usersapp.usersbackend.entities.Venta;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.DashboardRepository;
import com.springboot.backend.andres.usersapp.usersbackend.repositories.VentaRepository;

@Service
public class DashboardServiceImp implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Autowired
    private VentaRepository ventaRepository;

    public Integer getTotalStock() {
        return dashboardRepository.getStockTotal();
    }

    public Integer getStockByAlmacen(Long almacenId) {
        Integer stock = dashboardRepository.getStockTotalByAlmacen(almacenId);
        return stock != null ? stock : 0;
    }

    public Double getVentasDiarias() {
        Double ventasDiarias = dashboardRepository.getVentasDiarias();
        return ventasDiarias != null ? ventasDiarias : 0.0;
    }

    public Double getVentasSemanales() {
        Double ventasSemanales = dashboardRepository.getVentasSemanales();
        return ventasSemanales != null ? ventasSemanales : 0.0;
    }

    public Double getVentasMensuales() {
        Double ventasMensuales = dashboardRepository.getVentasMensuales();
        return ventasMensuales != null ? ventasMensuales : 0.0;
    }

    public Double getVentasPorFecha(LocalDate fecha) {
        List<Venta> ventas = (List<Venta>) ventaRepository.findAll();

        return ventas.stream()
                .filter(venta -> venta.getFechaVenta().toLocalDate().isEqual(fecha))
                .mapToDouble(Venta::getMontoTotal)
                .sum();
    }

    @Transactional
    public List<MedicamentoReporteDto> getTopMedicamentosVendidosEnSemana(LocalDate startDate, LocalDate endDate) {
        // Obtener ventas en el rango de fechas
        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay());

        // Mapa para acumular cantidades por medicamento
        Map<String, Integer> medicamentosCantidad = new HashMap<>();

        // Procesar detalles de las ventas
        for (Venta venta : ventas) {
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                medicamentosCantidad.merge(
                        detalle.getMedicamento().getNombre(),
                        detalle.getCantidadTipo(),
                        Integer::sum);
            }
        }

        // Ordenar y devolver los 5 medicamentos más vendidos
        return medicamentosCantidad.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(5)
                .map(entry -> new MedicamentoReporteDto(entry.getKey(), entry.getValue())) // Mapear a DTO
                .collect(Collectors.toList());
    }

}
