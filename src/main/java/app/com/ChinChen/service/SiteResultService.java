package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.SiteResult;
import app.com.ChinChen.repository.SiteResultRepostiory;

@Service
public class SiteResultService {
    private final SiteResultRepostiory repository;

    public SiteResultService(SiteResultRepostiory repository) {
        this.repository = repository;
    }

    public List<SiteResult> findAll() {
        return repository.findAll();
    }

}
