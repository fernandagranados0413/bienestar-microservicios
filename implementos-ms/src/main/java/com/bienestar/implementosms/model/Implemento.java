package com.bienestar.implementosms.model;

/**
 * Representa un implemento deportivo que presta Bienestar Universitario.
 * Un "record" de Java 17 es una clase inmutable que solo guarda datos.
 */
public record Implemento(
        int id,
        String nombre,
        String deporte,
        int disponibles,
        int total,
        String estado) {
}
