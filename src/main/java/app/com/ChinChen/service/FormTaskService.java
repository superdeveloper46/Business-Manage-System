package app.com.ChinChen.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.FormTask;
import app.com.ChinChen.domain_temp.AlreadySigner;
import app.com.ChinChen.repository.FormTaskRepository;

@Service
public class FormTaskService {
    @Value("${server.port}")
    String port;
    private final FormTaskRepository repository;

    public FormTaskService(FormTaskRepository repository) {
        this.repository = repository;
    }

    public List<FormTask> findAll() {
        return repository.findAll();
    }

    public Optional<FormTask> findById(String id) {
        return repository.findById(id);
    }

    public List<FormTask> findByTaskStatusId(String formId, String taskStatusIid, String creatorId) {
        return repository.findByTaskStatusId(formId, taskStatusIid, creatorId);
    }

    public List<FormTask> findBySignerId(String formId, String signerId) {
        return repository.findBySignerId(formId, signerId);
    }
    

    public FormTask findByFormTaskId(String formTaskId, String creatorId) {
        return repository.findByFormTaskId(formTaskId, creatorId);
    }

    public List<FormTask> findAll(String formId, String creatorId) {
        return repository.findAll(formId, creatorId);
    }

    public List<Employee> findCreator(String formTaskId) {
        return repository.findCreator(formTaskId);
    }

    public List<Employee> findSignerId(String formTaskId, String field) {
        return repository.findSignerId(formTaskId, field);
    }

    public List<AlreadySigner> findAlreadySigners(String formTaskId) {
        return repository.findAlreadySigners(formTaskId);
    }

    public FormTask save(FormTask Form) {
        return repository.save(Form);
    }

}
