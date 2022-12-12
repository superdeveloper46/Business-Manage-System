package app.com.ChinChen.controller;

import app.com.ChinChen.domain.ProjectType;
import app.com.ChinChen.service.ProjectTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
@RequestMapping(path = {"/projectType"})
public class ProjectTypeController {
    private final ProjectTypeService service;
    public ProjectTypeController(ProjectTypeService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectType data) throws URISyntaxException {
        ProjectType result = service.save(data);
        return ResponseEntity
                .created(new URI("/projectType/" + result.get_id()))
                .body(result);
    }
    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<ProjectType> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        ProjectType result = service.findById(id).orElse(new ProjectType());
        return ResponseEntity
                .ok()
                .body(result);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ProjectType data){
        ProjectType exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }
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
