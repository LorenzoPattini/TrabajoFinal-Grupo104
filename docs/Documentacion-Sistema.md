# Documentación del Sistema — Cotizador de Seguros Automotores

**Grupo 104** — Federico Quinteros, Lorenzo Pattini, Uriel Palma

---

## 1. Diseño y documentación de Base de Datos

### Motor y justificación
Se utiliza **MongoDB Atlas** (NoSQL, orientado a documentos) en lugar de una base relacional por:
- Esquema flexible: las cotizaciones pueden tener atributos variables sin necesidad de migraciones.
- Embedding de datos relacionados (cliente, vehículo, factores) en un solo documento, evitando joins.
- Alineación natural con JSON, el formato que ya maneja el backend en sus respuestas REST.
- Escalabilidad horizontal para el crecimiento futuro del proyecto.

### Base y colección
- **Base de datos:** `cotizador-tfi`
- **Colección principal:** `cotizaciones`

### Decisión de diseño: embedding vs. referencias
Se optó por embeber los datos del cliente, el vehículo y los factores aplicados dentro de cada documento de cotización, en lugar de crear colecciones separadas y referenciarlas. Motivo: una cotización es un registro histórico que no debe cambiar aunque el cliente actualice sus datos más adelante — necesitamos una "foto" congelada de la información tal como era en el momento de cotizar, no una referencia viva.

### Esquema

```json
{
  "_id": "ObjectId",
  "cliente": {
    "nombre": "string",
    "dni": "string",
    "fechaNacimiento": "date",
    "email": "string"
  },
  "vehiculo": {
    "patente": "string",
    "marca": "string",
    "modelo": "string",
    "version": "string",
    "anio": "number",
    "combustible": "string"
  },
  "factoresAplicados": {
    "zona": "string",
    "edadConductor": "number",
    "antiguedadLicencia": "number",
    "dispositivosSeguridad": ["string"]
  },
  "precioFinal": "number",
  "fecha": "date"
}
```

### Diagrama de la colección

```mermaid
erDiagram
    COTIZACION {
        ObjectId _id
        number precioFinal
        date fecha
    }
    CLIENTE {
        string nombre
        string dni
        date fechaNacimiento
        string email
    }
    VEHICULO {
        string patente
        string marca
        string modelo
        string version
        number anio
        string combustible
    }
    FACTORES_APLICADOS {
        string zona
        number edadConductor
        number antiguedadLicencia
        string dispositivosSeguridad
    }
    COTIZACION ||--|| CLIENTE : embebe
    COTIZACION ||--|| VEHICULO : embebe
    COTIZACION ||--|| FACTORES_APLICADOS : embebe
```

### Evidencia
La base `cotizador-tfi` y la colección `cotizaciones` ya fueron creadas en el cluster de MongoDB Atlas del equipo, con un documento de ejemplo cargado que valida la estructura definida arriba.

---

## 2. Definición funcional de cada módulo

### Módulo 1 — Consulta de datos del vehículo
- **Descripción:** permite registrar la patente como dato informativo y seleccionar, de forma independiente, las características técnicas del vehículo mediante información provista por una API externa de vehículos.
- **Entrada:** patente ingresada manualmente por el usuario y selección de marca, modelo, año, versión y combustible, según los datos disponibles en la API externa.
- **Proceso:** la patente no se envía a la API para consultar, validar ni recuperar información. La consulta externa se utiliza únicamente como fuente de datos para la selección de características del vehículo.
- **Salida:** objeto `vehiculo` con la patente informativa y los datos técnicos seleccionados, que se utilizarán en el módulo de cálculo y se conservarán en la cotización.

### Módulo 2 — Gestión de usuarios/prospectos
- **Descripción:** registra los datos del titular y conductor, y mantiene el historial de cotizaciones por cliente.
- **Entrada:** datos personales ingresados por el usuario (nombre, DNI, fecha de nacimiento, email).
- **Salida:** documento `cliente` embebido en cada cotización asociada.

### Módulo 3 — Motor de cálculo de cotización
- **Descripción:** aplica las reglas de negocio (factor regional, personal y del vehículo) sobre un valor base para generar el precio final.
- **Entrada:** datos del cliente, del vehículo y de la zona.
- **Salida:** documento `cotizacion` completo, persistido en la colección `cotizaciones`.

### Módulo 4 — Dashboard de estadísticas
- **Descripción:** presenta el embudo de conversión (cotización → compra) y un mapa de calor geográfico por zona.
- **Entrada:** lectura agregada sobre la colección `cotizaciones`.
- **Salida:** visualizaciones para el equipo comercial.

---

## 3. Requerimientos funcionales y no funcionales

### Funcionales
- El sistema debe permitir ingresar y registrar la patente como dato informativo, sin usarla para consultar o validar información en una API externa.
- El sistema debe permitir seleccionar marca, modelo, año, versión y combustible a partir de información disponible en una API externa de vehículos, de forma independiente de la patente.
- El sistema debe permitir registrar los datos del cliente y del conductor.
- El sistema debe calcular el precio final de la póliza aplicando las reglas de negocio vigentes.
- El sistema debe generar cotizaciones para varias aseguradoras de forma simultánea.
- El sistema debe conservar el historial de cotizaciones por cliente.
- El sistema debe mostrar estadísticas de conversión y demanda por zona.

### No funcionales
*(a definir en equipo)*
- Tiempo de respuesta esperado del cálculo de cotización.
- Disponibilidad del servicio.
- Seguridad y confidencialidad de los datos personales almacenados.
- Escalabilidad ante el crecimiento del volumen de cotizaciones.

---

## 4. Reglas de negocio

La prima final se calcula aplicando reglas secuenciales sobre un valor base:
- **Factor Regional:** multiplicador según zona/código postal (zonas de mayor o menor riesgo).
- **Factor Personal:** recargo para conductores menores de 25 años; ajuste según antigüedad de la licencia de conducir.
- **Factor del Vehículo:** ajuste según tipo de combustible (nafta/diésel/eléctrico); descuentos por dispositivos de seguridad (alarma, GPS, cortacorriente).

*(Los valores numéricos exactos de cada factor —porcentajes y rangos— se definen en equipo.)*

---

## 5. Diagramas

- **Diagrama de flujo general del sistema:** ver `docs/TP_Final_Diagrama_de_Flujo.jpg` (versión editable: `docs/TP_Final_Diagrama_de_Flujo.drawio`). La patente se registra como dato informativo; la API se consulta a partir de la selección de características del vehículo, no mediante la patente.
- **Diagrama de la colección de MongoDB:** incluido en la sección 1 de este documento.
- *(Diagrama de componentes/clases del backend: a definir en equipo.)*
