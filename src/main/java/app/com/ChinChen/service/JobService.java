package app.com.ChinChen.service;

import app.com.ChinChen.domain.Department;
import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.JobModules;
import app.com.ChinChen.repository.EmployeeRepository;
import app.com.ChinChen.repository.JobModulesRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.BodyInserters;
// import org.springframework.web.reactive.function.client.WebClient;
// import reactor.core.publisher.Mono;

import app.com.ChinChen.domain.Job;
import app.com.ChinChen.repository.JobRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobRepository repository;
    private final EmployeeRepository employeereRository;
    private final JobModulesRepository jobModulesRepository;

    public JobService(JobRepository repository, EmployeeRepository employeereRository, JobModulesRepository jobModulesRepository) {
        this.repository = repository;
        this.employeereRository = employeereRository;
        this.jobModulesRepository = jobModulesRepository;
    }

    public Job save(Job job) {
        return repository.save(job);
    }

    public Page<Job> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Job> findAllByForm() {
        return repository.findByForm();
    }

    public Optional<Job> findById(String id) {
        return repository.findById(id);
    }

    public String delete(String id, boolean delete) {
        List<String> ids = new ArrayList<String>();
        if (delete) {
            ids.add(id);
            repository.deleteBy_idIn(ids);
            return id;
        }
        List<Employee> departments = employeereRository.findByJobList(id);
        if (departments.size() > 0) {
            return "{\"delete\":true,\"msg\":\"該職務已有員工資料\"}";
        }
        List<JobModules> jobModules = jobModulesRepository.findByJobId(id);
        if (jobModules.size() > 0) {
            return "{\"delete\":true,\"msg\":\"該職務已有職務權限資料\"}";
        }
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }
}
