package com.springboot.backend.andres.usersapp.usersbackend.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacen;
import com.springboot.backend.andres.usersapp.usersbackend.entities.DetalleAlmacenId;

public interface DashboardRepository extends CrudRepository<DetalleAlmacen, DetalleAlmacenId> {
    @Query("SELECT SUM(da.stock) FROM DetalleAlmacen da")
    Integer getStockTotal();

    @Query("SELECT SUM(da.stock) FROM DetalleAlmacen da WHERE da.almacen.id = :almacenId")
    Integer getStockTotalByAlmacen(@Param("almacenId") Long almacenId);

    @Query("SELECT SUM(v.montoTotal) FROM Venta v WHERE DATE(v.fechaVenta) = CURRENT_DATE")
    Double getVentasDiarias();

    @Query("SELECT SUM(v.montoTotal) FROM Venta v WHERE WEEK(v.fechaVenta) = WEEK(CURRENT_DATE)")
    Double getVentasSemanales();

    @Query("SELECT SUM(v.montoTotal) FROM Venta v WHERE MONTH(v.fechaVenta) = MONTH(CURRENT_DATE)")
    Double getVentasMensuales();

}
