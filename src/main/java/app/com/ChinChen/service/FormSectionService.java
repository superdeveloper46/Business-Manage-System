package app.com.ChinChen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.repository.FormSectionRepository;


@Service
public class FormSectionService {
    @Value("${server.port}")
    String port;
    private final FormSectionRepository repository;

    public FormSectionService(FormSectionRepository repository) {
        this.repository = repository;
    }

    public Form getFormSections(String id) {
        return repository.getFormSections(id);
    }

    public List<Form> findByIdAndSectionName(String id, String sectionName) {
        return repository.findByIdAndSectionName(id, sectionName);
    }

    public List<Form> findByIdAndSort(String id, int sort) {
        return repository.findByIdAndSort(id, sort);
    }

    public List<Form> findByIdAndSectionName(String id, String sectionName, String formSection_id) {
        return repository.findByIdAndSectionName(id, sectionName, formSection_id);
    }

    public List<Form> findByIdAndSort(String id, int sort, String formSection_id) {
        return repository.findByIdAndSort(id, sort, formSection_id);
    }

}
