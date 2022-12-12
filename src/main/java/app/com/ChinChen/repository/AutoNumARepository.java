package app.com.ChinChen.repository;

import app.com.ChinChen.domain.AutoNumA;
import app.com.ChinChen.domain_temp.ItemCodeList;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
@RepositoryRestResource
public interface AutoNumARepository extends MongoRepository<AutoNumA, String> {

    @Aggregation(pipeline = {
            "{$match:{$expr: {$eq: [{$strLenCP: \"$itemCode\"}, 5]}}}",
            "{$project:{itemCode: 1,cName: {$trim: {input: \"$cName\"}}}}"
    })
    List<ItemCodeList> findItemCode();
}
