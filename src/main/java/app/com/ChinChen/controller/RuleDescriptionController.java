package app.com.ChinChen.controller;

import app.com.ChinChen.domain.RuleDescription;
import app.com.ChinChen.service.RuleDescriptionService;
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
@RequestMapping(path = {"/ruleDescription"})
public class RuleDescriptionController {
    private final RuleDescriptionService service;

    public RuleDescriptionController(RuleDescriptionService service) {
        this.service = service;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody RuleDescription data) throws URISyntaxException {
        RuleDescription result = service.save(data);
        return ResponseEntity
                .created(new URI("/ruleDescription/" + result.get_id()))
                .body(result);
    }
    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<RuleDescription> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        RuleDescription result = service.findById(id).orElse(new RuleDescription());
        return ResponseEntity
                .ok()
                .body(result);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody RuleDescription data){
        RuleDescription exist = service.findById(id).get();
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
