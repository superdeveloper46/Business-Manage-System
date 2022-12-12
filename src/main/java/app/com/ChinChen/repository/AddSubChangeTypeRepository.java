package app.com.ChinChen.repository;

import app.com.ChinChen.domain.AddSubChangeType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AddSubChangeTypeRepository extends MongoRepository<AddSubChangeType, String> {
}
