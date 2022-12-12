package app.com.ChinChen.repository;

import app.com.ChinChen.domain.OwnerPaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OwnerPaymentTypeRepository extends MongoRepository<OwnerPaymentType, String> {
}
