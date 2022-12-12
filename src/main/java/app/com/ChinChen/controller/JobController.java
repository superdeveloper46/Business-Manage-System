package app.com.ChinChen.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.com.ChinChen.domain.Job;
import app.com.ChinChen.service.JobService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/job"})
public class JobController {
    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Job data) throws URISyntaxException {
        Job result = service.save(data);
        return ResponseEntity
                .created(new URI("/job/" + result.get_id()))
                .body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Job data) {
        Job exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Job> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @GetMapping("/byForm")
    public ResponseEntity<?> findAllByForm() {
        List<Job> page = service.findAllByForm();
        return ResponseEntity
                .ok()
                .body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Job result = service.findById(id).orElse(new Job());
        return ResponseEntity
                .ok()
                .body(result);
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
