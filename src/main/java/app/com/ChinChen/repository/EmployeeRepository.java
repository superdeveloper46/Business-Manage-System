package app.com.ChinChen.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.Employee;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    @Query(value = "{}", fields = "{'desc':0}")
    Page<Employee> getAll(Pageable pageable);

    Employee findByAccount(String account);

    void deleteBy_idIn(List<String> ids);

    //Employee findByAccount(String account);

    @Query(value = "{'departmentId': ?0}", fields = "{'empName':1, 'account': 1, 'isLock': 1}", sort = "{'birthday': 1}")
    List<Employee> findAllByDepartment(String dep_id);

    @Aggregation(pipeline = {
            "{$lookup:{from: \"department\",localField: \"departmentId\",foreignField: \"_id\",as: \"department\"}}",
            "{$lookup:{from: \"job\",localField: \"jobList\",foreignField: \"_id\", as: \"job\"}}",
            "{$unwind:$department}",
            "{$project:{empName: 1,isBoss:1,empGUID: 1,account: 1,password: 1,email: 1,createDate: 1,isLock: 1," +
                    "lockDate: 1,expireDate: 1,departmentId: 1,departmentName: \"$department.depName\"," +
                    "jobList: 1, jobListName: \"$job.jobName\"," +
                    "gender: 1,personalNo: 1,birthday: 1,onBoardDate: 1,phone: 1,address: 1," +
                    "expertise: 1,interest: 1,contactList: 1,licenseList: 1,EmployeePic: 1,employeeSeal: 1}}"
    })
    List<Employee> findAll2();

    @Aggregation(pipeline = {
            "{$match:{'licenseList.licenseType._id': ?0}}",
            "{$lookup:{from: \"department\",localField: \"departmentId\",foreignField: \"_id\",as: \"department\"}}",
            "{$lookup:{from: \"job\",localField: \"jobList\",foreignField: \"_id\", as: \"job\"}}",
            "{$unwind:$department}",
            "{$project:{empName: 1,isBoss:1,empGUID: 1,account: 1,password: 1,email: 1,createDate: 1,isLock: 1," +
                    "lockDate: 1,expireDate: 1,departmentId: 1,departmentName: \"$department.depName\"," +
                    "jobList: 1, jobListName: \"$job.jobName\"," +
                    "gender: 1,personalNo: 1,birthday: 1,onBoardDate: 1,phone: 1,address: 1," +
                    "expertise: 1,interest: 1,contactList: 1,licenseList: 1,EmployeePic: 1,employeeSeal: 1}}"
    })
    List<Employee> findAll2(String licenseTypeId);

    @Query(value = "{'departmentId': ?0}")
    List<Employee> findByDepartmentId(String departmentId);

    @Query(value = "{'jobList': ?0}", fields = "{ 'jobList' : 1}")
    List<Employee> findByJobList(String jobId);

    @Query(value = "{'licenseList.licenseType._id': ?0}", fields = "{ 'licenseList' : 1}")
    List<Employee> findByLicenseTypeId(String LicenseTypeId);

    @Aggregation(pipeline = {
            "{$lookup:{from: 'department',localField: 'departmentId',foreignField: '_id',as: 'department'}}",
            "{$unwind:$department}",
            "{$match:{_id: ?0}}",

    })
    Optional<Employee> findById(String empId);

    //
    @Aggregation(pipeline = {
            "{$lookup:{from: 'department',localField: 'departmentId',foreignField: '_id',as: 'department'}}",
            "{$unwind:$department}",
            "{$match:{_id: ?0}}",
            "{$project:{empName: 1,account:1,departmentName: '$department.depName'}}"
    })
    List<Employee> findPickMan(String employeeId);

    @Aggregation(pipeline = {
            "{$lookup:{from: 'department',localField: 'departmentId',foreignField: '_id',as: 'department'}}",
            "{$unwind:$department}",
            "{$match:{ 'jobList': { $all: ?0} }}",
            "{$project:{empName: 1,account:1,departmentName: '$department.depName'}}"
    })
    List<Employee> findPickJob(String[] jobId);


    @Aggregation(pipeline = {
            "{$lookup:{from: 'department',localField: 'departmentId',foreignField: '_id',as: 'department'}}",
            "{$unwind:$department}",
            "{$match:{departmentId: ?0}}",
            "{$project:{empName: 1,account:1,departmentName: '$department.depName'}}"
    })
    List<Employee> findPickDepartment(String depId);

    @Aggregation(pipeline = {
            "{$lookup:{from: 'department',localField: 'departmentId',foreignField: '_id',as: 'department'}}",
            "{$unwind:$department}",
            "{$match:{departmentId: ?0}}",
            "{$match:{isBoss: true}}",
            "{$project:{empName: 1,account:1,departmentName: '$department.depName'}}"
    })
    List<Employee> findDirectSupervisor(String depId);
}
