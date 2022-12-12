package app.com.ChinChen.repository;

import app.com.ChinChen.domain.PaymentType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PaymentTypeRepositry extends MongoRepository<PaymentType, String> {
    void deleteBy_idIn(List<String> ids);
}
