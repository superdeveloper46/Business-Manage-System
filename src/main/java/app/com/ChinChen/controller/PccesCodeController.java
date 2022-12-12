package app.com.ChinChen.controller;

import app.com.ChinChen.domain.AutoNumA;
import app.com.ChinChen.domain.PccesCode;
import app.com.ChinChen.domain_temp.CodeSection;
import app.com.ChinChen.domain_temp.ItemCodeList;
import app.com.ChinChen.service.PccesCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = {"/pccesCode"})
public class PccesCodeController {
    private final PccesCodeService service;

    public PccesCodeController(PccesCodeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PccesCode data) throws URISyntaxException {
        PccesCode result = service.save(data);
        return ResponseEntity
                .created(new URI("/projectStep/" + result.get_id()))
                .body(result);
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10000, sort = "_id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PccesCode> page = service.findAll(pageable);
        return ResponseEntity
                .ok()
                .body(page.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        PccesCode result = service.findById(id).orElse(new PccesCode());
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody PccesCode data) {
        PccesCode exist = service.findById(id).get();
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

    @GetMapping("/findItemCode")
    public ResponseEntity<?> findItemCode() {
        List<ItemCodeList> result = service.findItemCode();
        return ResponseEntity
                .ok()
                .body(result);
    }

    @GetMapping("/getCodeSection/{CodeSection}/{ChapCode}")
    public ResponseEntity<?> getCodeSection(@PathVariable String CodeSection, @PathVariable String ChapCode) {
        List<CodeSection> result = service.getCodeSection(CodeSection, ChapCode);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getCodeSection/{CodeSection}/{ChapCode}/{SelfRow}")
    public ResponseEntity<?> getCodeSection(@PathVariable String CodeSection, @PathVariable String ChapCode, @PathVariable String SelfRow) {
        int selfRowValue = Integer.parseInt(SelfRow);
        List<CodeSection> result = service.getCodeSection(CodeSection, ChapCode, selfRowValue);
        return ResponseEntity.ok().body(result);
    }
}
