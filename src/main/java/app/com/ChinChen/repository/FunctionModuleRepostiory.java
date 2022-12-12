package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.FunctionModule;

import java.util.List;

@RepositoryRestResource
public interface FunctionModuleRepostiory extends MongoRepository<FunctionModule, String> {
    void deleteBy_idIn(List<String> ids);

    @Query(value = "{'rootFunctionId': ?0}", fields = "{ 'rootFunctionId' : 1}")
    List<FunctionModule> findByRootFunctionId(String rootFunctionId);
}
