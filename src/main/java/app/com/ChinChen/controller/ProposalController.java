package app.com.ChinChen.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Proposal;
import app.com.ChinChen.service.ProposalService;

@RestController
@RequestMapping(path = {"/proposal"})
public class ProposalController {
    private final ProposalService service;
    public ProposalController(ProposalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Proposal> list = service.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }


    @PostMapping("/create")
    public ResponseEntity<?> create_proposal(@RequestBody Proposal data) {
        Proposal result = service.save(data);
        return ResponseEntity
                .ok()
                .body(result);
    }

    //get proposal by ProjectId
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getByProjectId(@PathVariable String projectId){
        List<Proposal> data = this.service.findByProjectId(projectId);
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    //get Proposal by id
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Proposal data = this.service.findById(id);
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    //update proposal
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update_proposal(@PathVariable String id, @RequestBody Proposal data) {

        Proposal exist = service.findById(id);
        exist = data;
        exist.set_id(id);
        this.service.save(exist);

        return ResponseEntity
                .ok()
                .body(exist);
    }

    //delete Proposal
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        try {
            service.delete(id);
            return ResponseEntity.ok().body(id);
        } catch (Exception e) {
            HashMap<String, String> body = new HashMap<String, String>();
            body.put("msg", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(body);
        }
    }
}
