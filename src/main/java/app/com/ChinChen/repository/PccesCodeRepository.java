package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.PccesCode;

import java.util.List;

@RepositoryRestResource
public interface PccesCodeRepository extends MongoRepository<PccesCode, String> {
 
  void deleteBy_idIn(List<String> ids);

  @Aggregation(pipeline={
		"{$match: {$or: [{_id: /^?0/}, {description: /^?0/}]}}",
    "{$sort: { _id: 1 }}",
		"{$limit: 300}"
	})
  List<PccesCode> find(String value);
}
