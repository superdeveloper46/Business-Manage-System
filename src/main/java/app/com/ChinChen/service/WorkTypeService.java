package app.com.ChinChen.service;

import app.com.ChinChen.domain.WorkType;
import app.com.ChinChen.repository.WorkTypeRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkTypeService {

    private final WorkTypeRepostiory repository;

    public WorkTypeService(WorkTypeRepostiory repository) {
        this.repository = repository;
    }

    public WorkType save(WorkType workType) {
        return repository.save(workType);
    }

    public Page<WorkType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<WorkType> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }

    public List<WorkType> findAll() {
        return repository.findAll();
    }

    public List<WorkType> findAllByEnable() {
        return repository.findAllByEnable();
    }

}
