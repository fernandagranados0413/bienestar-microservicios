package com.bienestar.dashboardprestamosms.controller;

import com.bienestar.dashboardprestamosms.model.Implemento;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;

import java.util.Arrays;

@RestController
public class DashboardController {

    private final RestTemplate restTemplate;
    private final String implementosUrl;

    // CLAVE: la URL del proveedor llega por configuración (hostname + puerto).
    public DashboardController(RestTemplate restTemplate,
            @Value("${implementos.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.implementosUrl = baseUrl + "/api/implementos";
    }

    /**
     * Página principal: consume la API del proveedor y arma el dashboard.
     * Historias de usuario relacionadas: PD-INF-002 y PD-INV-003.
     */
    @GetMapping(value = "/", produces = "text/html;charset=UTF-8")
    public String dashboard() {
        try {
            // Llamada HTTP (interconexión REST) al microservicio de implementos
            Implemento[] datos =
                    restTemplate.getForObject(implementosUrl, Implemento[].class);
            if (datos == null) {
                datos = new Implemento[0];
            }

            int disponibles = Arrays.stream(datos)
                    .mapToInt(Implemento::disponibles).sum();
            int total = Arrays.stream(datos)
                    .mapToInt(Implemento::total).sum();

            StringBuilder html = new StringBuilder();
            html.append("<html><head><meta charset='UTF-8'>")
                .append("<title>Bienestar Universitario</title></head>")
                .append("<body style='font-family:Arial;margin:30px'>")
                .append("<h1>Dashboard de Préstamo de Implementos</h1>")
                .append("<p>Bienestar Universitario &mdash; ")
                .append(disponibles).append(" de ").append(total)
                .append(" unidades disponibles en este momento.</p>")
                .append("<table border='1' cellpadding='8' ")
                .append("style='border-collapse:collapse'>")
                .append("<tr style='background:#1F3864;color:white'>")
                .append("<th>Implemento</th><th>Deporte</th><th>Disponibles</th>")
                .append("<th>Estado</th><th>Situación</th></tr>");

            for (Implemento i : datos) {
                boolean agotado = i.disponibles() == 0;
                html.append("<tr><td>").append(HtmlUtils.htmlEscape(i.nombre()))
                    .append("</td><td>").append(HtmlUtils.htmlEscape(i.deporte()))
                    .append("</td><td>").append(i.disponibles())
                    .append(" / ").append(i.total())
                    .append("</td><td>").append(HtmlUtils.htmlEscape(i.estado()))
                    .append("</td><td style='color:")
                    .append(agotado ? "red'>Agotado" : "green'>Disponible")
                    .append("</td></tr>");
            }
            html.append("</table></body></html>");
            return html.toString();

        } catch (Exception e) {
            return "<html><body style='font-family:Arial;margin:30px'>"
                 + "<h1>Dashboard de Préstamo de Implementos</h1>"
                 + "<p style='color:red'>ERROR: el servicio de implementos "
                 + "está fuera de línea. Intenta de nuevo en unos minutos.</p>"
                 + "</body></html>";
        }
    }
}
