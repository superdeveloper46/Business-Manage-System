package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Form;

import java.util.List;

@RepositoryRestResource
public interface FormManageRepository extends MongoRepository<Form, String> {
  void deleteBy_idIn(List<String> ids);

  @Aggregation(pipeline={
    "{$lookup:{from: 'projectStep',localField: 'projectStepId',foreignField: '_id',as: 'projectStep'}}", 
    "{$project:{formName: 1,formCode: 1,projectStepId: 1,enable: 1,'projectStep.stepName': 1}}", 
    "{$unwind:'$projectStep'}",
    "{$sort:{formCode: 1}}"
	})
	List<Form> findAllData();

  @Aggregation(pipeline={
    "{'$match': {_id: ?0}}",
    "{'$unwind': '$formFlowList'}", 
    "{'$sort': {'_id': 1, 'formFlowList.sort': 1}}", 
    "{'$group': {'_id': '$_id', 'formFlowList': {'$push': '$formFlowList'}}}"
	})
	Form getFormFlows(String id);
	@Query(value = "{'projectStepId': ?0}", fields = "{ 'projectStepId' : 1}")
	List<Form> findByProjectStepId(String projectStepId);
}
