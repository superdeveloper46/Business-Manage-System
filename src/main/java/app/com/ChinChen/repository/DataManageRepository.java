package app.com.ChinChen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.DataManage;

import java.util.List;

@RepositoryRestResource
public interface DataManageRepository extends MongoRepository<DataManage, String> {

    @Query(value = "{'?0' : {$all: ?1}}")
    Page<DataManage> findFilter(String name, String[] value, Pageable pageable);

    @Query(value = "{$and: [{ 'manufacture_information.location': { $all: ?1 } } , { 'manufacture_type.workTypes': { $all: ?0 } } ]}")
    Page<DataManage> findAllFilter(String[] value, String[] value1, Pageable pageable);

    void deleteBy_idIn(List<String> ids);
}
