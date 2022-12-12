package app.com.ChinChen.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Material;

@RepositoryRestResource
public interface MaterialRepostiory extends MongoRepository<Material, String> {

    @Query(value="{'projectId':?0}")
    List<Material> findAllByProjectId(String projectId);
}
