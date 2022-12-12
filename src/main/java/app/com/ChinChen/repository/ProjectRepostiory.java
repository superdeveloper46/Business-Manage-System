package app.com.ChinChen.repository;

import app.com.ChinChen.domain.Project;
import app.com.ChinChen.domain_temp.Project_Data;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ProjectRepostiory extends MongoRepository<Project, String> {
    void deleteBy_idIn(List<String> ids);

    @Aggregation(pipeline = {
            "{$lookup:{from: \"employee\",localField: \"projectManagerId\",foreignField: \"_id\",as: \"projectManager\"}}",
            "{$lookup:{from: \"employee\",localField: \"engineerManagerId\",foreignField: \"_id\",as: \"engineerManager\"}}",
            "{$lookup:{from: \"employee\",localField: \"purchaseManagerId\",foreignField: \"_id\",as: \"purchaseManager\"}}",
            "{$lookup:{from: \"location\",localField: \"locationAreaList.locationName\",foreignField: \"_id\",as: \"locationObj\"}}",

            "{$unwind: {path: '$projectManager',preserveNullAndEmptyArrays: true}}",
            "{$unwind: {path: '$engineerManager',preserveNullAndEmptyArrays: true}}",
            "{$unwind: {path: '$purchaseManager',preserveNullAndEmptyArrays: true}}",
            "{$unwind: {path: '$locationObj',preserveNullAndEmptyArrays: true}}",
            "{$project:{projectNo:1,projectName:1,projectShortName:1,projectGovNo:1,bidAmount:1,"+
                       "locationAreaList:1,locationCName: \"$locationObj.locationName\","+
                       "beginDate:1,endDate:1,projectManagerId:1,projectManager:1,"+
                       "engineerManagerId:1,engineerManager:1,purchaseManagerId:1,purchaseManager:1,changePriceList:1}}",

    })
    List<Project_Data> findAll2();

    @Aggregation(pipeline = {
            "{$match:{'_id':?0}}",
            "{$lookup:{from: \"employee\",localField: \"projectManagerId\",foreignField: \"_id\",as: \"projectManager\"}}",
            "{$lookup:{from: \"employee\",localField: \"engineerManagerId\",foreignField: \"_id\",as: \"engineerManager\"}}",
            "{$lookup:{from: \"employee\",localField: \"purchaseManagerId\",foreignField: \"_id\",as: \"purchaseManager\"}}",
            "{$unwind: {path: '$projectManager',preserveNullAndEmptyArrays: true}}",
            "{$unwind: {path: '$engineerManager',preserveNullAndEmptyArrays: true}}",
            "{$unwind: {path: '$purchaseManager',preserveNullAndEmptyArrays: true}}",
    })
    Project_Data findDataById(String id);
}
