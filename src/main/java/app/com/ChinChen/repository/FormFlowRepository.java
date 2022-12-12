package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Form;

import java.util.List;

@RepositoryRestResource
public interface FormFlowRepository extends MongoRepository<Form, String> {
  void deleteBy_idIn(List<String> ids);

  @Aggregation(pipeline={
    "{'$match': {_id: ?0}}",
    "{'$unwind': '$formFlowList'}", 
    "{'$sort': {'_id': 1, 'formFlowList.sort': 1}}", 
    "{'$group': {'_id': '$_id', 'formFlowList': {'$push': '$formFlowList'}}}"
	})
	Form getFormFlows(String id);

}
