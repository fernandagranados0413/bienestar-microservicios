package com.bienestar.implementosms.controller;

import com.bienestar.implementosms.model.Implemento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ImplementoController {

    // Datos de ejemplo: en el proyecto real vendrían de una base de datos.
    private final List<Implemento> implementos = List.of(
            new Implemento(1, "Balón de voleibol", "Voleibol", 6, 10, "Bueno"),
            new Implemento(2, "Balón de baloncesto", "Baloncesto", 4, 8,
                    "Bueno"),
            new Implemento(3, "Balón de fútbol sala", "Fútbol sala", 0, 6,
                    "Regular"),
            new Implemento(4, "Kit de tenis de mesa", "Tenis de mesa", 3, 5,
                    "Excelente"));

    /**
     * Lista los implementos. Con ?deporte=Voleibol filtra por deporte.
     * Historia de usuario relacionada: PD-INV-003 (consulta de disponibilidad).
     */
    @GetMapping("/api/implementos")
    public List<Implemento> listar(
            @RequestParam(name = "deporte", required = false) String deporte) {
        if (deporte == null || deporte.isBlank()) {
            return implementos;
        }
        return implementos.stream()
                .filter(i -> i.deporte().equalsIgnoreCase(deporte))
                .toList();
    }

    /** Consulta un implemento por su id. Responde 404 si no existe. */
    @GetMapping("/api/implementos/{id}")
    public ResponseEntity<Implemento> obtener(@PathVariable("id") int id) {
        return implementos.stream()
                .filter(i -> i.id() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
