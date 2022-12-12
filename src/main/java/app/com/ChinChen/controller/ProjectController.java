package app.com.ChinChen.controller;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import app.com.ChinChen.domain.Job;
import app.com.ChinChen.domain.WorkType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;

import app.com.ChinChen.domain.Project;
import app.com.ChinChen.domain_temp.Project_Data;
import app.com.ChinChen.domain.EmployDetail;
import app.com.ChinChen.domain.Situation;
import app.com.ChinChen.service.ProjectService;
import app.com.ChinChen.service.SituationService;

@RestController
@RequestMapping(path = {"/project"})
public class ProjectController {
    private final ProjectService service;
    private final SituationService situationService;

    @Autowired
    MongoTemplate mongoTemplate;

    public ProjectController(ProjectService service, SituationService situationService) {
        this.service = service;
        this.situationService = situationService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Project data) throws URISyntaxException {
        Project result = service.save(data);
        return ResponseEntity
                .created(new URI("/Project/" + result.get_id()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
        @PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Project_Data> page = this.service.findAll2(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Project data) {
        Project exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    //get project by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable String id){
        Project_Data data = this.service.getProjectDataById(id);
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    //insert employDetail
    /*
     * {
     *      situationId: value,
     *      employeeId: value,
     *      rootEmployeeId: value
     * }
     */
    @PostMapping("/createEmployDetail/{projectId}")
    public ResponseEntity<?> create_employDetail(@PathVariable String projectId, @RequestBody EmployDetail data) {
        final Query query;
        final Update update = new Update(); 
        query = new Query(new Criteria().andOperator(
            Criteria.where("_id").is(projectId)
        ));
        update.push("employeeList", data);

        mongoTemplate.updateFirst(query, update, "project");

        HashMap<String, String> body = new HashMap<String, String>();
            body.put("status", "ok");
            body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }


    //delete employDetail
    @PostMapping("/deleteEmployDetail/{id}/{sub_id}")
    public ResponseEntity<?> delete_EmployDetail(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update; 
        query = new Query(new Criteria().andOperator(
            Criteria.where("_id").is(id),
            Criteria.where("employeeList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("employeeList", new 
                    BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "project");
        
        HashMap<String, String> body = new HashMap<String, String>();
            body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }


    @GetMapping("/situation")
    public ResponseEntity<?> getSituation(){
        List<Situation> data = this.situationService.findAll();
        return ResponseEntity
                    .ok()
                    .body(data);
    }

}
