package app.com.ChinChen.repository;

import app.com.ChinChen.domain.ConstructionLogType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ConstructionLogTypeRepository extends MongoRepository<ConstructionLogType, String> {
}
