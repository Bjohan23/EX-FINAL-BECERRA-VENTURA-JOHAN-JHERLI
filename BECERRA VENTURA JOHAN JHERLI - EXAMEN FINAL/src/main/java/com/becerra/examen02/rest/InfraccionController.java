package com.becerra.examen02.rest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.becerra.examen02.converter.InfraccionConverter;
import com.becerra.examen02.dto.InfraccionDto;
import com.becerra.examen02.entity.Infraccion;
import com.becerra.examen02.service.InfraccionService;
import com.becerra.examen02.util.WrapperResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;




@RestController
@RequestMapping("/v1/infracciones")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InfraccionController{
@Autowired
    private InfraccionService service;

    @Autowired
    private InfraccionConverter converter;
    @GetMapping
    public ResponseEntity<List<InfraccionDto>> findAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<InfraccionDto> infraccion = converter.fromEntities(service.findAll());
        return new WrapperResponse(true, "success", infraccion).createResponse(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InfraccionDto> create (@RequestBody InfraccionDto infraccion) {
        Infraccion entity = converter.fromDTO(infraccion);
        InfraccionDto dto = converter.fromEntity(service.save(entity));//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InfraccionDto> update (@PathVariable("id") int id, @RequestBody InfraccionDto infraccion) {
        Infraccion entity = converter.fromDTO(infraccion);
        InfraccionDto dto = converter.fromEntity(service.save(entity));
//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete (@PathVariable("id") int id) {
        service.delete(id);
//        return ResponseEntity.ok(null);
        return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InfraccionDto> findById (@PathVariable("id") int id) {
        InfraccionDto dto = converter.fromEntity(service.findById(id));

//        return ResponseEntity.ok(dto);
        return new WrapperResponse(true, "success", dto).createResponse(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<InfraccionDto>> findByDni(
            @RequestParam String dni,
            @RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<InfraccionDto> infracciones = converter.fromEntities(service.findByNombre(dni, page));
        return new WrapperResponse(true, "success", infracciones).createResponse(HttpStatus.OK);
    }
}
