package app.com.ChinChen.service;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.Job;
import app.com.ChinChen.domain.WorkType;
import app.com.ChinChen.library.AES;
import app.com.ChinChen.library.CalProjectDate;
import app.com.ChinChen.library.PageUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Project;
import app.com.ChinChen.domain_temp.Project_Data;
import app.com.ChinChen.repository.ProjectRepostiory;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepostiory repository;

    public ProjectService(ProjectRepostiory repository) {
        this.repository = repository;
    }

    public Project save(Project project) {
        return repository.save(project);
    }

    public Page<Project_Data> findAll2(Pageable pageable) {
        List<Project_Data> ProjectDataList = repository.findAll2();
        Page<Project_Data> Project_Data = PageUtil.getPage(ProjectDataList, pageable);
        return Project_Data;
    }

    public Optional<Project> findById(String id) {
        return repository.findById(id);
    }

    public Project getProjectById(String id) {
        return repository.findById(id).get();
    }

    public Project_Data getProjectDataById(String id) {
        Project_Data projectData = repository.findDataById(id);
        CalProjectDate.getPtojectDate(new Date(), projectData);
        return projectData;
    }
}
