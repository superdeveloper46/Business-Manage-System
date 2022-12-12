package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Situation;
import app.com.ChinChen.domain.TaskStatus;
import app.com.ChinChen.repository.SituationRepostiory;
import app.com.ChinChen.repository.TaskStatusRepostiory;

@Service
public class SituationService {
    private final SituationRepostiory repository;

    public SituationService(SituationRepostiory repository) {
        this.repository = repository;
    }

    public List<Situation> findAll() {
        return repository.findAll();
    }

}
