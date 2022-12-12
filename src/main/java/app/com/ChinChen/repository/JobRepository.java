package app.com.ChinChen.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Job;

import java.util.List;

@RepositoryRestResource
public interface JobRepository extends MongoRepository<Job, String>{
    void deleteBy_idIn(List<String> ids);

    @Query(value = "{}", fields = "{ 'jobCode' : 1, 'jobName' : 1}")
    List<Job> findByForm();
}
