package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Situation;

@RepositoryRestResource
public interface SituationRepostiory extends MongoRepository<Situation, String> {
}
