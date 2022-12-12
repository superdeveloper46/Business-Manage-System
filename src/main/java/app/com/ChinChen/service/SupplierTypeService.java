package app.com.ChinChen.service;

import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain.SupplierType;
import app.com.ChinChen.repository.SupplierRepostiory;
import app.com.ChinChen.repository.SupplierTypeRepostiory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierTypeService {
    private final SupplierTypeRepostiory repository;
    private final SupplierRepostiory supplierRepository;

    public SupplierTypeService(SupplierTypeRepostiory repository, SupplierRepostiory supplierRepository) {
        this.repository = repository;
        this.supplierRepository = supplierRepository;
    }

    public SupplierType save(SupplierType supplierType) {
        return repository.save(supplierType);
    }

    public Page<SupplierType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<SupplierType> findById(String id) {
        return repository.findById(id);
    }

    public String delete(String id, boolean delete) {
        List<Supplier> suppliers = supplierRepository.findBySupplierTypeId(id);
        if (suppliers.size() > 0) {
            return "{\"delete\":false,\"msg\":\"該廠商合作模式已有廠商資料\"}";
        }
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
        return id;
    }
}
