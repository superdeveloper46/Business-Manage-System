package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain_temp.Menu2;

@RepositoryRestResource
public interface MenuRepository2 extends MongoRepository<Employee, String> {
	
	@Aggregation(pipeline={
		"{$match:{_id: ?0}}",
		"{$unwind:{path: '$jobList'}}",
		"{$lookup:{from: 'job',localField: 'jobList',foreignField: '_id',as: 'job'}}",
		"{$unwind:{path: '$job'}}",
		"{$lookup:{from: 'jobModules',let: {'job_id': '$jobList','job_id': '$job._id'},pipeline: [{$match: {$expr: {$and: [{$eq: ['$jobId','$$job_id']},{$eq: ['$showInMenu',true]}]}}}],as: 'jobModules'}}",
		"{$unwind:{path: '$jobModules'}}",
		"{$lookup:{from: 'functionModule',let: {'module_id': '$jobModules.moduleId'},pipeline: [{$match: {$expr: {$and: [{$eq: ['$_id','$$module_id']}]}}}],as: 'functionModule'}}",
		"{$unwind:'$functionModule'}",
		"{$sort:{'job.rank': 1,'functionModule.level': 1, 'jobModules.sort': 1}}",
		"{$group:{ _id: '$jobList', data:{$push: {_id: '$functionModule._id', parent_id: '$functionModule.rootFunctionId', sort: '$jobModules.sort', level: '$functionModule.level', name: '$functionModule.moduleName', path: '$functionModule.controllerName', iconClasses: '$functionModule.menuClass'}} }}",
	})
	List<Menu2> getMenu2(String emp_id);

}
