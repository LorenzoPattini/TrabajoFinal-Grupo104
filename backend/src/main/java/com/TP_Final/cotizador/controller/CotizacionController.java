package com.TP_Final.cotizador.controller;

import com.TP_Final.cotizador.dto.CotizacionRequestDTO;
import com.TP_Final.cotizador.model.Cotizacion;
import com.TP_Final.cotizador.service.CotizacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cotizaciones")
@RequiredArgsConstructor
public class CotizacionController {

    private final CotizacionService cotizacionService;

    @PostMapping("/calcular")
    public ResponseEntity<Cotizacion> calcularCotizacion(@Valid @RequestBody CotizacionRequestDTO request) {
        Cotizacion cotizacionGenerada = cotizacionService.generarCotizacion(request);

        // Devuelve un HTTP 201 (Created) con el JSON de la cotización guardada
        return new ResponseEntity<>(cotizacionGenerada, HttpStatus.CREATED);
    }
}