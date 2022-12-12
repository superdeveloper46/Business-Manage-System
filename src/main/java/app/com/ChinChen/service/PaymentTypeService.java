package app.com.ChinChen.service;

import app.com.ChinChen.domain.PaymentType;
import app.com.ChinChen.repository.PaymentTypeRepositry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentTypeService {
    private final PaymentTypeRepositry repository;

    public PaymentTypeService(PaymentTypeRepositry repository) {
        this.repository = repository;
    }
    public PaymentType save(PaymentType data) {
        return repository.save(data);
    }

    public Page<PaymentType> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<PaymentType> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }
}
