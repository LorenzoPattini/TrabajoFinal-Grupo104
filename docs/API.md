# API REST — Cotizador de Seguros Automotores

## Estado actual

El backend del Trabajo Final Integrador se encuentra
en su etapa inicial de desarrollo.

Actualmente, el proyecto contiene la configuración
base de Spring Boot y MongoDB, pero todavía no
cuenta con controladores REST implementados.

## Configuración

El backend está desarrollado con Java 21 y
Spring Boot.

Para ejecutarlo, se debe configurar la variable
de entorno `MONGODB_URI`.

Desde la carpeta `backend/`:

```bash
./gradlew bootRun
```

En Windows:

```powershell
.\gradlew.bat bootRun
```

Por defecto, Spring Boot utiliza el puerto 8080.

## Endpoints

Actualmente no existen endpoints REST propios
del cotizador implementados.

La ruta `/api` está prevista para las futuras
funcionalidades del sistema.

## Códigos de respuesta

Los códigos HTTP específicos de las operaciones
del cotizador todavía no están definidos en el código.

## Referencias

Consultar [Documentación del sistema](Documentacion-Sistema.md)
para conocer los requerimientos, casos de uso y
funcionalidades previstas.
