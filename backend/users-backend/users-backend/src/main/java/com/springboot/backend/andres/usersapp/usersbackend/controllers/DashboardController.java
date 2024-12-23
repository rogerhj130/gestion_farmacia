package com.springboot.backend.andres.usersapp.usersbackend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.backend.andres.usersapp.usersbackend.dto.MedicamentoReporteDto;
import com.springboot.backend.andres.usersapp.usersbackend.dto.RangoFechasDto;
import com.springboot.backend.andres.usersapp.usersbackend.entities.FechaRequest;
import com.springboot.backend.andres.usersapp.usersbackend.services.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/total-stock")
    public ResponseEntity<Integer> getTotalStock() {
        Integer totalStock = dashboardService.getTotalStock();
        return ResponseEntity.ok(totalStock);
    }

    @GetMapping("/stock-por-almacen/{almacenId}")
    public ResponseEntity<Integer> getStockByAlmacen(@PathVariable Long almacenId) {
        Integer stock = dashboardService.getStockByAlmacen(almacenId);
        return ResponseEntity.ok(stock);
    }

    // te muestra el monto total vendido del dia automaticamente
    // sin que pongas nada en postman, solo colocas este endpoint
    @GetMapping("/ventas-diarias")
    public Double getVentasDiarias() {
        return dashboardService.getVentasDiarias();
    }

    // te muestra el monto total vendido dela semana automaticamente
    // sin que pongas nada en postman, solo colocas este endpoint
    @GetMapping("/ventas-semanales")
    public Double getVentasSemanales() {
        return dashboardService.getVentasSemanales();
    }

    // te muestra el monto total vendido del mes automaticamente
    // sin que pongas nada en postman, solo colocas este endpoint
    @GetMapping("/ventas-mensuales")
    public Double getVentasMensuales() {
        return dashboardService.getVentasMensuales();
    }

    // te muestra el monto total vendido en una fecha especifica
    // tenes que colocar una fecha especifica
    // para que te muestre el monto total vendido en ese dia
    @PostMapping("/ventas-especifica")
    public Double getVentaEspecfica(@RequestBody FechaRequest fechaRequest) {
        return dashboardService.getVentasPorFecha(fechaRequest.getFecha());
    }

    @PostMapping("/medicamentos-mas-vendidos-semana")
    public ResponseEntity<List<MedicamentoReporteDto>> getTopMedicamentosVendidosSemana(@RequestBody RangoFechasDto rangoFechas) {
        List<MedicamentoReporteDto> reporte = dashboardService.getTopMedicamentosVendidosEnSemana(
                rangoFechas.getFechaInicio(), rangoFechas.getFechaFin());
        return ResponseEntity.ok(reporte);
    }

}
