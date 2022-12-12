package app.com.ChinChen.repository;

import app.com.ChinChen.domain.PurchaseForm;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface PurchaseFormRepostiory extends MongoRepository<PurchaseForm, String> {
    @Query(value = "{}", fields = "{ 'workBeginTime' : 1,'workEndTime' : 1}")
    List<PurchaseForm> findAllByWorkEndTime();
}
