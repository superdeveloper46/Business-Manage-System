package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.WorkType;

import java.util.List;

@RepositoryRestResource
public interface WorkTypeRepostiory extends MongoRepository<WorkType, String> {
    void deleteBy_idIn(List<String> ids);

    @Query(value = "{'enable': true}")
    List<WorkType> findAllByEnable();
}
