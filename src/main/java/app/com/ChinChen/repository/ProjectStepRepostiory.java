package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.ProjectStep;

import java.util.List;

@RepositoryRestResource
public interface ProjectStepRepostiory extends MongoRepository<ProjectStep, String> {
    void deleteBy_idIn(List<String> ids);
}
