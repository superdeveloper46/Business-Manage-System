package app.com.ChinChen.controller;

import app.com.ChinChen.domain.ProjectStep;
import app.com.ChinChen.service.ProjectStepService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = { "/projectStep" })
public class ProjectStepController {

    private final ProjectStepService service;

    public ProjectStepController(ProjectStepService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getProjectSteps() {
        List<ProjectStep> page = service.findAll();
        return ResponseEntity
                .ok()
                .body(page);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProjectStep data) throws URISyntaxException {
        ProjectStep result = service.save(data);
        return ResponseEntity
                .created(new URI("/projectStep/" + result.get_id()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProjectStep> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        ProjectStep result = service.findById(id).orElse(new ProjectStep());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ProjectStep data) {
        ProjectStep exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id, @RequestBody Map<String, Boolean> data) {
        try {
            boolean delete = data.get("delete");
            String result = service.delete(id, delete);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            HashMap<String, String> body = new HashMap<String, String>();
            body.put("msg", e.getMessage());
            return ResponseEntity.unprocessableEntity().body(body);
        }
    }
}
