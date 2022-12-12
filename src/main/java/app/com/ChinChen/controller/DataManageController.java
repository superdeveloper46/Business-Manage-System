package app.com.ChinChen.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.com.ChinChen.domain.DataManage;
import app.com.ChinChen.service.DataManageService;

import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = {"/dataManage"})
public class DataManageController {
    private final DataManageService service;

    public DataManageController(DataManageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DataManage data) throws URISyntaxException {
        DataManage result = service.save(data);
        return ResponseEntity
                .created(new URI("/dataManage/" + result.get_id()))
                .body(result);
    }

    @PostMapping("/all")
    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable, @RequestBody Map<String, String[]> data){
        String[] value = data.get("value");
        String[] value1 = data.get("value1");
        Page<DataManage> page = service.findAll(pageable, value, value1);
        
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    // @GetMapping
    // public ResponseEntity<?> findAll_(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable){
    //     Page<DataManage> page = service.findAll(pageable);
    //     return ResponseEntity
    //             .ok()
    //             .body(page.getContent());
    // }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        DataManage result = service.findById(id).orElse(new DataManage());
        return ResponseEntity
                .ok()
                .body(result);
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

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody DataManage data){
        DataManage exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }
}
