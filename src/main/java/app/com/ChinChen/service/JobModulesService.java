package app.com.ChinChen.service;

import app.com.ChinChen.domain.FunctionModule;
import app.com.ChinChen.domain.JobModules;
import app.com.ChinChen.library.PageUtil;
import app.com.ChinChen.repository.FunctionModuleRepostiory;
import app.com.ChinChen.repository.JobModulesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JobModulesService {
    private final JobModulesRepository repository;
    private final FunctionModuleRepostiory repository2;

    public JobModulesService(JobModulesRepository repository, FunctionModuleRepostiory repository2) {
        this.repository = repository;
        this.repository2 = repository2;
    }

    public JobModules save(JobModules data) {
        return repository.save(data);
    }

    public Page<JobModules> findAll(Pageable pageable) {
        Page<JobModules> jobModulesPage = repository.findAll(pageable);
        List<FunctionModule> moduleList = repository2.findAll();
        jobModulesPage.forEach((k) -> {
            String moduleId = k.getModuleId().trim();
            if (moduleId != null &&!moduleId.isEmpty()) {
                Optional<FunctionModule> functionModule = moduleList.stream().filter(u -> u.get_id().equals(moduleId)).findFirst();
                if (functionModule.isPresent()) {
                    k.setModuleName(functionModule.get().getModuleName());
                    String rootFunctionId = functionModule.get().getRootFunctionId().trim();
                    if (rootFunctionId != null &&!rootFunctionId.isEmpty()) {
                        Optional<FunctionModule> functionModule2 = moduleList.stream().filter(u -> u.get_id().equals(rootFunctionId)).findFirst();
                        if (functionModule2.isPresent()) {
                            //k.setRootFunctionName(functionModule2.get().getModuleName());
                        }
                    }
                }
            }
        });
        return jobModulesPage;
        //return repository.findAll(pageable);
    }

    public Page<JobModules> findAll2(Pageable pageable, Map<String, String> data) {
        String jobId = data.get("jobId");
        List<JobModules> jobModulesList = new ArrayList<JobModules>();
        if (!jobId.isEmpty()) {
            jobModulesList = repository.findAll2(jobId);
        } else {
            jobModulesList = repository.findAll2();
        }
        Page<JobModules> jobModulesPage = PageUtil.getPage(jobModulesList, pageable);
        return jobModulesPage;
    }

    public Optional<JobModules> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
