package app.com.ChinChen.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Permission;
import app.com.ChinChen.security.jwt.JwtUtils;
import app.com.ChinChen.service.PermissionService;

@RestController
@RequestMapping(path = {"/permission"})
public class PermissionController {
    private final PermissionService service;

    @Autowired
	JwtUtils jwtUtils;

    public PermissionController(PermissionService service) {
        this.service = service;
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<?> findAll(@PathVariable String moduleId, HttpServletRequest request) {
        String loginId = jwtUtils.getLoginId(request);
        List<Permission> list = service.findAll(loginId, moduleId);
        return ResponseEntity
                .ok()
                .body(list);
    }
}
