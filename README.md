# Trabajo Final – Grupo 104

## Integrantes
- Federico Quinteros
- Lorenzo Pattini
- Uriel Palma

## Trabajo Final Integrador – Tecnicatura en Programación

# Cotizador de Seguros Automotores

Sistema web orientado a la generación y comparación de cotizaciones de seguros automotores. Su objetivo es permitir que un usuario obtenga presupuestos simulados a partir de información del vehículo, del conductor y de la zona geográfica, mediante reglas de negocio definidas y documentadas.


## Descripción del proyecto

El proyecto propone agilizar y hacer más transparente el proceso de cotización de seguros automotores. El usuario ingresará los datos necesarios y el sistema calculará propuestas de distintas aseguradoras ficticias para una cobertura seleccionada, permitiendo comparar sus precios mensuales.

El cálculo se realizará en el backend con una fórmula multicriterio. El frontend permitirá ingresar la información y visualizar los resultados; MongoDB Atlas conservará el historial de cotizaciones y los factores utilizados en cada cálculo.

## Tecnologías utilizadas

| Componente | Tecnología | Finalidad |
|---|---|---|
| Backend | Java + Spring Boot | API REST, validaciones y motor de cálculo de cotizaciones. |
| Frontend | React + TypeScript + Vite | Interfaz web, formularios y presentación de cotizaciones. |
| Base de datos | MongoDB Atlas | Almacenamiento de cotizaciones con datos embebidos e historial de resultados. |
| Construcción del backend | Gradle | Gestión de dependencias y compilación. |
| Despliegue del frontend | Vercel | Plataforma prevista para publicar la aplicación web. |
| Despliegue del backend | Render | Plataforma prevista para publicar la API REST. |

Se eligió MongoDB Atlas por su modelo de documentos, que permite guardar en una misma cotización la información del cliente, el conductor, el vehículo, los factores aplicados y las propuestas de aseguradoras. Esta estructura conserva una **copia histórica** de los datos utilizados, incluso si las configuraciones cambian posteriormente. También facilita el intercambio de información en formato JSON con la API REST.

## Objetivos y características principales

### 1. Consulta de datos del vehículo

- **Patente informativa:** el usuario ingresará la patente manualmente para registrarla junto a los datos del vehículo. No se utilizará para buscar, consultar o validar datos mediante una API externa, ni modificará el precio de la cotización.
- **Consulta de características técnicas:** se integra la API de ArgAutos, que provee el catálogo de vehículos del mercado argentino para seleccionar marca, modelo, año de fabricación, versión y tipo de combustible, independientemente de la patente.
- **Antigüedad del automóvil:** se calculará a partir del año de fabricación y se utilizará como factor en el precio del seguro.

### 2. Registro de clientes y conductores

- **Datos personales:** registro de la información necesaria del cliente y del conductor, según corresponda, como nombre, DNI, fecha de nacimiento y correo electrónico.
- **Historial de cotizaciones:** conservación de las propuestas generadas, sus importes y factores de cálculo para permitir consultas posteriores mediante acceso autorizado.

### 3. Algoritmo de cálculo multicriterio

La prima mensual se calculará aplicando una **fórmula multiplicativa** sobre la prima base definida para cada aseguradora y tipo de cobertura:

**Prima final = Prima base × Factor zona × Factor edad × Factor licencia × Factor combustible × Factor antigüedad del vehículo × Factor seguridad**

Los factores contemplados son:

- **Factor geográfico:** ajuste por categoría de riesgo asociada al código postal.
- **Factor por edad:** ajuste según la edad del conductor.
- **Factor por licencia:** ajuste según la antigüedad de la licencia de conducir.
- **Factor por combustible:** ajuste según el tipo de combustible del vehículo.
- **Factor por antigüedad del vehículo:** ajuste según los años transcurridos desde la fabricación.
- **Factor de seguridad:** descuentos por dispositivos como alarma, GPS y cortacorriente.

Para la primera versión se definieron tres aseguradoras ficticias y coberturas de Responsabilidad Civil, Terceros Completo y Todo Riesgo. Cada aseguradora tendrá primas base configuradas, y el sistema aplicará los mismos factores a las propuestas disponibles para la cobertura solicitada.

Los porcentajes y precios base son **valores de simulación académica**. Su detalle, las validaciones y los ejemplos matemáticos se encuentran en la documentación técnica.

### 4. Solicitud formal de contratación

El usuario podrá seleccionar una propuesta y registrar su intención de contratarla mediante una **solicitud formal** asociada a la cotización. Esta acción no incluye pagos, emisión de pólizas ni contratación efectiva con una compañía real.

### 5. Panel de estadísticas y conversión

- **Embudo de conversión:** indicadores de cotizaciones generadas y de aquellas que avanzan a una solicitud formal de contratación.
- **Tasa de conversión:** proporción de cotizaciones que derivan en una solicitud formal, evitando contabilizar una misma cotización convertida más de una vez.
- **Demanda por zona:** estadísticas agrupadas por código postal para identificar las regiones con mayor cantidad de cotizaciones.


## Arquitectura prevista

```text
Frontend (React + TypeScript)
         | API REST / JSON
         v
Backend (Java + Spring Boot)
         |             |
         v             v
   MongoDB Atlas    API ArgAutos
```

El frontend se desplegará en Vercel y el backend en Render. La consulta al catálogo de ArgAutos se realizará mediante la selección de las características del vehículo, sin utilizar la patente como criterio de búsqueda.

## Documentación

- [Documentación del sistema](docs/Documentacion-Sistema.md):
  requerimientos, reglas de negocio, modelo de datos,
  casos de uso, diagramas y matriz de trazabilidad.
- [API REST del backend](docs/API.md):
  estado actual, configuración y endpoints disponibles.

## Cómo ejecutar

### Backend

Para ejecutar el backend, configurar las variables
de entorno documentadas en `backend/.env.example`.

Desde la carpeta `backend/`, ejecutar:

**Linux o macOS:**

```bash
./gradlew bootRun
```

**Windows:**

```powershell
.\gradlew.bat bootRun
```

El servidor utiliza el puerto 8080 por defecto.
La ruta `/api` está prevista para los endpoints REST.

### Frontend

Desde la carpeta `frontend/`, copiar
`.env.example` como `.env`.

Instalar las dependencias:

```bash
npm install
```

Iniciar el servidor de desarrollo:

```bash
npm run dev
```

Por defecto, la aplicación estará disponible en
http://localhost:5173.

## Fuentes de datos

Los datos de vehículos (marcas, modelos, versiones y años) son provistos por [ArgAutos](https://argautos.com).

