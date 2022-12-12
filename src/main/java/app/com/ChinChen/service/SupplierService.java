package app.com.ChinChen.service;

import app.com.ChinChen.domain.PurchaseForm;
import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain_temp.supplierCareList;
import app.com.ChinChen.domain_temp.supplierContract;
import app.com.ChinChen.library.DateCal;
import app.com.ChinChen.library.PageUtil;
import app.com.ChinChen.repository.ContractRepostiory;
import app.com.ChinChen.repository.PurchaseFormRepostiory;
import app.com.ChinChen.repository.SupplierRepostiory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class SupplierService {
    private final SupplierRepostiory repository;
    private final ContractRepostiory contractRepostiory;
    private final PurchaseFormRepostiory purchaseFormRepostiory;
    @Autowired
    private MongoTemplate mongoTemplate;

    public SupplierService(SupplierRepostiory repository, ContractRepostiory contractRepostiory, PurchaseFormRepostiory purchaseFormRepostiory) {
        this.repository = repository;
        this.contractRepostiory = contractRepostiory;
        this.purchaseFormRepostiory = purchaseFormRepostiory;
    }

    public Supplier save(Supplier data) {
        return repository.save(data);
    }

    public Page<Supplier> findAll(Pageable pageable, String[] locationName, String[] workTypeId) {
        if (locationName.length == 0 && workTypeId.length == 0) {
            return repository.findAll(pageable);
        }

        if (workTypeId.length == 0) {
            return repository.findFilter("LocationItem.locationName", locationName, pageable);
        }

        if (locationName.length == 0) {
            return repository.findFilter("workTypeIdList", workTypeId, pageable);
        }

        if (locationName.length != 0 && workTypeId.length != 0) {
            return repository.findAllFilter(locationName, workTypeId, pageable);
        }
        return null;
    }

    public Page<Supplier> findAll2(Pageable pageable, String[] locationName, String[] workTypeId) {
        List<Supplier> SupplierList = null;
        if (locationName.length == 0 && workTypeId.length == 0) {
            SupplierList = repository.findAll2();
            //return PageUtil.getPage(repository.findAll2(), pageable);
        } else if (locationName.length == 0) {
            SupplierList = repository.findAll2("workTypeIdList", workTypeId);
            //return PageUtil.getPage(repository.findAll2("workTypeIdList", workTypeId), pageable);
        } else if (workTypeId.length == 0) {
            SupplierList = repository.findAll2("locationItem.locationName", locationName);
            //return PageUtil.getPage(repository.findAll2("locationItem.locationName", locationName), pageable);
        } else {
            SupplierList = repository.findAll2(locationName, workTypeId);
        }
        Date nowDate = new Date();
        List<PurchaseForm> purchaseFormList = purchaseFormRepostiory.findAllByWorkEndTime();
        SupplierList.forEach((k) -> {
            List<String> purchaseFormIdList = k.getPurchaseFormId();
            for (String purchaseFormId : purchaseFormIdList) {
                if (purchaseFormId != null && !purchaseFormId.isEmpty()) {
                    Optional<PurchaseForm> purchaseForm = purchaseFormList.stream().filter(u -> u.get_id().equals(purchaseFormId)).findFirst();
                    if (purchaseForm.isPresent()) {
                        int compare = purchaseForm.get().getWorkEndTime().compareTo(nowDate);
                        if (compare > 0) {
                            k.setCooperation(k.getCooperation() + 1);
                        } else {
                            k.setImplement(k.getImplement() + 1);
                        }
                    }
                }
            }
        });
        return PageUtil.getPage(SupplierList, pageable);
    }

    public Optional<Supplier> findById(String id) {
        return repository.findById(id);
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }

    public List<Supplier> findAll() {
        return repository.findAll();
    }

    public List<Supplier> findAllByEnable() {
        return repository.findAllByEnable();
    }

    public List<Supplier> findIdAndName() {
        return repository.findByIdAndName();
    }

    public Page<supplierCareList> findSupplierCare(Pageable pageable, String[] locationName, String[] employeeId, String[] supplierId, String[] sDate, String[] eDate) {
        Date sdate = null;
        if (sDate.length != 0) {
            Instant instant = Instant.parse(sDate[0] + "T00:00:00.000Z");
            sdate = DateCal.DateAddHours(Date.from(instant), -8);
        }
        Date edate = null;
        if (sDate.length != 0) {
            Instant instant = Instant.parse(eDate[0] + "T23:59:59.999Z");
            edate = DateCal.DateAddHours(Date.from(instant), -8);
        }
        if (locationName.length == 0 && employeeId.length == 0 && supplierId.length == 0 && sDate.length == 0 && eDate.length == 0) {
            return PageUtil.getPage(repository.findSupplierCare(), pageable);
        }
        if (locationName.length == 0 && employeeId.length == 0 && supplierId.length == 0 && sDate.length != 0 && eDate.length != 0) {
            return PageUtil.getPage(repository.findSupplierCare(sdate, edate), pageable);
        }
        if (employeeId.length == 0 && supplierId.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("locationItem.locationName", locationName, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("locationItem.locationName", locationName), pageable);
        }
        if (locationName.length == 0 && supplierId.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId), pageable);
        }
        if (locationName.length == 0 && employeeId.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("_id", supplierId, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("_id", supplierId), pageable);
        }
        if (locationName.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId, "_id", supplierId, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId, "_id", supplierId), pageable);
        }
        if (employeeId.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("locationItem.locationName", locationName, "_id", supplierId, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("locationItem.locationName", locationName, "_id", supplierId), pageable);
        }
        if (supplierId.length == 0) {
            if (sDate.length != 0 && sDate.length != 0) {
                return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId, "locationItem.locationName", locationName, sdate, edate), pageable);
            }
            return PageUtil.getPage(repository.findSupplierCare("supplierCareList.employeeId", employeeId, "locationItem.locationName", locationName), pageable);
        }
        if (locationName.length != 0 && employeeId.length != 0 && supplierId.length != 0 && sDate.length != 0 && eDate.length != 0) {
            return PageUtil.getPage(repository.findSupplierCare(locationName, employeeId, supplierId, sdate, edate), pageable);
        }
        return PageUtil.getPage(repository.findSupplierCare(locationName, employeeId, supplierId), pageable);
    }

    public List<supplierContract> findSupplierContract(String supplierId) {
        return contractRepostiory.findSupplierContract(supplierId);
    }
}
