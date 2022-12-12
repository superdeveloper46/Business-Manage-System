package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Permission;
import app.com.ChinChen.repository.PermissionRepostiory;

@Service
public class PermissionService {
    private final PermissionRepostiory repository;

    public PermissionService(PermissionRepostiory repository) {
        this.repository = repository;
    }

    public List<Permission> findAll(String loginId, String moduleId) {
        return repository.findAll(loginId, moduleId);
    }

}
