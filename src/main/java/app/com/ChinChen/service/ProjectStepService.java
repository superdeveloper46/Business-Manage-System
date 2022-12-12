package app.com.ChinChen.service;

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.domain.ProjectStep;
import app.com.ChinChen.repository.FormManageRepository;
import app.com.ChinChen.repository.ProjectStepRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectStepService {
    private final ProjectStepRepostiory repository;
    private final FormManageRepository formManageRepository;

    public ProjectStepService(ProjectStepRepostiory repository, FormManageRepository formManageRepository) {
        this.repository = repository;
        this.formManageRepository = formManageRepository;
    }

    public ProjectStep save(ProjectStep data) {
        return repository.save(data);
    }

    public Page<ProjectStep> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<ProjectStep> findById(String id) {
        return repository.findById(id);
    }

    public String delete(String id, boolean delete) {
//        Optional<ProjectStep> projectStep = repository.findById(id);
//        if (projectStep.isPresent()) {
//            int stepNo = projectStep.get().getStepNo();
//        }
        List<Form> forms = formManageRepository.findByProjectStepId(id);
        if (forms.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該專案階段已有表單資料\"}";
        }
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }

    public List<ProjectStep> findAll() {
        return repository.findAll();
    }

}
