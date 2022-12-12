package app.com.ChinChen.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Proposal;

@RepositoryRestResource
public interface ProposalRepostiory extends MongoRepository<Proposal, String> {

    @Query(value="{'projectId':?0}")
    List<Proposal> findAllByProjectId(String projectId);
}
