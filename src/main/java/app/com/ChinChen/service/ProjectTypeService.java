package app.com.ChinChen.service;

import app.com.ChinChen.domain.ProjectType;
import app.com.ChinChen.repository.ProjectTypeRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectTypeService {
    private final ProjectTypeRepostiory repository;

    public ProjectTypeService(ProjectTypeRepostiory repository) {
        this.repository = repository;
    }

    public ProjectType save(ProjectType projectType) {
        return repository.save(projectType);
    }

    public Page<ProjectType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProjectType> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
