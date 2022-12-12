package app.com.ChinChen.controller;

import app.com.ChinChen.domain.FunctionModule;
import app.com.ChinChen.service.FunctionModuleService;
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
@RequestMapping(path = {"/functionModule"})
public class FunctionModuleController {
    private final FunctionModuleService service;

    public FunctionModuleController(FunctionModuleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody FunctionModule data) throws URISyntaxException {
        FunctionModule result = service.save(data);
        return ResponseEntity
                .created(new URI("/functionModule/" + result.get_id()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<FunctionModule> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        FunctionModule result = service.findById(id).orElse(new FunctionModule());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody FunctionModule data) {
        FunctionModule exist = service.findById(id).get();
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
