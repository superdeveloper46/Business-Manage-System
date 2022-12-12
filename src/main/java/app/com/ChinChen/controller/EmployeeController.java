package app.com.ChinChen.controller;

import app.com.ChinChen.library.AES;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.service.EmployeeService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = {"/employee"})
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/byDepartment/{dep_id}")
    public ResponseEntity<?> findAllByForm(@PathVariable String dep_id) {
        List<Employee> page = service.findAllByDepartment(dep_id);
        return ResponseEntity
                .ok()
                .body(page);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee data) throws URISyntaxException {
        Employee employee = service.findByAccount(data);
        if (employee != null) {
            return ResponseEntity.ok().body("{\"msg\":\"員工編號重複\"}");
        }
        Employee result = service.save(data);
        //密碼解密
        AES aes = new AES();
        result.setPassword(aes.decode(result.getPassword()));
        return ResponseEntity
                .created(new URI("/employee/" + result.get_id()))
                .body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Employee result = service.findById(id).orElse(new Employee());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Employee> page = service.findAll2(pageable);
        return ResponseEntity
                .ok()
                .body(page);
    }

    @PostMapping("/all")
    public ResponseEntity<?> findAll2(@PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable, @RequestBody Map<String, String> data) {
        String licenseTypeId = data.get("licenseTypeId");
        Page<Employee> page = service.findAll(pageable, licenseTypeId);
        return ResponseEntity
                .ok()
                .body(page);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Employee data) {
        Employee exist = service.findById(id).get();
        exist = data;
        exist.set_id(id);
        this.service.save(exist);
        //密碼解密
        AES aes = new AES();
        exist.setPassword(aes.decode(exist.getPassword()));
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
