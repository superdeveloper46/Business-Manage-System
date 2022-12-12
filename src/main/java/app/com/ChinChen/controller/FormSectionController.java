package app.com.ChinChen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mongodb.BasicDBObject;

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.domain.FormSection;
import app.com.ChinChen.payload.response.MessageResponse;
import app.com.ChinChen.service.FormSectionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = {"/formSection"})
public class FormSectionController {
    private final FormSectionService service;

    @Autowired
    MongoTemplate mongoTemplate;

    public FormSectionController(FormSectionService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllFormSection(@PathVariable String id){
        Form exist = service.getFormSections(id);
        
        if(exist == null){
            return ResponseEntity
                    .ok()
                    .body(new ArrayList<>());    
        }
        return ResponseEntity
                .ok()
                .body(exist.getFormSectionList());
    } 

    @PostMapping("/{type}/{id}")
    public ResponseEntity<?> insertorupdateFormSection(@PathVariable String type, @PathVariable String id, @RequestBody FormSection data){

        final Query query;
        final Update update = new Update(); 
        if(type.equals("add")){

            List<Form> exist = service.findByIdAndSectionName(id, data.getFormSectionName());
            if(exist.size() != 0){
                return ResponseEntity
                    .ok()
                    .body(new MessageResponse(1, "fromSectionName is already exists"));
            }

            exist = service.findByIdAndSort(id, data.getSort());
            if(exist.size() != 0){
                return ResponseEntity
                    .ok()
                    .body(new MessageResponse(1, "Sort is already exists"));
            }

            query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
            ));
            update.push("formSectionList", data);

        }else{

            List<Form> exist = service.findByIdAndSectionName(id, data.getFormSectionName(), data.get_id());
            if(exist.size() != 0){
                return ResponseEntity
                    .ok()
                    .body(new MessageResponse(1, "fromSectionName is already exists"));
            }

            exist = service.findByIdAndSort(id, data.getSort(), data.get_id());
            if(exist.size() != 0){
                return ResponseEntity
                    .ok()
                    .body(new MessageResponse(1, "Sort is already exists"));
            }

            query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("formSectionList").elemMatch(Criteria.where("_id").is(data.get_id()))
            ));
            update.set("formSectionList.$", data);
        }
        
        mongoTemplate.updateFirst(query, update, "form");
        return ResponseEntity
                .ok()
                .body(new MessageResponse(0, "ok"));

    }

    @DeleteMapping("/{formId}/{formSectionId}")
    public ResponseEntity<?> deleteFormSection(@PathVariable String formId, @PathVariable String formSectionId){
        final Query query;
        Update update = new Update(); 

        query = new Query(new Criteria().andOperator(
            Criteria.where("_id").is(formId),
            Criteria.where("formSectionList").elemMatch(Criteria.where("_id").is(formSectionId))
        ));
        update = new Update().pull("formSectionList", new 
                     BasicDBObject("_id", formSectionId));

        mongoTemplate.updateMulti(query, update, "form");
        return ResponseEntity
                .ok()
                .body(new MessageResponse(0, "ok"));
        
    }

}

