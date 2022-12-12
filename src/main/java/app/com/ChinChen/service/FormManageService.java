package app.com.ChinChen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.repository.FormManageRepository;

import java.util.Optional;


@Service
public class FormManageService {
    @Value("${server.port}")
    String port;
    private final FormManageRepository repository;

    public FormManageService(FormManageRepository repository) {
        this.repository = repository;
    }

    public Form save(Form Form) {
        return repository.save(Form);
    }

    public List<Form> findAll() {
        return repository.findAllData();
    }

    public Optional<Form> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }

}
