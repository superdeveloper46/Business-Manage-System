package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.FormTask;
import app.com.ChinChen.domain_temp.AlreadySigner;

import java.util.List;

@RepositoryRestResource
public interface FormTaskRepository extends MongoRepository<FormTask, String> {
  void deleteBy_idIn(List<String> ids);

  @Aggregation(pipeline={
    "{$lookup:{from: 'employee',localField: 'creatorId',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$lookup:{from: 'emergencyType',localField: 'emergencyTypeId',foreignField: '_id',as: 'emergencyType'}}",
    "{$unwind:'$emergencyType'}", 
    "{$lookup:{from: 'taskStatus',localField: 'taskStatusId',foreignField: '_id',as: 'taskStatus'}}",
    "{$unwind:'$taskStatus'}",
    "{$lookup:{from: 'workType',localField: 'formTaskContent.purchaseForm.workTypeId',foreignField: '_id',as: 'workType'}}",
    "{$unwind:'$workType'}",
    "{$lookup:{from: 'supplier',localField: 'formTaskContent.purchaseForm.recommendSupplierId',foreignField: '_id',as: 'supplier'}}",
    "{$unwind:{path: '$supplier',preserveNullAndEmptyArrays: true}}}",
    "{$match: {$or:[{formId: ?0, creatorId: ?1}, {'formId':?0, 'taskSiteList.isCurrent': true, 'taskSiteList.signer':?1}]}}",
    "{$sort: {_id: -1}}"
	}) 
  List<FormTask> findAll(String formId, String creatorId);

  @Aggregation(pipeline={
    "{$lookup:{from: 'employee',localField: 'creatorId',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$lookup:{from: 'emergencyType',localField: 'emergencyTypeId',foreignField: '_id',as: 'emergencyType'}}",
    "{$unwind:'$emergencyType'}", 
    "{$lookup:{from: 'taskStatus',localField: 'taskStatusId',foreignField: '_id',as: 'taskStatus'}}",
    "{$unwind:'$taskStatus'}",
    "{$lookup:{from: 'workType',localField: 'formTaskContent.purchaseForm.workTypeId',foreignField: '_id',as: 'workType'}}",
    "{$unwind:'$workType'}",
    "{$lookup:{from: 'supplier',localField: 'formTaskContent.purchaseForm.recommendSupplierId',foreignField: '_id',as: 'supplier'}}",
    "{$unwind:{path: '$supplier',preserveNullAndEmptyArrays: true}}}",
    "{$match: {'formId':?0, 'taskStatusId':?1, 'creatorId':?2}}",
    "{$sort: {_id: -1}}"
    
	}) 
  List<FormTask> findByTaskStatusId(String formId, String taskStatusId, String creatorId);

  @Aggregation(pipeline={
    "{$lookup:{from: 'employee',localField: 'creatorId',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$lookup:{from: 'emergencyType',localField: 'emergencyTypeId',foreignField: '_id',as: 'emergencyType'}}",
    "{$unwind:'$emergencyType'}", 
    "{$lookup:{from: 'taskStatus',localField: 'taskStatusId',foreignField: '_id',as: 'taskStatus'}}",
    "{$unwind:'$taskStatus'}",
    "{$lookup:{from: 'workType',localField: 'formTaskContent.purchaseForm.workTypeId',foreignField: '_id',as: 'workType'}}",
    "{$unwind:'$workType'}",
    "{$lookup:{from: 'supplier',localField: 'formTaskContent.purchaseForm.recommendSupplierId',foreignField: '_id',as: 'supplier'}}",
    "{$unwind:{path: '$supplier',preserveNullAndEmptyArrays: true}}}",
    "{$match: {'formId':?0, 'taskSiteList.isCurrent': true, 'taskSiteList.signer':?1}}",
    "{$sort: {_id: -1}}"
    
	}) 
  List<FormTask> findBySignerId(String formId, String signerId);

