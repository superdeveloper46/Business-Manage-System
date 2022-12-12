package app.com.ChinChen.service;

import java.util.List;

import app.com.ChinChen.domain_temp.Project_Data;
import app.com.ChinChen.library.CalProjectDate;
import app.com.ChinChen.repository.ProjectRepostiory;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.DailyReport;
import app.com.ChinChen.repository.DailyReportRepostiory;

@Service
public class DailyReportService {
    private final DailyReportRepostiory repository;

    private final ProjectRepostiory projectRepository;

    public DailyReportService(DailyReportRepostiory repository, ProjectRepostiory projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public List<DailyReport> findByProjectId(String projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public DailyReport findById(String id) {
        return repository.findById(id).get();
    }

    public DailyReport findAllById(String id) {
        DailyReport dailyReport = repository.findAllById(id);
        Project_Data projectData = projectRepository.findDataById(dailyReport.getProjectId());
        CalProjectDate.getPtojectDate(dailyReport.getDailyDate(), projectData);
        dailyReport.setProject(projectData);
        return dailyReport;
    }

    public DailyReport save(DailyReport data) {
        return repository.save(data);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
