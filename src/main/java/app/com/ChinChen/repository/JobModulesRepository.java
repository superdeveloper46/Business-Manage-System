package app.com.ChinChen.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.JobModules;

import java.util.List;

@RepositoryRestResource
public interface JobModulesRepository extends MongoRepository<JobModules, String> {
    void deleteBy_idIn(List<String> ids);

    @Aggregation(pipeline = {
            "{$match:{jobId: ?0}}",
            "{$lookup:{ from: 'job',localField: 'jobId',foreignField: '_id',as: 'job'}}",
            "{$lookup:{from:\"functionModule\",localField: \"moduleId\",foreignField: \"_id\",as: \"functionModule\"}}",
            "{$lookup:{from:\"functionModule\",localField: \"functionModule.rootFunctionId\",foreignField: \"_id\",as: \"functionModule2\"}}",
            "{$unwind:$job}",
            "{$unwind:$functionModule}",
            "{$project:{jobId: 1,jobName: \"$job.jobName\",moduleId: 1," +
                    "moduleName: \"$functionModule.moduleName\"," +
                    "rootFunctionName: \"$functionModule2.moduleName\"," +
                    "insert: 1,update: 1,delete: 1,showInMenu: 1,sort: 1}}"
    })
    List<JobModules> findAll2(String jobId);

    @Aggregation(pipeline = {
            "{$lookup:{from:'job',localField: 'jobId',foreignField: '_id',as: 'job'}}",
            "{$lookup:{from:\"functionModule\",localField: \"moduleId\",foreignField: \"_id\",as: \"functionModule\"}}",
            "{$lookup:{from:\"functionModule\",localField: \"functionModule.rootFunctionId\",foreignField: \"_id\",as: \"functionModule2\"}}",
            "{$unwind:$job}",
            "{$unwind:$functionModule}",
            "{$project:{jobId: 1,jobName: \"$job.jobName\",moduleId: 1," +
                    "moduleName: \"$functionModule.moduleName\"," +
                    "rootFunctionName: \"$functionModule2.moduleName\"," +
                    "insert: 1,update: 1,delete: 1,showInMenu: 1,sort: 1}}"
    })
    List<JobModules> findAll2();

    @Query(value = "{'jobId': ?0}", fields = "{ 'jobId' : 1}")
    List<JobModules> findByJobId(String jobId);

    @Query(value = "{'moduleId': ?0}", fields = "{ 'moduleId' : 1}")
    List<JobModules> findByModuleId(String moduleId);
}
