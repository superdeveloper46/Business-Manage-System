package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.RuleDescription;

import java.util.List;

@RepositoryRestResource
public interface RuleDescriptionRepostiory extends MongoRepository<RuleDescription, String> {
    void deleteBy_idIn(List<String> ids);
}
