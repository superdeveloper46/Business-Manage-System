package app.com.ChinChen.repository;

import app.com.ChinChen.domain.AddSubScheduleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AddSubScheduleTypeRepository extends MongoRepository<AddSubScheduleType, String> {
}
