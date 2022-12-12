package app.com.ChinChen.controller;

import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Contract;
import app.com.ChinChen.service.ContractService;

@RestController
@RequestMapping(path = {"/contract"})
public class ContractController {
    private final ContractService service;
    public ContractController(ContractService service) {
        this.service = service;
    }

    @PostMapping("/createContractNo")
    public ResponseEntity<?> create(@RequestBody Contract data) throws URISyntaxException {
        Contract result = service.save(data);
        return ResponseEntity
                .ok()
                .body(result);
    }

    @PostMapping("/updateContractDetailData/{id}")
    public ResponseEntity<?> updateContractDetailData(@PathVariable String id, @RequestBody Contract data) throws URISyntaxException {

        Contract exist = service.findById(id).get();
        exist.setContractTypeId(data.getContractTypeId());
        exist.setContractDate(data.getContractDate());
        exist.setContractFile(data.getContractFile());
        exist.setContractDetailList(data.getContractDetailList());
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

}
