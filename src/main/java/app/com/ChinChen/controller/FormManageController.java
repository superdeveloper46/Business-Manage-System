package app.com.ChinChen.controller;

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

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.service.FormManageService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = {"/formManage"})
public class FormManageController {
    private final FormManageService service;

    @Autowired
    MongoTemplate mongoTemplate;

    public FormManageController(FormManageService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Form data) throws URISyntaxException {
        Form result = service.save(data);
        return ResponseEntity
                .created(new URI("/Form/" + result.get_id()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Form> data = service.findAll();
        return ResponseEntity
                .ok()
                .body(data);
    }    

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        Form result = service.findById(id).orElse(new Form());
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
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Form data){
        Form exist = service.findById(id).get();
        data.setFormSectionList(exist.getFormSectionList());
        data.setFormFlowList(exist.getFormFlowList());
        exist = data;
        exist.set_id(id);

        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }
}

