package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.TaskStatus;
import app.com.ChinChen.repository.TaskStatusRepostiory;

@Service
public class TaskStatusService {
    private final TaskStatusRepostiory repository;

    public TaskStatusService(TaskStatusRepostiory repository) {
        this.repository = repository;
    }

    public List<TaskStatus> findAll() {
        return repository.findAll();
    }

}
