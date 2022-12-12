package app.com.ChinChen.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.BasicDBObject;

import app.com.ChinChen.domain.ConstructionLogTypeDetail;
import app.com.ChinChen.domain.DailyReport;
import app.com.ChinChen.domain.Document;
import app.com.ChinChen.domain.MaterialManage;
import app.com.ChinChen.domain.SpecialConstraction;
import app.com.ChinChen.domain.SupplierContract;
import app.com.ChinChen.service.DailyReportService;

@RestController
@RequestMapping(path = {"/dailyReport"})
public class DailyReportController {
    private final DailyReportService service;

    @Autowired
    MongoTemplate mongoTemplate;

    public DailyReportController(DailyReportService service) {
        this.service = service;
    }

    //get dailyReport by ProjectId
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getByProjectId(@PathVariable String projectId) {
        List<DailyReport> data = this.service.findByProjectId(projectId);
        return ResponseEntity
                .ok()
                .body(data);
    }

    //get dailyReport by id
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        DailyReport data = this.service.findAllById(id);
        return ResponseEntity
                .ok()
                .body(data);
    }

    //insert DailyReport
    @PostMapping("/create")
    public ResponseEntity<?> create_DailyReport(@RequestBody DailyReport data) {
        DailyReport result = this.service.save(data);
        return ResponseEntity
                .ok()
                .body(result);
    }

    //delete DailyReport
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

    //insert logTypeDetailList
    @PostMapping("/createConstructionLogTypeDetail/{id}")
    public ResponseEntity<?> create_ConstructionLogTypeDetail(@PathVariable String id, @RequestBody ConstructionLogTypeDetail data) {
        final Query query;
        final Update update = new Update();
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        update.push("logTypeDetailList", data);

        mongoTemplate.updateFirst(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }

    //delete logTypeDetailList
    @PostMapping("/deleteConstructionLogTypeDetail/{id}/{sub_id}")
    public ResponseEntity<?> delete_ConstructionLogTypeDetail(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update;
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("logTypeDetailList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("logTypeDetailList", new
                BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }

    //insert supplierContractList
    @PostMapping("/createSupplierContract/{id}")
    public ResponseEntity<?> create_SupplierContract(@PathVariable String id, @RequestBody SupplierContract data) {
        final Query query;
        final Update update = new Update();
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        update.push("supplierContractList", data);

        mongoTemplate.updateFirst(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }

    //delete supplierContractList
    @PostMapping("/deleteSupplierContract/{id}/{sub_id}")
    public ResponseEntity<?> delete_SupplierContract(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update;
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("supplierContractList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("supplierContractList", new
                BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }

    //insert specialConstractionList
    @PostMapping("/createSpecialConstraction/{id}")
    public ResponseEntity<?> create_SpecialConstraction(@PathVariable String id, @RequestBody SpecialConstraction data) {
        final Query query;
        final Update update = new Update();
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        update.push("specialConstractionList", data);

        mongoTemplate.updateFirst(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }

    //delete specialConstractionList
    @PostMapping("/deleteSpecialConstraction/{id}/{sub_id}")
    public ResponseEntity<?> delete_SpecialConstraction(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update;
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("specialConstractionList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("specialConstractionList", new
                BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }

    //insert materialManageList
    @PostMapping("/createMaterialManage/{id}")
    public ResponseEntity<?> create_MaterialManage(@PathVariable String id, @RequestBody MaterialManage data) {
        final Query query;
        final Update update = new Update();
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        update.push("materialManageList", data);

        mongoTemplate.updateFirst(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }

    //delete materialManageList
    @PostMapping("/deleteMaterialManage/{id}/{sub_id}")
    public ResponseEntity<?> delete_MaterialManage(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update;
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("materialManageList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("materialManageList", new
                BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }

    //insert documentList
    @PostMapping("/createDocument/{id}")
    public ResponseEntity<?> create_Document(@PathVariable String id, @RequestBody Document data) {
        final Query query;
        final Update update = new Update();
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id)
        ));
        update.push("documentList", data);

        mongoTemplate.updateFirst(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        body.put("_id", data.get_id());
        return ResponseEntity
                .ok()
                .body(body);
    }

    //delete documentList
    @PostMapping("/deleteDocument/{id}/{sub_id}")
    public ResponseEntity<?> delete_Document(@PathVariable String id, @PathVariable String sub_id) {
        final Query query;
        Update update;
        query = new Query(new Criteria().andOperator(
                Criteria.where("_id").is(id),
                Criteria.where("documentList").elemMatch(Criteria.where("_id").is(sub_id))
        ));
        update = new Update().pull("documentList", new
                BasicDBObject("_id", sub_id));

        mongoTemplate.updateMulti(query, update, "dailyReport");

        HashMap<String, String> body = new HashMap<String, String>();
        body.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(body);
    }

}
