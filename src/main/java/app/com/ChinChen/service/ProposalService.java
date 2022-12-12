package app.com.ChinChen.service;

import java.util.List;

import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.Proposal;
import app.com.ChinChen.repository.ProposalRepostiory;

@Service
public class ProposalService {
    private final ProposalRepostiory repository;

    public ProposalService(ProposalRepostiory repository) {
        this.repository = repository;
    }

    public List<Proposal> findByProjectId(String projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public Proposal findById(String id) {
        return repository.findById(id).get();
    }

    public Proposal save(Proposal data) {
        return repository.save(data);
    }

    public List<Proposal> findAll() {
        return repository.findAll();
    }
    
    public void delete(String id) {
        repository.deleteById(id);
    }
}
