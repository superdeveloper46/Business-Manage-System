package app.com.ChinChen.repository;

import app.com.ChinChen.domain.LicenseType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface LicenseTypeRepository extends MongoRepository<LicenseType, String> {
    void deleteBy_idIn(List<String> ids);
}
