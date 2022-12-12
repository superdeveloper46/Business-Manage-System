package app.com.ChinChen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Contract;
import app.com.ChinChen.repository.ContractRepostiory;

@Service
public class ContractService {
    private final ContractRepostiory repository;

    public ContractService(ContractRepostiory repository) {
        this.repository = repository;
    }

    public List<Contract> findAll() {
        return repository.findAll();
    }

    public Contract save(Contract data) {
        return repository.save(data);
    }

    public Optional<Contract> findById(String id) {
        return repository.findById(id);
    }

}
