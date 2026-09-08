# Trabajo Final - Grupo104

## Integrantes: 
- Federico Quinteros
- Lorenzo Pattini
- Uriel Palma
  
## Presentación del Trabajo Final Integrador de la Tecnicatura de Programación

##  Cotizador de Seguros Automotores

Sistema integral para la generación automatizada de presupuestos de seguros de autos. 
Permite a usuarios cotizar en tiempo real el valor de una póliza en función de múltiples variables del vehículo, el conductor y el entorno.

##  Descripción del Proyecto

Este cotizador nace para resolver la lentitud y opacidad del mercado asegurador actual. 
El sistema entrega un precio final, basandose en factores como zona geográfica, edad del conductor, tipo de combustible, dispositivos de seguridad, etc. 

##  Tecnologías Utilizadas

- **Backend** | Java + Spring Boot | Robustez, escalabilidad y ecosistema maduro para APIs REST y conexión a MongoDB. 
- **Frontend** | TypeScript + React | Tipado estático para reducir errores en la lógica de cotización y componentes reutilizables para un dashboard ágil. 
- **Base de Datos (NoSQL)** | MongoDB Atlas | Esquema flexible: ideal para almacenar cotizaciones con atributos variables sin necesidad de migraciones complejas. Para utilizar el Embedding y evitar el congelamiento de valores; Reducción de Round-Trips y generar un Dashboard de Estadísticas; Alineación Natural con JSON evita la fricción de tener que traducir a tablas relacionales; Y como projecto que puede crecer requiere Escalabilidad Horizontal.
- **Build Tool** | Gradle | Gestión de dependencias y construcción del backend de forma eficiente. 
- **Despliegue Frontend** | Vercel |  Despliegue continuo (CI/CD) directamente desde el repositorio GitHub. 
- **Despliegue Backend** | Render |  Alojamiento económico y sencillo para servicios Spring Boot con conexión a MongoDB Atlas. 

##  Objetivos y Características Principales

### 1️⃣ Datos Actualizados de Vehículos
- **Consumo de fuentes externas:** Integración con APIs de patentes para obtener **marca, modelo, año, versión y tipo de combustible** automáticamente al ingresar la patente.

### 2️⃣ Gestión y Persistencia de Usuarios
- **Registro de prospectos:** Guardado de datos del titular y del conductor (nombre, DNI, fecha de nacimiento, correo) en MongoDB Atlas.
- **Histórico por cliente:** Capacidad de recuperar cotizaciones anteriores de un mismo usuario para darle seguimiento comercial sin tener que volver a ingresar sus datos.

### 3️⃣ Algoritmo de Cálculo Multicriterio (Precio Dinámico)
La prima final se calcula aplicando reglas de negocio secuenciales sobre un valor base:
- **Factor Regional:** Multiplicador según código postal (zonas de alto/bajo riesgo).
- **Factor Personal:** Edad del conductor (recargo para menores de 25 años) y años de antigüedad de licencia.
- **Factor del Vehículo:** Tipo de combustible (diésel/gasolina/eléctrico) y dispositivos de seguridad (alarma, GPS, cortacorriente) que otorgan descuentos.
- **Simultaneidad:** El sistema genera simultáneamente la cotización final diferentes  aseguradoras.

### 4️⃣ Panel de Estadísticas y Conversión (Dashboard)
- **Embudo de conversión:** Indicadores que reflejan cuántos clientes pasan de la *"Cotización en Línea"* a la *"Solicitud Formal de Compra"*.
- **Mapa de calor geográfico:** Estadísticas agrupadas por zona (código postal) para identificar regiones con mayor demanda o mayor tasa de cierre.

