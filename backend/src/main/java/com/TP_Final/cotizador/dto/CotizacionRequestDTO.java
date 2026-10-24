package com.TP_Final.cotizador.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CotizacionRequestDTO {

    // --- Datos del Cliente ---
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String clienteNombre;
    @NotBlank(message = "El DNI es obligatorio")
    private String clienteDni;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate clienteFechaNacimiento;
    private String clienteEmail;

    // --- Datos del Conductor ---
    @NotBlank(message = "El nombre del conductor es obligatorio")
    private String conductorNombre;
    @NotBlank(message = "El DNI del conductor es obligatorio")
    private String conductorDni;
    @NotNull(message = "La fecha de nacimiento del conductor es obligatoria")
    private LocalDate conductorFechaNacimiento;
    @Min(value = 0, message = "La antigüedad de licencia no puede ser negativa")
    private Integer antiguedadLicencia;

    // --- Datos del Vehículo ---
    @NotBlank(message = "La patente es obligatoria")
    private String patente;
    @NotBlank(message = "La marca es obligatoria")
    private String marca;
    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;
    @NotBlank(message = "La versión es obligatoria")
    private String version;
    @NotNull(message = "El año es obligatorio")
    @Min(value = 1900, message = "El año debe ser mayor a 1900")
    private Integer anio;
    @NotBlank(message = "El tipo de combustible es obligatorio")
    private String combustible; // Nafta, Diesel, GNC, Hibrido, Electrico

    // Dispositivos de seguridad
    private Boolean tieneAlarma;
    private Boolean tieneGps;
    private Boolean tieneCortacorriente;

    // --- Datos de la Cotización ---
    @NotBlank(message = "El código postal es obligatorio")
    private String codigoPostal;
    @NotBlank(message = "La cobertura es obligatoria")
    private String coberturaSolicitada; // "Responsabilidad Civil", "Terceros Completo", "Todo Riesgo"
}