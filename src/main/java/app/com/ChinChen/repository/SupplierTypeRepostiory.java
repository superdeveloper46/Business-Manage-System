package app.com.ChinChen.repository;

import app.com.ChinChen.domain.SupplierType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplierTypeRepostiory extends MongoRepository<SupplierType, String> {
    void deleteBy_idIn(List<String> ids);
}
