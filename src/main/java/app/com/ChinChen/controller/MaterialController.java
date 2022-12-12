package app.com.ChinChen.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Material;
import app.com.ChinChen.service.MaterialService;

@RestController
@RequestMapping(path = {"/material"})
public class MaterialController {
    private final MaterialService service;

    @Autowired
    MongoTemplate mongoTemplate;

    public MaterialController(MaterialService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create_material(@RequestBody Material data) {
        Material result = service.save(data);
        return ResponseEntity
                .ok()
                .body(result);
    }

    //get Material by ProjectId
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getByProjectId(@PathVariable String projectId){
        List<Material> data = this.service.findByProjectId(projectId);
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    //get Mateiral by id
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Material data = this.service.findById(id);
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    //update materail
    @PostMapping("/update/{id}")
    public ResponseEntity<?> update_material(@PathVariable String id, @RequestBody Material data) {

        Material exist = service.findById(id);
        exist = data;
        exist.set_id(id);
        this.service.save(exist);

        return ResponseEntity
                .ok()
                .body(exist);
    }

    //delete material
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
