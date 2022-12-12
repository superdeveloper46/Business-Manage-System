package app.com.ChinChen.repository;

import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain_temp.supplierCareList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Date;
import java.util.List;

public interface SupplierRepostiory extends MongoRepository<Supplier, String> {
    @Query(value = "{'?0' : {$in: ?1}}")
    Page<Supplier> findFilter(String name, String[] value, Pageable pageable);

    @Query(value = "{$or: [{ 'LocationItem.locationName': { $in: ?0 } } , { 'workTypeIdList': { $in: ?1 } } ]}")
    Page<Supplier> findAllFilter(String[] value, String[] value1, Pageable pageable);

    @Query(value = "{'enable': true}")
    List<Supplier> findAllByEnable();

    void deleteBy_idIn(List<String> ids);

    @Query(value = "{}", fields = "{ '_id' : 1, 'supplierName' : 1}")
    List<Supplier> findByIdAndName();

    @Aggregation(pipeline = {
            "{$lookup:{from: \"supplierType\",localField: \"supplierTypeId\",foreignField: \"_id\",as: \"supplierType\"}}",
            "{$lookup:{from: \"employee\",localField: \"employeeId\",foreignField: \"_id\",as: \"employee\"}}",
            "{$lookup:{from: \"workType\",localField: \"workTypeIdList\",foreignField: \"_id\",as: \"workType\"}}",
            "{$lookup:{from: \"bank\",localField: \"supplierBankData.bankId\",foreignField: \"_id\",as: \"bank\"}}",
            "{$lookup:{from: \"contract\",localField: \"_id\",foreignField: \"supplierId\",as: \"contract\"}}",
            "{$unwind:$supplierType}",
            "{$unwind:$employee}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$project:{supplierName: 1, businessNo: 1,codeName: 1, contactName: 1,contactPhone: 1, badRecord: 1," +
                    "email: 1, supplierTypeId: 1,supplierTypeName: \"$supplierType.typeName\",workersNum:1," +
                    "locationItem:1,address:1,workTypeIdList:1,workTypeNameList:\"$workType.workTypeName\"," +
                    "locationAreaList:1,employeeId:1,employeeName:\"$employee.empName\"," +
                    "remark: 1,supplierBankData: 1,disableDay: 1,disableRemark: 1,enable: 1,bankName:\"$bank.bankName\"," +
                    "supplierCareList: {contentDate: \"$supplierCareList.contentDate\",content: \"$supplierCareList.content\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",}" +
                    "contractDate: {$max: \"$contract.contractDate\"},purchaseFormId:\"$contract.purchaseFormId\"}}",
            "{'$group': {'_id': \"$_id\",'supplierName': {$first: \"$supplierName\"},'businessNo': {$first: \"$businessNo\"}," +
                    "'codeName': {$first: \"$codeName\"},'contactName': {$first: \"$contactName\"},'contactPhone': {$first: \"$contactPhone\"}," +
                    "'badRecord': {$first: \"$badRecord\"},'email': {$first: \"$email\"},'supplierTypeId': {$first: \"$supplierTypeId\"}," +
                    "'supplierTypeName': {$first: \"$supplierTypeName\"},'workersNum': {$first: \"$workersNum\"},'locationItem': {$first: \"$locationItem\"}," +
                    "'address': {$first: \"$address\"},'workTypeIdList': {$first: \"$workTypeIdList\"},'workTypeNameList': {$first: \"$workTypeNameList\"}," +
                    "'locationAreaList': {$first: \"$locationAreaList\"},'employeeId': {$first: \"$employeeId\"},'employeeName': {$first: \"$employeeName\"}," +
                    "'remark': {$first: \"$remark\"},'supplierBankData': {$first: \"$supplierBankData\"},'disableDay': {$first: \"$disableDay\"},'disableRemark': {$first: \"$disableRemark\"}," +
                    "'supplierCareList': {$push: \"$supplierCareList\"},'enable': { $first: \"$enable\" },'bankName': {$first: \"$bankName\"}," +
                    "'contractDate': {$first: \"$contractDate\"},'purchaseFormId': {$first: \"$purchaseFormId\"}}}"
    })
    List<Supplier> findAll2();

    @Aggregation(pipeline = {
            "{$match:{$and: [{'locationItem.locationName': {$in: ?0}},{workTypeIdList: {$in: ?1}}]}}",
            "{$lookup:{from: \"supplierType\",localField: \"supplierTypeId\",foreignField: \"_id\",as: \"supplierType\"}}",
            "{$lookup:{from: \"employee\",localField: \"employeeId\",foreignField: \"_id\",as: \"employee\"}}",
            "{$lookup:{from: \"workType\",localField: \"workTypeIdList\",foreignField: \"_id\",as: \"workType\"}}",
            "{$lookup:{from: \"bank\",localField: \"supplierBankData.bankId\",foreignField: \"_id\",as: \"bank\"}}",
            "{$lookup:{from: \"contract\",localField: \"_id\",foreignField: \"supplierId\",as: \"contract\"}}",
            "{$unwind:$supplierType}",
            "{$unwind:$employee}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$project:{supplierName: 1, businessNo: 1,codeName: 1, contactName: 1,contactPhone: 1, badRecord: 1," +
                    "email: 1, supplierTypeId: 1,supplierTypeName: \"$supplierType.typeName\",workersNum:1," +
                    "locationItem:1,address:1,workTypeIdList:1,workTypeNameList:\"$workType.workTypeName\"," +
                    "locationAreaList:1,employeeId:1,employeeName:\"$employee.empName\"," +
                    "remark: 1,supplierBankData: 1,disableDay: 1,disableRemark: 1,enable: 1,bankName:\"$bank.bankName\"," +
                    "supplierCareList: {contentDate: \"$supplierCareList.contentDate\",content: \"$supplierCareList.content\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",}" +
                    "contractDate: {$max: \"$contract.contractDate\"},purchaseFormId:\"$contract.purchaseFormId\"}}",
            "{'$group': {'_id': \"$_id\",'supplierName': {$first: \"$supplierName\"},'businessNo': {$first: \"$businessNo\"}," +
                    "'codeName': {$first: \"$codeName\"},'contactName': {$first: \"$contactName\"},'contactPhone': {$first: \"$contactPhone\"}," +
                    "'badRecord': {$first: \"$badRecord\"},'email': {$first: \"$email\"},'supplierTypeId': {$first: \"$supplierTypeId\"}," +
                    "'supplierTypeName': {$first: \"$supplierTypeName\"},'workersNum': {$first: \"$workersNum\"},'locationItem': {$first: \"$locationItem\"}," +
                    "'address': {$first: \"$address\"},'workTypeIdList': {$first: \"$workTypeIdList\"},'workTypeNameList': {$first: \"$workTypeNameList\"}," +
                    "'locationAreaList': {$first: \"$locationAreaList\"},'employeeId': {$first: \"$employeeId\"},'employeeName': {$first: \"$employeeName\"}," +
                    "'remark': {$first: \"$remark\"},'supplierBankData': {$first: \"$supplierBankData\"},'disableDay': {$first: \"$disableDay\"},'disableRemark': {$first: \"$disableRemark\"}," +
                    "'supplierCareList': {$push: \"$supplierCareList\"},'enable': { $first: \"$enable\" },'bankName': {$first: \"$bankName\"}," +
                    "'contractDate': {$first: \"$contractDate\"},'purchaseFormId': {$first: \"$purchaseFormId\"}}}"
    })
    List<Supplier> findAll2(String[] value, String[] value1);

    @Aggregation(pipeline = {
            "{$match:{$and: [{'?0': {$in: ?1}}]}}",
            "{$lookup:{from: \"supplierType\",localField: \"supplierTypeId\",foreignField: \"_id\",as: \"supplierType\"}}",
            "{$lookup:{from: \"employee\",localField: \"employeeId\",foreignField: \"_id\",as: \"employee\"}}",
            "{$lookup:{from: \"workType\",localField: \"workTypeIdList\",foreignField: \"_id\",as: \"workType\"}}",
            "{$lookup:{from: \"bank\",localField: \"supplierBankData.bankId\",foreignField: \"_id\",as: \"bank\"}}",
            "{$lookup:{from: \"contract\",localField: \"_id\",foreignField: \"supplierId\",as: \"contract\"}}",
            "{$unwind:$supplierType}",
            "{$unwind:$employee}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$project:{supplierName: 1, businessNo: 1,codeName: 1, contactName: 1,contactPhone: 1, badRecord: 1," +
                    "email: 1, supplierTypeId: 1,supplierTypeName: \"$supplierType.typeName\",workersNum:1," +
                    "locationItem:1,address:1,workTypeIdList:1,workTypeNameList:\"$workType.workTypeName\"," +
                    "locationAreaList:1,employeeId:1,employeeName:\"$employee.empName\"," +
                    "remark: 1,supplierBankData: 1,disableDay: 1,disableRemark: 1,enable: 1,bankName:\"$bank.bankName\"," +
                    "supplierCareList: {contentDate: \"$supplierCareList.contentDate\",content: \"$supplierCareList.content\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",}" +
                    "contractDate: {$max: \"$contract.contractDate\"},purchaseFormId:\"$contract.purchaseFormId\"}}",
            "{'$group': {'_id': \"$_id\",'supplierName': {$first: \"$supplierName\"},'businessNo': {$first: \"$businessNo\"}," +
                    "'codeName': {$first: \"$codeName\"},'contactName': {$first: \"$contactName\"},'contactPhone': {$first: \"$contactPhone\"}," +
                    "'badRecord': {$first: \"$badRecord\"},'email': {$first: \"$email\"},'supplierTypeId': {$first: \"$supplierTypeId\"}," +
                    "'supplierTypeName': {$first: \"$supplierTypeName\"},'workersNum': {$first: \"$workersNum\"},'locationItem': {$first: \"$locationItem\"}," +
                    "'address': {$first: \"$address\"},'workTypeIdList': {$first: \"$workTypeIdList\"},'workTypeNameList': {$first: \"$workTypeNameList\"}," +
                    "'locationAreaList': {$first: \"$locationAreaList\"},'employeeId': {$first: \"$employeeId\"},'employeeName': {$first: \"$employeeName\"}," +
                    "'remark': {$first: \"$remark\"},'supplierBankData': {$first: \"$supplierBankData\"},'disableDay': {$first: \"$disableDay\"},'disableRemark': {$first: \"$disableRemark\"}," +
                    "'supplierCareList': {$push: \"$supplierCareList\"},'enable': { $first: \"$enable\" },'bankName': {$first: \"$bankName\"}," +
                    "'contractDate': {$first: \"$contractDate\"},'purchaseFormId': {$first: \"$purchaseFormId\"}}}"
    })
    List<Supplier> findAll2(String name, String[] value);

    @Query(value = "{'supplierTypeId': ?0}", fields = "{ 'supplierTypeId' : 1}")
    List<Supplier> findBySupplierTypeId(String supplierTypeId);

    @Aggregation(pipeline = {
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare();
    @Aggregation(pipeline = {
            "{$match:{$and:[{'supplierCareList.contentDate':{$gte:?0}},{'supplierCareList.contentDate':{$lte:?1}}]}}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(Date sdate, Date edate);

    @Aggregation(pipeline = {
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'locationItem.locationName': {$in: ?0}},{'supplierCareList.employeeId': {$in: ?1}},{'_id': {$in: ?2}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String[] value, String[] value1, String[] value2);

    @Aggregation(pipeline = {
            "{$match:{$and:[{'supplierCareList.contentDate':{$gte:?3}},{'supplierCareList.contentDate':{$lte:?4}}]}}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'locationItem.locationName': {$in: ?0}},{'supplierCareList.employeeId': {$in: ?1}},{'_id': {$in: ?2}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String[] value, String[] value1, String[] value2, Date sdate, Date edate);

    @Aggregation(pipeline = {
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'?0': {$in: ?1}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String name, String[] value);
    @Aggregation(pipeline = {
            "{$match:{$and:[{'supplierCareList.contentDate':{$gte:?2}},{'supplierCareList.contentDate':{$lte:?3}}]}}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'?0': {$in: ?1}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String name, String[] value, Date sdate, Date edate);

    @Aggregation(pipeline = {
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'?0': {$in: ?1}},{'?2': {$in: ?3}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String name1, String[] value, String name2, String[] value1);
    @Aggregation(pipeline = {
            "{$match:{$and:[{'supplierCareList.contentDate':{$gte:?4}},{'supplierCareList.contentDate':{$lte:?5}}]}}",
            "{$unwind:$supplierCareList}",
            "{$lookup:{from: \"employee\",localField: \"supplierCareList.employeeId\",foreignField: \"_id\",as: \"employee2\"}}",
            "{$unwind:$employee2}",
            "{$match:{$and: [{'?0': {$in: ?1}},{'?2': {$in: ?3}}]}}",
            "{$project: { contentDate: \"$supplierCareList.contentDate\",supplierName: 1,locationName: \"$locationItem.locationName\",employeeId: \"$supplierCareList.employeeId\",empName: \"$employee2.empName\",content: \"$supplierCareList.content\"}}"
    })
    List<supplierCareList> findSupplierCare(String name1, String[] value, String name2, String[] value1, Date sdate, Date edate);

}
