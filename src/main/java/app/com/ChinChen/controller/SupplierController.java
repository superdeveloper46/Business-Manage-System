package app.com.ChinChen.controller;

import app.com.ChinChen.domain.DailyReport;
import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain_temp.supplierCareList;
import app.com.ChinChen.domain_temp.supplierContract;
import app.com.ChinChen.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping(path = {"/supplier"})
public class SupplierController {
    private final SupplierService service;

    public SupplierController(SupplierService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Supplier data) throws URISyntaxException {
        Supplier result = service.save(data);
        return ResponseEntity
                .created(new URI("/supplier/" + result.get_id()))
                .body(result);
    }

    @PostMapping("/all")
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody Map<String, String[]> data) {
        String[] locationName = data.get("locationName");
        String[] workTypeId = data.get("workTypeId");
        Page<Supplier> page = service.findAll2(pageable, locationName, workTypeId);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @PostMapping("/findSupplierCare")
    public ResponseEntity<?> findSupplierCare(
            @PageableDefault(size = 10000, sort = "contentDate", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestBody Map<String, String[]> data) {
        String[] locationName = data.get("locationName");
        String[] employeeId = data.get("employeeId");
        String[] supplierId = data.get("supplierId");
        String[] sDate = data.get("sDate");
        String[] eDate = data.get("eDate");
        Page<supplierCareList> page = service.findSupplierCare(pageable, locationName, employeeId, supplierId,sDate, eDate);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Supplier result = service.findById(id).orElse(new Supplier());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<Supplier> list = service.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Supplier data) {
        Supplier exist = service.findById(id).get();
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
    @GetMapping("/findSupplierContract/{supplierId}")
    public ResponseEntity<?> findSupplierContract(@PathVariable String supplierId){
        List<supplierContract> data = this.service.findSupplierContract(supplierId);
        return ResponseEntity
                .ok()
                .body(data);
    }
}
