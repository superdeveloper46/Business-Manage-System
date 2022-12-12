package app.com.ChinChen.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Employee;

@RepositoryRestResource
public interface AuthRepositry extends MongoRepository<Employee, String> {
	
	@Query(value="{'account':?0}")
    Employee findByAccount(String account);

    @Query(value="{'account':?0, 'personalNo':?1}")
    Employee findByAccountAndPersonalNo(String account, String personNo);
}
