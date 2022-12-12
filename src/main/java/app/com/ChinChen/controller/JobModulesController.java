package app.com.ChinChen.controller;

import app.com.ChinChen.domain.JobModules;
import app.com.ChinChen.service.JobModulesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = {"/jobModules"})
public class JobModulesController {
    private final JobModulesService service;

    public JobModulesController(JobModulesService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody JobModules data) throws URISyntaxException {
        JobModules result = service.save(data);
        return ResponseEntity
                .created(new URI("/jobModules/" + result.get_id()))
                .body(result);
    }

//    @GetMapping
//    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<JobModules> page = service.findAll(pageable);
//        return ResponseEntity
//                .ok()
//                .body(page.getContent());
//    }
    @PostMapping("/all")
    public ResponseEntity<?> findAll2(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable,@RequestBody Map<String, String> data) {
        Page<JobModules> page = service.findAll2(pageable,data);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        JobModules result = service.findById(id).orElse(new JobModules());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody JobModules data) {
        JobModules exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
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
