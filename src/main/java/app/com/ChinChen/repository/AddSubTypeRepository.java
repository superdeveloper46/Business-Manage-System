package app.com.ChinChen.repository;

import app.com.ChinChen.domain.AddSubType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AddSubTypeRepository extends MongoRepository<AddSubType, String> {
}
