package app.com.ChinChen.service;

import app.com.ChinChen.domain.AutoNumA;
import app.com.ChinChen.domain.PccesCode;
import app.com.ChinChen.domain_temp.CodeSection;
import app.com.ChinChen.domain_temp.ItemCodeList;
import app.com.ChinChen.repository.AutoNumARepository;
import app.com.ChinChen.repository.AutoNumBRepository;
import app.com.ChinChen.repository.PccesCodeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PccesCodeService {
    private final PccesCodeRepository repository;
    private final AutoNumARepository autoNumARepository;
    private final AutoNumBRepository autoNumBRepository;

    public PccesCodeService(PccesCodeRepository repository, AutoNumARepository autoNumARepository, AutoNumBRepository autoNumBRepository) {
        this.repository = repository;
        this.autoNumARepository = autoNumARepository;
        this.autoNumBRepository = autoNumBRepository;
    }

    public PccesCode save(PccesCode data) {
        return repository.save(data);
    }

    public Page<PccesCode> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<PccesCode> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }

    public List<ItemCodeList> findItemCode() {
        return autoNumARepository.findItemCode();
    }

    public List<CodeSection> getCodeSection(String CodeSection, String ChapCode) {
        return autoNumBRepository.getCodeSection(ChapCode, CodeSection);
    }

    public List<CodeSection> getCodeSection(String CodeSection, String ChapCode, int selfRowValue) {
        return autoNumBRepository.getCodeSection(ChapCode, CodeSection, selfRowValue);
    }

}
