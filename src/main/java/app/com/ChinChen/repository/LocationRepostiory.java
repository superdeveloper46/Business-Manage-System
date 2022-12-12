package app.com.ChinChen.repository;

import app.com.ChinChen.domain.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LocationRepostiory extends MongoRepository<Location, String> {
}
