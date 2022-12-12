package app.com.ChinChen.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.TaskStatus;
import app.com.ChinChen.service.TaskStatusService;

@RestController
@RequestMapping(path = {"/taskStatus"})
public class TaskStatusController {
    private final TaskStatusService service;
    public TaskStatusController(TaskStatusService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<TaskStatus> list = service.findAll();
        return ResponseEntity
                .ok()
                .body(list);
    }
}
