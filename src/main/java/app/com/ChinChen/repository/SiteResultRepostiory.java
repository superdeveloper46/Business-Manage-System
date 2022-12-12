package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.SiteResult;

@RepositoryRestResource
public interface SiteResultRepostiory extends MongoRepository<SiteResult, String> {
}