  @Aggregation(pipeline={
    "{$lookup:{from: 'employee',localField: 'creatorId',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$lookup:{from: 'emergencyType',localField: 'emergencyTypeId',foreignField: '_id',as: 'emergencyType'}}",
    "{$unwind:'$emergencyType'}", 
    "{$lookup:{from: 'taskStatus',localField: 'taskStatusId',foreignField: '_id',as: 'taskStatus'}}",
    "{$unwind:'$taskStatus'}",
    "{$lookup:{from: 'workType',localField: 'formTaskContent.purchaseForm.workTypeId',foreignField: '_id',as: 'workType'}}",
    "{$unwind:'$workType'}",
    "{$lookup:{from: 'supplier',localField: 'formTaskContent.purchaseForm.recommendSupplierId',foreignField: '_id',as: 'supplier'}}",
    "{$unwind:{path: '$supplier',preserveNullAndEmptyArrays: true}}}",
    "{$lookup:{from: 'form',localField: 'formId',foreignField: '_id',as: 'form'}}",
    "{$unwind:'$form'}",
    "{$match: {$or:[{_id: ?0, creatorId: ?1}, {'_id':?0, 'taskSiteList.isCurrent': true, 'taskSiteList.signer':?1}]}}",
    "{$unwind: {path: '$formTaskContent.purchaseForm.inquiryDataList',preserveNullAndEmptyArrays: true}}", 
    "{$lookup: {from: 'supplier',localField: 'formTaskContent.purchaseForm.inquiryDataList.supplierId',foreignField: '_id',as: 'formTaskContent.purchaseForm.inquiryDataList.supplier'}}", 
    "{$unwind: {path: '$formTaskContent.purchaseForm.inquiryDataList.supplier',preserveNullAndEmptyArrays: true}}", 
    "{$lookup: {from: 'contract',localField: 'formTaskContent.purchaseForm.inquiryDataList._id',foreignField: 'inquiryDataId',as: 'formTaskContent.purchaseForm.inquiryDataList.contract'}}", 
    "{$unwind: {path: '$formTaskContent.purchaseForm.inquiryDataList.contract',preserveNullAndEmptyArrays: true}}", 
    "{$group: {_id: '$_id',inquiryDataList: {$push: '$formTaskContent.purchaseForm.inquiryDataList'}, all: {$first: '$$ROOT'}}}", 
    "{$project: {_id:1, formId: '$all.formId', formTaskNo: '$all.formTaskNo', emergencyTypeId: '$all.emergencyTypeId', creatorId:'$all.creatorId', taskStatusId: '$all.taskStatusId', 'formTaskContent.purchaseForm._id': '$all.formTaskContent.purchaseForm._id',  'formTaskContent.purchaseForm.projectId':  '$all.formTaskContent.purchaseForm.projectId',  'formTaskContent.purchaseForm.workTypeId':  '$all.formTaskContent.purchaseForm.workTypeId', 'formTaskContent.purchaseForm.purchaseNo':  '$all.formTaskContent.purchaseForm.purchaseNo', 'formTaskContent.purchaseForm.workBeginTime':  '$all.formTaskContent.purchaseForm.workBeginTime', 'formTaskContent.purchaseForm.workEndTime':  '$all.formTaskContent.purchaseForm.workEndTime', 'formTaskContent.purchaseForm.purchaseDeadLine':'$all.formTaskContent.purchaseForm.purchaseDeadLine', 'formTaskContent.purchaseForm.recommendSupplierId': '$all.formTaskContent.purchaseForm.recommendSupplierId', 'formTaskContent.purchaseForm.supplierAmount': '$all.formTaskContent.purchaseForm.supplierAmount', 'formTaskContent.purchaseForm.workers': '$all.formTaskContent.purchaseForm.workers', 'formTaskContent.purchaseForm.pccesDataList': '$all.formTaskContent.purchaseForm.pccesDataList', 'formTaskContent.purchaseForm.inquiryDataList': '$inquiryDataList', 'formTaskContent.purchaseForm.engineeringScope': '$all.formTaskContent.purchaseForm.engineeringScope', 'formTaskContent.purchaseForm.engineeringSpec': '$all.formTaskContent.purchaseForm.engineeringSpec', 'formTaskContent.purchaseForm.illustration': '$all.formTaskContent.purchaseForm.illustration', 'formTaskContent.purchaseForm.calculation': '$all.formTaskContent.purchaseForm.calculation', 'formTaskContent.purchaseForm.remark': '$all.formTaskContent.purchaseForm.remark', 'employee': '$all.employee', 'department': '$all.department', 'emergencyType': '$all.emergencyType', 'taskStatus': '$all.taskStatus', 'workType': '$all.workType', 'supplier': '$all.supplier', 'form': '$all.form', 'comment': '$all.comment', 'taskSiteList': '$all.taskSiteList'}}"
	}) 
  FormTask findByFormTaskId(String formTaskId, String creatorId);

  @Aggregation(pipeline={
    "{$lookup:{from: 'employee',localField: 'creatorId',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$match:{_id: ?0}}",
    "{$project:{_id: '$employee._id', empName: '$employee.empName',account: '$employee.account',departmentName: '$department.depName'}}"
  })  
  List<Employee> findCreator(String formTaskId);

  @Aggregation(pipeline={
    "{$lookup:{from: 'project',localField: 'formTaskContent.purchaseForm.projectId',foreignField: '_id',as: 'project'}}",
    "{$unwind:'$project'}",
    "{$lookup:{from: 'employee',localField: 'project.?1',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$match:{_id: ?0}}",
    "{$project:{_id: '$employee._id', empName: '$employee.empName',account: '$employee.account',departmentName: '$department.depName'}}"
  })  
  List<Employee> findSignerId(String formTaskId, String field);

  @Aggregation(pipeline={
    "{$unwind:'$taskSiteList'}",
    "{$lookup:{from: 'employee',localField: 'taskSiteList.signer',foreignField: '_id',as: 'employee'}}",
    "{$unwind:'$employee'}",
    "{$lookup:{from: 'department',localField: 'employee.departmentId',foreignField: '_id',as: 'department'}}",
    "{$unwind:'$department'}",
    "{$match: {'_id':?0, 'taskSiteList.isCurrent': false, 'taskSiteList.siteResultId':'result1'}}",
    "{$project: {_id: '$employee._id', empName: '$employee.empName', departmentName: '$department.depName', formFlowId: '$taskSiteList.formFlowId'}}",
    "{$group:{_id: '$_id',empName: {$first: '$empName'}, departmentName: {$first: '$departmentName'}, formFlowId: {$first: '$formFlowId'}}}"
	})
  List<AlreadySigner> findAlreadySigners(String formTaskId);
  


}
