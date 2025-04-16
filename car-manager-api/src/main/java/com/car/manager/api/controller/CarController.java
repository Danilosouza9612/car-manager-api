package com.car.manager.api.controller;

import com.car.manager.core.dto.PageContent;
import com.car.manager.core.dto.car.CarDTO;
import com.car.manager.core.dto.car.CarResponseDTO;
import com.car.manager.core.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
public class CarController implements ICarController{
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity<CarResponseDTO> create(@Valid @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.create(carDTO, getLogin()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponseDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(carService.read(id, getLogin()));
    }

    @GetMapping
    public ResponseEntity<PageContent<CarResponseDTO>> list(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int perPage
    ) {
        return ResponseEntity.ok(carService.list(page, perPage, getLogin()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CarDTO carDTO) {
        return ResponseEntity.ok(carService.update(id, carDTO, getLogin()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        carService.delete(id, getLogin());
        return ResponseEntity.noContent().build();
    }

    private String getLogin(){
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
