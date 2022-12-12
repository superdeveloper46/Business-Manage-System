package app.com.ChinChen.service;

import app.com.ChinChen.domain.RuleDescription;
import app.com.ChinChen.repository.RuleDescriptionRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleDescriptionService {
    private final RuleDescriptionRepostiory repository;

    public RuleDescriptionService(RuleDescriptionRepostiory repository) {
        this.repository = repository;
    }

    public RuleDescription save(RuleDescription ruleDescription) {
        return repository.save(ruleDescription);
    }

    public Page<RuleDescription> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<RuleDescription> findAll() {
        return repository.findAll();
    }

    public Optional<RuleDescription> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
