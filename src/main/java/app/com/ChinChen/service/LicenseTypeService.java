package app.com.ChinChen.service;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.LicenseType;
import app.com.ChinChen.repository.EmployeeRepository;
import app.com.ChinChen.repository.LicenseTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LicenseTypeService {
    private final LicenseTypeRepository repository;
    private final EmployeeRepository employeeRepository;

    public LicenseTypeService(LicenseTypeRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }

    public LicenseType save(LicenseType licenseType) {
        return repository.save(licenseType);
    }

    public Page<LicenseType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<LicenseType> findById(String id) {
        return repository.findById(id);
    }

    public String delete(String id, boolean delete) {
        List<Employee> employees = employeeRepository.findByLicenseTypeId(id);
        if (employees.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該證照類型已有員工資料\"}";
        }
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }
}
