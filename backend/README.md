# 🚗 Cotizador de Seguros Automotores — Backend

Backend del Trabajo Final Integrador desarrollado con **Java + Spring Boot**.
Actualmente el proyecto se encuentra en su **etapa inicial**: cuenta con la estructura generada por Spring Initializr, la configuración base y el esqueleto de la aplicación listo para comenzar a incorporar la lógica de negocio.

> **Estado del proyecto:** Scaffold inicial. Todavía no hay controladores, servicios, repositorios ni modelos implementados. Este README refleja **únicamente lo que existe hoy en el repositorio**.

---

## 📋 Tabla de Contenidos

- [Estado Actual](#estado-actual)
- [Tecnologías y Dependencias](#tecnologías-y-dependencias)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Configuración Actual](#configuración-actual)
- [Cómo Ejecutar el Proyecto](#cómo-ejecutar-el-proyecto)
- [Próximos Pasos](#próximos-pasos)
- [Autores](#autores)

---

## 🛠️ Tecnologías y Dependencias

### Stack principal

| Componente | Versión / Detalle |
| :--- | :--- |
| **Lenguaje** | Java 21 (LTS) |
| **Framework** | Spring Boot 4.1.1 |
| **Gestor de dependencias** | Gradle (Groovy DSL) |
| **Base de datos** | MongoDB (NoSQL) |
| **Build Tool wrapper** | Gradle Wrapper (incluido en el repo) |

### Dependencias declaradas en `build.gradle`

| Dependencia | Tipo | Propósito |
| :--- | :--- | :--- |
| `spring-boot-starter-actuator` | implementation | Endpoints de monitoreo y health checks. |
| `spring-boot-starter-data-mongodb` | implementation | Integración con MongoDB. |
| `spring-boot-starter-validation` | implementation | Validación de DTOs (Bean Validation). |
| `spring-boot-starter-webmvc` | implementation | API REST con Spring MVC. |
| `lombok` | compileOnly + annotationProcessor | Reducción de boilerplate (getters, setters, builders). |
| `spring-boot-devtools` | developmentOnly | Reinicio automático en desarrollo. |
| `spring-boot-starter-*-test` | testImplementation | Starters de testing para cada módulo. |
| `junit-platform-launcher` | testRuntimeOnly | Ejecución de tests con JUnit 5. |

---