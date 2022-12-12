package app.com.ChinChen.repository;

import app.com.ChinChen.domain_temp.supplierContract;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Contract;

import java.util.List;

@RepositoryRestResource
public interface ContractRepostiory extends MongoRepository<Contract, String> {
    @Aggregation(pipeline = {
            "{$match:{supplierId:?0}}",
            "{$lookup:{from: \"contractType\",localField: \"contractTypeId\",foreignField: \"_id\",as: \"contractType\"}}",
            "{$lookup:{from: \"purchaseForm\",localField: \"purchaseFormId\",foreignField: \"_id\",as: \"purchaseForm\"}}",
            "{$lookup:{from: \"workType\",localField: \"purchaseForm.workTypeId\",foreignField: \"_id\",as: \"workType\"}}",
            "{$lookup:{from: \"project\",localField: \"purchaseForm.projectId\",foreignField: \"_id\",as: \"project\"}}",
            "{$lookup:{from: \"employee\",localField: \"project.purchaseManagerId\",foreignField: \"_id\",as: \"employee\"}}",
            "{$unwind:$purchaseForm}",
            "{$unwind:$project}",
            "{$project:{contractTypeName: \"$contractType.contractTypeName\",contractNo: 1," +
                    "projectName: \"$project.projectName\",projectShortName: \"$project.projectShortName\",workTypeName: \"$workType.workTypeName\"," +
                    "workBeginTime: \"$purchaseForm.workBeginTime\",workEndTime: \"$purchaseForm.workEndTime\"," +
                    "contractDate: 1,purchaseManagerName:\"$employee.empName\",bidAmount:\"$project.bidAmount\"}}",
            "{$unwind:$contractTypeName}",
            "{$unwind:$workTypeName}",
            "{$unwind:$purchaseManagerName}"
    })
    List<supplierContract> findSupplierContract(String supplierId);
}
