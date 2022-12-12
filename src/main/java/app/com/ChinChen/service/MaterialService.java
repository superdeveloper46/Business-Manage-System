package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Material;
import app.com.ChinChen.repository.MaterialRepostiory;

@Service
public class MaterialService {
    private final MaterialRepostiory repository;

    public MaterialService(MaterialRepostiory repository) {
        this.repository = repository;
    }

    public List<Material> findByProjectId(String projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public Material findById(String id) {
        return repository.findById(id).get();
    }

    public Material save(Material data) {
        return repository.save(data);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
