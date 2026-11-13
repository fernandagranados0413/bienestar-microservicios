# Wellness Microservices: sports equipment loans with Docker

Technical demo for the **sports equipment loan service of University Wellness**
(balls and other sports equipment). Two Java (Spring Boot) microservices run in
containers orchestrated with Docker Compose.

## Problem

The equipment loan service is under-used. According to the survey carried out
for the project, many students do not know the service, and they point to
equipment availability and opening hours as aspects to improve. This demo
implements a minimal version of the REST API for availability lookups.

## Architecture

| Microservice | Port | Role |
|---|---|---|
| `equipment-ms` (provider) | 8088 | REST API with the equipment list and availability (JSON) |
| `loans-dashboard-ms` (client) | 8089 | Web dashboard that consumes the provider API |

Both containers talk to each other inside the `wellness-net` network. The
dashboard finds the provider by its service name (`equipment-service`).

## Requirements

- Java 17 or higher
- Maven 3.9 or higher
- Docker Desktop

## Run

```bash
cd equipment-ms
mvn clean package
cd ..
cd loans-dashboard-ms
mvn clean package
cd ..
docker compose up --build -d
```

- Dashboard: http://localhost:8089/
- API: http://localhost:8088/api/equipment
- Filter by sport: http://localhost:8088/api/equipment?sport=Volleyball
- By id: http://localhost:8088/api/equipment/3

To stop and remove the containers:

```bash
docker compose down
```

## Interconnection test

```bash
docker compose stop equipment-service    # the dashboard shows "offline"
docker compose start equipment-service   # the dashboard shows the data again
```

## Structure

```
.
├── docker-compose.yml
├── equipment-ms/          # provider (port 8088)
└── loans-dashboard-ms/    # client (port 8089)
```

## Relation to the Product Backlog

- Own REST API (backend): `equipment-ms`
- PD-INV-003: availability lookup by sport
- PD-INF-002: service information in a single place

The data is sample data kept in memory. Reservations, QR codes, notifications
and reports belong to the next sprints of the backlog.

## Authors

Fernanda Granados, Operating Systems, Universidad de Cundinamarca.
