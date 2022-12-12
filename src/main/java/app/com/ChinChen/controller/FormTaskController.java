package app.com.ChinChen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.ContractType;
import app.com.ChinChen.domain.EmergencyType;
import app.com.ChinChen.domain.FormTask;
import app.com.ChinChen.domain.RuleDescription;
import app.com.ChinChen.domain.ScopeDescription;
import app.com.ChinChen.domain.SiteResult;
import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain.TaskStatus;
import app.com.ChinChen.domain.WorkType;
import app.com.ChinChen.domain_temp.FormAndProjectData;
import app.com.ChinChen.domain_temp.FormTaskInit;
import app.com.ChinChen.repository.MenuRepository1;
import app.com.ChinChen.security.jwt.JwtUtils;
import app.com.ChinChen.service.ContractTypeService;
import app.com.ChinChen.service.EmergencyTypeService;
import app.com.ChinChen.service.FormTaskService;
import app.com.ChinChen.service.RuleDescriptionService;
import app.com.ChinChen.service.ScopeDescriptionService;
import app.com.ChinChen.service.SiteResultService;
import app.com.ChinChen.service.SupplierService;
import app.com.ChinChen.service.TaskStatusService;
import app.com.ChinChen.service.WorkTypeService;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = {"/formTask"})
public class FormTaskController {
    private final FormTaskService service;
    private final WorkTypeService workTypeService;
    private final EmergencyTypeService emergencyTypeService;
    private final SupplierService supplierService;
    private final TaskStatusService taskStatusService;
    private final ScopeDescriptionService scopeService;
    private final RuleDescriptionService ruleService;
    private final ContractTypeService contractTypeService;
    private final SiteResultService siteResultService;
    
	@Autowired
	JwtUtils jwtUtils;

    @Autowired
	MenuRepository1 menuRepo1;
    

    public FormTaskController(FormTaskService service, WorkTypeService workTypeService, EmergencyTypeService emergencyTypeService, SupplierService supplierService, TaskStatusService taskStatusService, ScopeDescriptionService scopeService, RuleDescriptionService ruleService, ContractTypeService contractTypeService, SiteResultService siteResultService) {
        this.service = service;
        this.workTypeService = workTypeService;
        this.emergencyTypeService = emergencyTypeService;
        this.supplierService = supplierService;
        this.taskStatusService = taskStatusService;
        this.scopeService = scopeService;
        this.ruleService = ruleService;
        this.contractTypeService = contractTypeService;
        this.siteResultService = siteResultService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FormTask data, HttpServletRequest request) {
        data.setCreatorId(jwtUtils.getLoginId(request));
        data.setTaskStatusId("2022102718360001");
        data.setBeginTime(new Date());
        FormTask result = service.save(data);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/filter/{formId}/{taskStatusId}")
    public ResponseEntity<?> findByTaskStatusId(@PathVariable String formId, @PathVariable String taskStatusId, HttpServletRequest request){
        String creatorId = jwtUtils.getLoginId(request);
        List<FormTask> data = null;
        if(taskStatusId.equals("all")){
            data = service.findAll(formId, creatorId);            
        }
        else if(taskStatusId.equals("2022102718360005")){
            data = service.findBySignerId(formId, creatorId);
        }
        else{
            data = service.findByTaskStatusId(formId, taskStatusId, creatorId);
        }
        return ResponseEntity
                    .ok()
                    .body(data);
    }


    @GetMapping("/detail/{formTaskId}/{taskStatusId}")
    public ResponseEntity<?> findByFormTaskId(@PathVariable String formTaskId, @PathVariable String taskStatusId, HttpServletRequest request){
        FormTask data = null;
        String creatorId = jwtUtils.getLoginId(request);
        
        data = service.findByFormTaskId(formTaskId, creatorId);
        return ResponseEntity
                    .ok()
                    .body(data);
        
    }
    

    @GetMapping("/init/{formId}")
    public ResponseEntity<?> findAllBasicData(@PathVariable String formId, HttpServletRequest request){
        String loginId = jwtUtils.getLoginId(request);
        List<WorkType> workTypes = this.workTypeService.findAllByEnable();
        List<EmergencyType> emergencyTypes = this.emergencyTypeService.findAll();
        List<Supplier> suppliers = this.supplierService.findAllByEnable();
        List<TaskStatus> taskStatus = this.taskStatusService.findAll();
        FormAndProjectData formAndProjectData = this.menuRepo1.getFormAndProjectData(loginId, formId);
        List<ScopeDescription> scope = this.scopeService.findAll();
        List<RuleDescription> rule = this.ruleService.findAll();
        List<ContractType> contractTypes = this.contractTypeService.findAll();
        List<SiteResult> siteResult = this.siteResultService.findAll();
        


        FormTaskInit init = new FormTaskInit();
        init.setWorkTypes(workTypes);
        init.setEmergencyTypes(emergencyTypes);
        init.setSuppliers(suppliers);
        init.setTaskStatus(taskStatus);
        init.setFormAndProjectData(formAndProjectData);
        init.setScope(scope);
        init.setRule(rule);
        init.setContractTypes(contractTypes);
        init.setSiteResult(siteResult);
        
        return ResponseEntity
                .ok()
                .body(init);

    }

}

