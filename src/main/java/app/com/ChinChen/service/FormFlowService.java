package app.com.ChinChen.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Form;
import app.com.ChinChen.repository.FormFlowRepository;

@Service
public class FormFlowService {
    @Value("${server.port}")
    String port;
    private final FormFlowRepository repository;

    public FormFlowService(FormFlowRepository repository) {
        this.repository = repository;
    }

    public Form getFormFlows(String id) {
        return repository.getFormFlows(id);
    }

}
