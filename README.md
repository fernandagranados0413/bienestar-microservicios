# PrestaDeporte: microservicios con Docker

Demo técnica del proyecto de **préstamo de balones e implementos deportivos de Bienestar Universitario**.
Dos microservicios en Java (Spring Boot) que se ejecutan en contenedores con Docker Compose.

## Problemática

El servicio de préstamo de implementos deportivos está poco aprovechado: según la encuesta realizada,
hay desconocimiento del servicio y se señalan como aspectos por mejorar la disponibilidad de los
implementos y los horarios. Esta demo implementa una versión mínima de la API REST de consulta de
disponibilidad.

## Arquitectura

| Microservicio | Puerto | Función |
|---|---|---|
| `implementos-ms` (proveedor) | 8088 | API REST con la lista de implementos y su disponibilidad (JSON) |
| `dashboard-prestamos-ms` (cliente) | 8089 | Dashboard web que consume la API del proveedor |

Los dos contenedores se comunican dentro de la red `bienestar-net`. El dashboard encuentra al proveedor
por su nombre de servicio (`implementos-bienestar`).

## Requisitos

- Java 17 o superior
- Maven 3.9 o superior
- Docker Desktop

## Ejecución

```bash
cd implementos-ms
mvn clean package
cd ..
cd dashboard-prestamos-ms
mvn clean package
cd ..
docker compose up --build -d
```

- Dashboard: http://localhost:8089/
- API: http://localhost:8088/api/implementos
- Filtro por deporte: http://localhost:8088/api/implementos?deporte=Voleibol
- Por identificador: http://localhost:8088/api/implementos/3

Para detener y eliminar los contenedores:

```bash
docker compose down
```

## Prueba de interconexión

```bash
docker compose stop implementos-bienestar    # el dashboard muestra "fuera de línea"
docker compose start implementos-bienestar   # el dashboard vuelve a mostrar los datos
```

## Estructura

```
.
├── docker-compose.yml
├── implementos-ms/            # proveedor (puerto 8088)
└── dashboard-prestamos-ms/    # cliente (puerto 8089)
```

## Relación con el Product Backlog

- API REST propia (backend): `implementos-ms`
- PD-INV-003: consulta de disponibilidad por deporte
- PD-INF-002: información del servicio en un solo lugar

Los datos son de ejemplo y están en memoria. Las reservas, los códigos QR, las notificaciones y los
reportes corresponden a los siguientes sprints del backlog.

## Autores

Fernanda Granados, Sistemas Operativos y Universidad de Cundinamarca .
