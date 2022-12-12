package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Form;

import java.util.List;

@RepositoryRestResource
public interface FormSectionRepository extends MongoRepository<Form, String> {
  void deleteBy_idIn(List<String> ids);

  @Aggregation(pipeline={
    "{'$match': {_id: ?0}}",
    "{'$unwind': '$formSectionList'}", 
    "{'$sort': {'_id': 1, 'formSectionList.sort': 1}}", 
    "{'$group': {'_id': '$_id', 'formSectionList': {'$push': '$formSectionList'}}}"
	})
	Form getFormSections(String id);

  @Query(value = "{ '$and' : [{ '_id' : ?0}, { 'formSectionList' : { '$elemMatch' : { 'formSectionName' : ?1}}}]}")
  List<Form> findByIdAndSectionName(String id, String sectionName);

  @Query(value = "{ '$and' : [{ '_id' : ?0}, { 'formSectionList' : { '$elemMatch' : { 'sort' : ?1}}}]}")
  List<Form> findByIdAndSort(String id, int sort);


  @Query(value = "{ '$and' : [{ '_id' : ?0}, { 'formSectionList' : { '$elemMatch' : { 'formSectionName' : ?1, _id: {$ne: ?2}}}}]}")
  List<Form> findByIdAndSectionName(String id, String sectionName, String formSection_id);

  @Query(value = "{ '$and' : [{ '_id' : ?0}, { 'formSectionList' : { '$elemMatch' : { 'sort' : ?1,  _id: {$ne: ?2}}}}]}")
  List<Form> findByIdAndSort(String id, int sort, String formSection_id);


}
