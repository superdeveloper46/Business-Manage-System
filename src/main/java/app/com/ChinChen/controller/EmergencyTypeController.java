package app.com.ChinChen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.EmergencyType;
import app.com.ChinChen.service.EmergencyTypeService;

@RestController
@RequestMapping(path = {"/emergencyType"})
public class EmergencyTypeController {
    private final EmergencyTypeService service;
    public EmergencyTypeController(EmergencyTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<EmergencyType> list = service.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }
}
