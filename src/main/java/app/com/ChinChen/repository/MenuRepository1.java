package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Project;
import app.com.ChinChen.domain_temp.FormAndProjectData;
import app.com.ChinChen.domain_temp.Menu1;

@RepositoryRestResource
public interface MenuRepository1 extends MongoRepository<Project, String> {

	@Aggregation(pipeline = {
			"{$match:{$or: [{projectManagerId: ?0},{engineerManagerId: ?0},{purchaseManagerId: ?0},{'employeeList.employeeId': ?0} ]}}",
			"{$lookup:{from: 'projectStep', localField: 'projectStepId', foreignField: '_id', as: 'project_step'}}",
			"{$unwind: $project_step}",
			"{$project:{name: '$projectShortName', iconClasses: '$project_step.stepClass', projectStepId: 1}}",
			"{$lookup:{from: 'form', let: { ps_id: '$projectStepId'}, pipeline: [ { $match: { $expr: { $and: [{ $eq: [ '$projectStepId',  '$$ps_id' ]}, {$eq: [ '$enable', true ]}]}}}, {$group:{_id:'$formCode', name: {$first: '$formName'}, path:{$push: {$concat:['/formTask/', '$_id']}}}}, { $project: { name: 1, path: 1} }], as: 'children'}}",
			"{$sort: {_id: 1}}",
			"{$unwind: {path: '$children',preserveNullAndEmptyArrays: true}}",
			"{$sort: {'children._id': 1}}",
			"{'$group': {'_id': '$_id', 'name': {$first:'$name'}, 'iconClasses': {$first: '$iconClasses'}, 'children': {'$push': '$children'}}}"
	})
	List<Menu1> getMenu1(String emp_id);

	@Aggregation(pipeline = {
			"{$match:{$or: [{projectManagerId: ?0},{engineerManagerId: ?0},{purchaseManagerId: ?0},{'employeeList.employeeId': ?0} ]}}",
			"{$lookup:{from: 'projectStep', localField: 'projectStepId', foreignField: '_id', as: 'project_step'}}",
			"{$unwind: '$project_step'}",
			"{$lookup:{from: 'department', localField: 'companyId', foreignField: '_id', as: 'department'}}",
			"{$unwind: '$department'}",
			"{$lookup:{from: 'form', let: { ps_id: '$projectStepId'}, pipeline: [ { $match: { $expr: { $and: [{ $eq: [ '$projectStepId',  '$$ps_id' ]}, {$eq: [ '$enable', true ]}]}}}, ], as: 'children'}}",
			"{$unwind: '$children'}",
			"{$match: {'children._id': ?1}}",
			"{$project: {'projectId': '$_id', 'projectNo': 1, 'formId': '$children._id', 'projectName': '$projectName', 'purchaseDeadLine': '$endDate', 'depChineseCode': '$department.depChineseCode'}}"
	})
	FormAndProjectData getFormAndProjectData(String loginId, String formId);

}
