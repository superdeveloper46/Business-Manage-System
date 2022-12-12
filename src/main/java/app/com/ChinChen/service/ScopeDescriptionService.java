package app.com.ChinChen.service;

import app.com.ChinChen.domain.ScopeDescription;
import app.com.ChinChen.repository.ScopeDescriptionreRository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScopeDescriptionService {
    private final ScopeDescriptionreRository repository;

    public ScopeDescriptionService(ScopeDescriptionreRository repository) {
        this.repository = repository;
    }

    public ScopeDescription save(ScopeDescription scopeDescription) {
        return repository.save(scopeDescription);
    }

    public Page<ScopeDescription> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ScopeDescription> findAll() {
        return repository.findAll();
    }

    public Optional<ScopeDescription> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
