package app.com.ChinChen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.FormTask;
import app.com.ChinChen.domain.PccesCode;
import app.com.ChinChen.repository.FormTaskProcessRepository;
import app.com.ChinChen.repository.PccesCodeRepository;

@Service
public class FormTaskProcessService {
    private final FormTaskProcessRepository repository;
    private PccesCodeRepository pccesCodeRepository;
    

    public FormTaskProcessService(FormTaskProcessRepository repository, PccesCodeRepository pccesCodeRepository) {
        this.repository = repository;
        this.pccesCodeRepository = pccesCodeRepository;
    }

    public List<FormTask> findAll() {
        return repository.findAll();
    }

    public Optional<FormTask> findById(String id) {
        return repository.findById(id);
    }

    public FormTask save(FormTask formTask) {
        return repository.save(formTask);
    }

    public List<PccesCode> findPccesCode(String value) {
        return pccesCodeRepository.find(value);
    }

    
}
