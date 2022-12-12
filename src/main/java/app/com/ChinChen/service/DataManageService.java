package app.com.ChinChen.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.com.ChinChen.domain.DataManage;
import app.com.ChinChen.repository.DataManageRepository;

import java.util.Optional;


@Service
public class DataManageService {
    @Value("${server.port}")
    String port;
    private final DataManageRepository repository;

    public DataManageService(DataManageRepository repository) {
        this.repository = repository;
    }

    public DataManage save(DataManage dataManage) {
        return repository.save(dataManage);
    }

    public Page<DataManage> findAll(Pageable pageable, String[] value, String[] value1) {
        
        if(value.length == 0 && value1.length == 0){
            return repository.findAll(pageable);
        }

        if(value1.length == 0){
            return repository.findFilter("manufacture_type.workTypes", value, pageable);
        }

        if(value.length == 0){
            return repository.findFilter("manufacture_information.location", value1, pageable);
        }

        if(value.length != 0 && value1.length != 0){
            return repository.findAllFilter(value, value1, pageable);
        }

        return null;
    }

    public Optional<DataManage> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
