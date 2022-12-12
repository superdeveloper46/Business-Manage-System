package app.com.ChinChen.repository;

import java.util.List;

import app.com.ChinChen.domain.Employee;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Permission;

@RepositoryRestResource
public interface PermissionRepostiory extends MongoRepository<Employee, String> {
	@Aggregation(pipeline = {
			"{$match:{_id: ?0}}",
			"{$unwind:{path: '$jobList'}}",
			"{$lookup:{from: 'job',localField: 'jobList',foreignField: '_id',as: 'job'}}",
			"{$unwind:{path: '$job'}}",
			"{$lookup:{from: 'jobModules',let: {'job_id': '$jobList','job_id': '$job._id'},pipeline: [{$match: {$expr: {$and: [{$eq: ['$jobId','$$job_id']},{$eq: ['$showInMenu',true]}]}}}],as: 'jobModules'}}",
			"{$unwind:{path: '$jobModules'}}",
			"{$lookup:{from: 'functionModule',let: {'module_id': '$jobModules.moduleId'},pipeline: [{$match: {$expr: {$and: [{$eq: ['$_id','$$module_id']}]}}}],as: 'functionModule'}}",
			"{$unwind:'$functionModule'}",
			"{$sort:{'job.rank': 1,'functionModule.level': 1, 'jobModules.sort': 1}}",
			"{$match:{'jobModules.moduleId': ?1}}",
			"{$project: {'jobId': '$job._id', 'jobName': '$job.jobName', 'moduleId': '$functionModule._id', 'moduleName': '$functionModule.moduleName', 'insert': '$jobModules.insert', 'update': '$jobModules.update', 'delete': '$jobModules.delete'}}"
	})
	List<Permission> findAll(String loginId, String moduleId);
}
