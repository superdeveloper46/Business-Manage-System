package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.EmergencyType;
import app.com.ChinChen.repository.EmergencyTypeRepostiory;

@Service
public class EmergencyTypeService {
    private final EmergencyTypeRepostiory repository;

    public EmergencyTypeService(EmergencyTypeRepostiory repository) {
        this.repository = repository;
    }

    public List<EmergencyType> findAll() {
        return repository.findAll();
    }

}
