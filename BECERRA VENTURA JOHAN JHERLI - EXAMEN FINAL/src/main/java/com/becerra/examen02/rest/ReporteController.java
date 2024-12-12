package com.becerra.examen02.rest;

import com.becerra.examen02.converter.InfraccionConverter;
import com.becerra.examen02.dto.InfraccionDto;
import com.becerra.examen02.service.InfraccionService;
import com.becerra.examen02.util.WrapperResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reportes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReporteController {

    private final InfraccionService infraccionService;
    private final InfraccionConverter infraccionConverter;

    @GetMapping("/infracciones")
    public ResponseEntity<WrapperResponse<List<InfraccionDto>>> getAllInfracciones() {
        List<InfraccionDto> infracciones = infraccionConverter.fromEntities(infraccionService.findAll());
        return new WrapperResponse<>(true, "success", infracciones)
                .createResponse();
    }

    @GetMapping("/infracciones/dni/{dni}")
    public ResponseEntity<WrapperResponse<List<InfraccionDto>>> getInfraccionesPorDni(
            @PathVariable String dni) {
        List<InfraccionDto> infracciones = infraccionConverter.fromEntities(
                infraccionService.findByDni(dni)
        );
        return new WrapperResponse<>(true, "success", infracciones)
                .createResponse();
    }
}