package com.springboot.backend.andres.usersapp.usersbackend.dto;

import java.util.List;

public class AlmacenMedicamentosDTO {
    private String nombreAlmacen;
    private List<MedicamentoDTO> medicamentos;

    public AlmacenMedicamentosDTO(String nombreAlmacen, List<MedicamentoDTO> medicamentos) {
        this.nombreAlmacen = nombreAlmacen;
        this.medicamentos = medicamentos;
    }

    // Getters and setters
    public String getNombreAlmacen() {
        return nombreAlmacen;
    }

    public void setNombreAlmacen(String nombreAlmacen) {
        this.nombreAlmacen = nombreAlmacen;
    }

    public List<MedicamentoDTO> getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(List<MedicamentoDTO> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public static class MedicamentoDTO {
        private String nombreMedicamento;
        private Integer stock;

        public MedicamentoDTO(String nombreMedicamento, Integer stock) {
            this.nombreMedicamento = nombreMedicamento;
            this.stock = stock;
        }

        // Getters and setters
        public String getNombreMedicamento() {
            return nombreMedicamento;
        }

        public void setNombreMedicamento(String nombreMedicamento) {
            this.nombreMedicamento = nombreMedicamento;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }
    }
}
