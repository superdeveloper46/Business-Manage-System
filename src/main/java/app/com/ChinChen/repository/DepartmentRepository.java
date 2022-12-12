package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import app.com.ChinChen.domain.Department;

import java.util.List;

@RepositoryRestResource
public interface DepartmentRepository extends MongoRepository<Department, String> {
    void deleteBy_idIn(List<String> ids);

    @Query(value = "{}", sort = "{'depCode': 1, 'level': 1}")
    List<Department> findAllByForm();
    @Query(value = "{'rootDepartmentId': ?0}",fields = "{ 'rootDepartmentId' : 1}")
    List<Department> findByRootDepartmentId(String rootDepartmentId);
}
