package app.com.ChinChen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.SiteResult;
import app.com.ChinChen.service.SiteResultService;

@RestController
@RequestMapping(path = {"/siteResult"})
public class SiteResultController {
    private final SiteResultService service;
    public SiteResultController(SiteResultService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<SiteResult> list = service.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }
}
