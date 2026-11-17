# Frontend - Cotizador de Seguros Automotores

Frontend correspondiente al Trabajo Final Integrador del **Grupo 104** de la Tecnicatura Universitaria en Programación.

El proyecto consiste en un sistema web para la generación y comparación de cotizaciones de seguros automotores a partir de información del vehículo, el conductor y diferentes factores de riesgo.

## Estado actual

El frontend se encuentra actualmente en una etapa inicial de desarrollo.

En esta primera iteración se realizó:

- Configuración inicial del proyecto con React, TypeScript y Vite.
- Configuración de ESLint.
- Incorporación de Axios para la futura comunicación con el backend.
- Incorporación de React Router DOM para la futura navegación entre vistas.
- Definición de una estructura inicial de carpetas.
- Configuración de un cliente HTTP centralizado.
- Desarrollo de una pantalla Home demostrativa y responsive.
- Preparación de la aplicación para su posterior despliegue en Vercel.

Las pantallas definitivas, rutas y flujo completo de cotización se incorporarán progresivamente a medida que avance la definición funcional del sistema.

## Tecnologías utilizadas

- **React:** construcción de la interfaz mediante componentes reutilizables.
- **TypeScript:** tipado estático del código frontend.
- **Vite:** entorno de desarrollo y herramienta de construcción.
- **Axios:** consumo de la API REST del backend.
- **React Router DOM:** dependencia incorporada para implementar la navegación entre las distintas vistas del sistema.
- **ESLint:** análisis estático y mantenimiento de calidad del código.
- **Vercel:** plataforma prevista para el despliegue del frontend.

## Estructura inicial

```text
frontend/
├── public/
│   └── favicon.svg
├── src/
│   ├── services/
│   │   └── api.ts
│   ├── App.css
│   ├── App.tsx
│   ├── index.css
│   └── main.tsx
├── .env.example
├── .gitignore
├── eslint.config.js
├── index.html
├── package.json
├── package-lock.json
├── README.md
├── tsconfig.app.json
├── tsconfig.json
├── tsconfig.node.json
└── vite.config.ts
```


### Carpetas principales

**public/**  
Contiene los recursos públicos de la aplicación,
como el favicon.

**src/**  
Contiene el código fuente de la aplicación React,
incluyendo la pantalla inicial y sus estilos.

**src/services/**  
Contiene el cliente HTTP centralizado,
configurado con Axios para la comunicación
con el backend.


## Comunicación con el backend

La comunicación HTTP se encuentra centralizada en:

```text
src/services/api.ts
```

Actualmente se utiliza una instancia de Axios configurada de la siguiente manera:

```ts
import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
```

La URL del backend se obtiene mediante una variable de entorno para evitar incorporar direcciones específicas dentro del código fuente.

Ejemplo para desarrollo local:

```env
VITE_API_URL=http://localhost:8080/api
```

Cuando el backend sea desplegado en Render, esta variable podrá configurarse con la URL correspondiente al entorno productivo.

## Ejecución local

### Requisitos

Es necesario contar con:

- Node.js
- npm

### Configuración de variables de entorno

Desde la carpeta `frontend/`, copiar el archivo
`.env.example` como `.env`.

En Windows (PowerShell):

```powershell
Copy-Item .env.example .env
```

En Linux o macOS:

```bash
cp .env.example .env
```

La variable `VITE_API_URL` permite configurar
la dirección del backend.

### Instalación

Desde la carpeta `frontend/`, ejecutar:

```bash
npm install
```

### Iniciar el servidor de desarrollo

```bash
npm run dev
```

Por defecto, Vite inicia la aplicación en:

http://localhost:5173

## Scripts disponibles

```bash
npm run dev
```

Inicia el servidor de desarrollo.

```bash
npm run build
```

Genera la versión optimizada de producción.

```bash
npm run lint
```

Ejecuta ESLint para verificar el código.

```bash
npm run preview
```

Permite visualizar localmente la versión generada para producción.

## Despliegue

El frontend será desplegado en **Vercel**.

La arquitectura prevista del sistema es:

```text
Frontend
React + TypeScript
Vercel
        │
        │ HTTP / REST / JSON
        ▼
Backend
Java + Spring Boot
Render
        │
        ▼
MongoDB Atlas
```

El despliegue podrá integrarse con el repositorio de GitHub para generar nuevas versiones automáticamente a partir de los cambios incorporados al proyecto.

## Próximos pasos

Entre las próximas tareas previstas para el frontend se encuentran:

- Definir junto al equipo el flujo definitivo de navegación.
- Diseñar las vistas correspondientes al proceso de cotización.
- Implementar componentes reutilizables.
- Definir los tipos de datos intercambiados con el backend.
- Integrar los endpoints REST desarrollados en Spring Boot.
- Implementar validaciones de formularios.
- Incorporar la visualización y comparación de cotizaciones.
- Implementar las vistas asociadas a estadísticas y conversión.
- Configurar el despliegue en Vercel.

> La estructura funcional puede modificarse durante las próximas iteraciones de acuerdo con las decisiones de diseño y los requerimientos definidos por el equipo.