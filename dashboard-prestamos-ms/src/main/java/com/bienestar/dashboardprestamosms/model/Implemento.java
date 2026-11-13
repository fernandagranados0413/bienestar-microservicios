package com.bienestar.dashboardprestamosms.model;

/**
 * Copia de la estructura que publica el proveedor. En microservicios cada
 * servicio define su propia clase: no se comparte código entre ellos.
 */
public record Implemento(
        int id,
        String nombre,
        String deporte,
        int disponibles,
        int total,
        String estado) {
}
