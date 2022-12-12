package app.com.ChinChen.service;

import app.com.ChinChen.domain.Department;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.repository.DepartmentRepository;
import app.com.ChinChen.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private final DepartmentRepository repository;
    private final EmployeeRepository employeerepository;

    public DepartmentService(DepartmentRepository repository, EmployeeRepository employeerepository) {
        this.repository = repository;
        this.employeerepository = employeerepository;
    }

    public Department save(Department data) {
        return repository.save(data);
    }

    public Page<Department> findAll(Pageable pageable) {
        Page<Department> departmentPage = repository.findAll(pageable);
        List<Department> departmentList = repository.findAll();
        departmentPage.forEach((k) -> {
            String rootDepartmentId = k.getRootDepartmentId().trim();
            if (rootDepartmentId != null && !rootDepartmentId.isEmpty()) {
                Optional<Department> department = departmentList.stream().filter(u -> u.get_id().equals(rootDepartmentId)).findFirst();
                if (department.isPresent()) {
                    k.setRootDepartmentName(department.get().getDepName());
                }
            }
        });
        return departmentPage;
        // return repository.findAll(pageable);
    }

    public Optional<Department> findById(String id) {
        return repository.findById(id);
    }

    public List<Department> findAllByForm() {
        return repository.findAllByForm();
    }

    public String delete(String id, boolean delete) {
        List<Employee> employees = employeerepository.findByDepartmentId(id);
        if (employees.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該部門已有員工資料\"}";
        }
        List<Department> departments = repository.findByRootDepartmentId(id);
        if (departments.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該部門已有下層部門資料\"}";
        }
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }
}
