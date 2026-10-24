package com.TP_Final.cotizador.service;

import com.TP_Final.cotizador.dto.CotizacionRequestDTO;
import com.TP_Final.cotizador.model.*;
import com.TP_Final.cotizador.repository.CotizacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    public Cotizacion generarCotizacion(CotizacionRequestDTO dto) {
        // 1. Validaciones iniciales y cálculos de edad
        int edadConductor = Period.between(dto.getConductorFechaNacimiento(), LocalDate.now()).getYears();
        if (edadConductor < 18) {
            throw new IllegalArgumentException("El conductor debe ser mayor de 18 años.");
        }
        if (dto.getAntiguedadLicencia() > edadConductor - 16) {
            throw new IllegalArgumentException("La antigüedad de la licencia no es coherente con la edad.");
        }

        int anioActual = LocalDate.now().getYear();
        int antiguedadVehiculo = anioActual - dto.getAnio();

        // 2. Calcular Factores (RN-01 a RN-06)
        BigDecimal fZona = calcularFactorZona(dto.getCodigoPostal());
        BigDecimal fEdad = calcularFactorEdad(edadConductor);
        BigDecimal fLicencia = calcularFactorLicencia(dto.getAntiguedadLicencia());
        BigDecimal fCombustible = calcularFactorCombustible(dto.getCombustible());
        BigDecimal fAntiguedad = calcularFactorAntiguedadVehiculo(antiguedadVehiculo);
        BigDecimal fSeguridad = calcularFactorSeguridad(dto.getTieneAlarma(), dto.getTieneGps(), dto.getTieneCortacorriente());

        // 3. Obtener primas base según cobertura (RN-07)
        List<Propuesta> propuestas = generarPropuestas(
                dto.getCoberturaSolicitada(), fZona, fEdad, fLicencia, fCombustible, fAntiguedad, fSeguridad
        );

        // 4. Armar el documento de Cotización (Embedding)
        Cotizacion cotizacion = new Cotizacion();
        cotizacion.setFecha(java.time.LocalDateTime.now());
        cotizacion.setEstado("COTIZADA");

        // Mapear Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getClienteNombre());
        cliente.setDni(dto.getClienteDni());
        cliente.setFechaNacimiento(dto.getClienteFechaNacimiento());
        cliente.setEmail(dto.getClienteEmail());
        cotizacion.setCliente(cliente);

        // Mapear Conductor
        Conductor conductor = new Conductor();
        conductor.setNombre(dto.getConductorNombre());
        conductor.setDni(dto.getConductorDni());
        conductor.setFechaNacimiento(dto.getConductorFechaNacimiento());
        conductor.setAntiguedadLicencia(dto.getAntiguedadLicencia());
        cotizacion.setConductor(conductor);

        // Mapear Vehículo
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPatente(dto.getPatente());
        vehiculo.setMarca(dto.getMarca());
        vehiculo.setModelo(dto.getModelo());
        vehiculo.setVersion(dto.getVersion());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setCombustible(dto.getCombustible());

        Vehiculo.DispositivosSeguridad ds = new Vehiculo.DispositivosSeguridad();
        ds.setAlarma(dto.getTieneAlarma());
        ds.setGps(dto.getTieneGps());
        ds.setCortacorriente(dto.getTieneCortacorriente());
        vehiculo.setDispositivosSeguridad(ds);
        cotizacion.setVehiculo(vehiculo);

        // Mapear resto de datos
        cotizacion.setCodigoPostal(dto.getCodigoPostal());
        cotizacion.setCategoriaZona(obtenerCategoriaZona(dto.getCodigoPostal()));
        cotizacion.setCoberturaSolicitada(dto.getCoberturaSolicitada());
        cotizacion.setAntiguedadVehiculo(antiguedadVehiculo);

        // Mapear Factores Aplicados
        FactoresAplicados factores = new FactoresAplicados();
        factores.setFactorZona(fZona);
        factores.setFactorEdad(fEdad);
        factores.setFactorLicencia(fLicencia);
        factores.setFactorCombustible(fCombustible);
        factores.setFactorAntiguedadVehiculo(fAntiguedad);
        factores.setFactorSeguridad(fSeguridad);
        cotizacion.setFactoresAplicados(factores);

        cotizacion.setResultados(propuestas);
        cotizacion.setSolicitudFormal(null);

        // 5. Guardar en MongoDB (RN-12)
        return cotizacionRepository.save(cotizacion);
    }

    // ==========================================
    // LÓGICA DE REGLAS DE NEGOCIO (RN)
    // ==========================================

    private BigDecimal calcularFactorZona(String cp) {
        // Simulación RN-01 (En un futuro esto vendría de una BD)
        if (cp.startsWith("1")) return new BigDecimal("1.20"); // Riesgo alto (ej. CABA)
        if (cp.startsWith("5")) return new BigDecimal("0.95"); // Riesgo bajo (ej. Córdoba)
        return new BigDecimal("1.00"); // Medio
    }

    private String obtenerCategoriaZona(String cp) {
        if (cp.startsWith("1")) return "ALTA";
        if (cp.startsWith("5")) return "BAJA";
        return "MEDIA";
    }

    private BigDecimal calcularFactorEdad(int edad) {
        // RN-02
        if (edad >= 18 && edad <= 24) return new BigDecimal("1.20");
        if (edad > 65) return new BigDecimal("1.10");
        return new BigDecimal("1.00");
    }

    private BigDecimal calcularFactorLicencia(int antiguedad) {
        // RN-03
        if (antiguedad < 2) return new BigDecimal("1.15");
        if (antiguedad <= 5) return new BigDecimal("1.05");
        return new BigDecimal("1.00");
    }

    private BigDecimal calcularFactorCombustible(String combustible) {
        // RN-04
        return switch (combustible.toLowerCase()) {
            case "diesel" -> new BigDecimal("1.05");
            case "gnc" -> new BigDecimal("1.03");
            case "hibrido" -> new BigDecimal("0.98");
            case "electrico" -> new BigDecimal("0.95");
            default -> new BigDecimal("1.00"); // Nafta
        };
    }

    private BigDecimal calcularFactorAntiguedadVehiculo(int antiguedad) {
        // RN-05
        if (antiguedad <= 2) return new BigDecimal("0.95");
        if (antiguedad <= 5) return new BigDecimal("1.00");
        if (antiguedad <= 10) return new BigDecimal("1.10");
        if (antiguedad <= 15) return new BigDecimal("1.20");
        return new BigDecimal("1.35");
    }

    private BigDecimal calcularFactorSeguridad(Boolean alarma, Boolean gps, Boolean cortacorriente) {
        // RN-06
        BigDecimal descuento = BigDecimal.ZERO;
        if (Boolean.TRUE.equals(alarma)) descuento = descuento.add(new BigDecimal("0.03"));
        if (Boolean.TRUE.equals(gps)) descuento = descuento.add(new BigDecimal("0.05"));
        if (Boolean.TRUE.equals(cortacorriente)) descuento = descuento.add(new BigDecimal("0.02"));

        // Tope máximo del 10%
        if (descuento.compareTo(new BigDecimal("0.10")) > 0) {
            descuento = new BigDecimal("0.10");
        }

        return BigDecimal.ONE.subtract(descuento);
    }

    private List<Propuesta> generarPropuestas(String cobertura, BigDecimal... factores) {
        // RN-07: Primas Base
        BigDecimal[] primasA = {new BigDecimal("30000"), new BigDecimal("50000"), new BigDecimal("80000")};
        BigDecimal[] primasB = {new BigDecimal("32000"), new BigDecimal("48000"), new BigDecimal("85000")};
        BigDecimal[] primasC = {new BigDecimal("28000"), new BigDecimal("52000"), new BigDecimal("78000")};

        int indexCobertura = switch (cobertura) {
            case "Responsabilidad Civil" -> 0;
            case "Terceros Completo" -> 1;
            case "Todo Riesgo" -> 2;
            default -> throw new IllegalArgumentException("Cobertura no válida");
        };

        List<Propuesta> lista = new ArrayList<>();
        lista.add(crearPropuesta("Aseguradora A", cobertura, primasA[indexCobertura], factores));
        lista.add(crearPropuesta("Aseguradora B", cobertura, primasB[indexCobertura], factores));
        lista.add(crearPropuesta("Aseguradora C", cobertura, primasC[indexCobertura], factores));
        return lista;
    }

    private Propuesta crearPropuesta(String aseguradora, String cobertura, BigDecimal primaBase, BigDecimal... factores) {
        // Fórmula: PrimaBase * F1 * F2 * F3 * F4 * F5 * F6
        BigDecimal precioFinal = primaBase;
        for (BigDecimal factor : factores) {
            precioFinal = precioFinal.multiply(factor);
        }

        // RN-11: Redondeo final (HALF_UP, 2 decimales)
        precioFinal = precioFinal.setScale(2, RoundingMode.HALF_UP);

        Propuesta p = new Propuesta();
        p.setAseguradora(aseguradora);
        p.setCobertura(cobertura);
        p.setPrimaBase(primaBase);
        p.setPrecioFinal(precioFinal);
        return p;
    }
}