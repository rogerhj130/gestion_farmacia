package com.springboot.backend.andres.usersapp.usersbackend.services;

import java.time.LocalDate;
import java.util.List;

import com.springboot.backend.andres.usersapp.usersbackend.dto.MedicamentoReporteDto;

public interface DashboardService {
    Integer getTotalStock();
    Integer getStockByAlmacen(Long almacenId);
    Double getVentasDiarias();
    Double getVentasSemanales();
    Double getVentasMensuales();
    Double getVentasPorFecha(LocalDate fecha);
    List<MedicamentoReporteDto> getTopMedicamentosVendidosEnSemana(LocalDate startDate, LocalDate endDate);

}
