package app.com.ChinChen.service;

import app.com.ChinChen.domain.ContractType;
import app.com.ChinChen.repository.ContractTypeRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContractTypeService {
    private final ContractTypeRepostiory repository;

    public ContractTypeService(ContractTypeRepostiory repository) {
        this.repository = repository;
    }

    public ContractType save(ContractType contractType) {
        return repository.save(contractType);
    }

    public Page<ContractType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ContractType> findAll() {
        return repository.findAll();
    }

    public Optional<ContractType> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
