package app.com.ChinChen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mongodb.BasicDBObject;

import app.com.ChinChen.domain.Department;
import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.Job;
import app.com.ChinChen.domain_temp.FormFlowInit;
import app.com.ChinChen.domain.Form;
import app.com.ChinChen.domain.FormFlow;
import app.com.ChinChen.payload.response.MessageResponse;
import app.com.ChinChen.service.DepartmentService;
import app.com.ChinChen.service.EmployeeService;
import app.com.ChinChen.service.FormFlowService;
import app.com.ChinChen.service.FormSectionService;
import app.com.ChinChen.service.JobService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/formFlow"})
public class FormFlowController {
    private final FormFlowService service;
    private final EmployeeService emp_service;
    private final DepartmentService dep_service;
    private final JobService job_service;
    private final FormSectionService section_service;

    @Autowired
    MongoTemplate mongoTemplate;

    public FormFlowController(FormFlowService service, FormSectionService section_service, EmployeeService emp_service, DepartmentService dep_service, JobService job_service) {
        this.service = service;
        this.emp_service = emp_service;
        this.dep_service = dep_service;
        this.job_service = job_service;
        this.section_service = section_service;
    }

    @GetMapping("/init/{id}")
    public ResponseEntity<?> findAllBasicData(@PathVariable String id){
        List<Employee> employees = this.emp_service.findAll();
        List<Job> jobs = this.job_service.findAllByForm();
        List<Department> deps = this.dep_service.findAllByForm();
        Form formSection =  this.section_service.getFormSections(id);
        Form formFlow = this.service.getFormFlows(id);

        FormFlowInit init = new FormFlowInit();
        init.setDeps(deps);
        init.setEmps(employees);
        init.setJobs(jobs);
        if(formSection == null){
            init.setFormSections(new ArrayList<>());
        }else{
            init.setFormSections(formSection.getFormSectionList());
        }

        if(formFlow == null){
            init.setFormFlows(new ArrayList<>());
        }else{
            init.setFormFlows(formFlow.getFormFlowList());
        }
        
        return ResponseEntity
                .ok()
                .body(init);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllFormFlow(@PathVariable String id){
        Form exist = service.getFormFlows(id);
        if(exist == null){
        return ResponseEntity
                .ok()
                .body(new ArrayList<>());
        }
        return ResponseEntity
                .ok()
                .body(exist.getFormFlowList());
    } 


    @PostMapping("/{type}/{id}")
    public ResponseEntity<?> insertorupdateFormFlow(@PathVariable String type, @PathVariable String id, @RequestBody FormFlow data){

        final Query query;
        final Update update = new Update(); 
        if(type.equals("add")){
            query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
            ));
            update.push("formFlowList", data);

        }else{
            query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("formFlowList").elemMatch(Criteria.where("_id").is(data.get_id()))
            ));
            update.set("formFlowList.$", data);
        }
        
        mongoTemplate.updateFirst(query, update, "form");
        return ResponseEntity
                .ok()
                .body(new MessageResponse(0, "ok"));

    }

    @PostMapping("/sort/{id}")
    public ResponseEntity<?> updateSort(@PathVariable String id, @RequestBody List<Map<String, String>> data){
        Query query;
        final Update update = new Update();
        for(int i=0; i<data.size(); i++){
            Map<String, String> rows = data.get(i);
            query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("formFlowList").elemMatch(Criteria.where("_id").is(rows.get("_id")))
            ));
            update.set("formFlowList.$.sort", Integer.valueOf(rows.get("sort")));
            
            mongoTemplate.updateFirst(query, update, "form");
        }
        
        return ResponseEntity
                .ok()
                .body(new MessageResponse(0, "ok"));

    }

    @DeleteMapping("/{formId}/{formFlowId}")
    public ResponseEntity<?> deleteFormFlow(@PathVariable String formId, @PathVariable String formFlowId){
        final Query query;
        Update update = new Update(); 

        query = new Query(new Criteria().andOperator(
            Criteria.where("_id").is(formId),
            Criteria.where("formFlowList").elemMatch(Criteria.where("_id").is(formFlowId))
        ));
        update = new Update().pull("formFlowList", new 
                     BasicDBObject("_id", formFlowId));

        mongoTemplate.updateMulti(query, update, "form");
        return ResponseEntity
                .ok()
                .body(new MessageResponse(0, "ok"));
        
    }


}

