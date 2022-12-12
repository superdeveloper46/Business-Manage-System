package app.com.ChinChen.service;

import app.com.ChinChen.domain.FunctionModule;
import app.com.ChinChen.domain.JobModules;
import app.com.ChinChen.repository.FunctionModuleRepostiory;
import app.com.ChinChen.repository.JobModulesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FunctionModuleService {
    private final FunctionModuleRepostiory repository;
    private final JobModulesRepository jobModulesRepository;

    public FunctionModuleService(FunctionModuleRepostiory repository, JobModulesRepository jobModulesRepository) {
        this.repository = repository;
        this.jobModulesRepository = jobModulesRepository;
    }

    public FunctionModule save(FunctionModule functionModule) {
        return repository.save(functionModule);
    }

    public Page<FunctionModule> findAll(Pageable pageable) {
        Page<FunctionModule> modulePage = repository.findAll(pageable);
        List<FunctionModule> moduleList = repository.findAll();
        modulePage.forEach((k) -> {
            String rootFunctionId = k.getRootFunctionId().trim();
            if (rootFunctionId != null && !rootFunctionId.isEmpty()) {
                Optional<FunctionModule> functionModule = moduleList.stream().filter(u -> u.get_id().equals(rootFunctionId)).findFirst();
                if (functionModule.isPresent()) {
                    k.setRootFunctionName(functionModule.get().getModuleName());
                }
            }
        });
        return modulePage;
        //return repository.findAll(pageable);
    }

    public Optional<FunctionModule> findById(String id) {
        return repository.findById(id);
    }

    public String delete(String id, boolean delete) {
        List<FunctionModule> functionModules = repository.findByRootFunctionId(id);
        if (functionModules.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該選單已有下層選單資料\"}";
        }
        List<JobModules> jobModules = jobModulesRepository.findByModuleId(id);
        if (jobModules.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該選單已有職務權限資料\"}";
        }
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }
}
