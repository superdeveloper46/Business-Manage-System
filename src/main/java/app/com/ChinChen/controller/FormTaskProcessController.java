package app.com.ChinChen.controller;


import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.com.ChinChen.domain.Department;
import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.FormTask;
import app.com.ChinChen.domain.FormTaskContent;
import app.com.ChinChen.domain.FormTaskSite;
import app.com.ChinChen.domain.InquiryData;
import app.com.ChinChen.domain.InquiryDetail;
import app.com.ChinChen.domain.PccesCode;
import app.com.ChinChen.domain.PccesData;
import app.com.ChinChen.domain.PurchaseForm;
import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain_temp.AlreadySigner;
import app.com.ChinChen.security.jwt.JwtUtils;
import app.com.ChinChen.service.DepartmentService;
import app.com.ChinChen.service.EmployeeService;
import app.com.ChinChen.service.FormTaskProcessService;
import app.com.ChinChen.service.FormTaskService;
import app.com.ChinChen.service.SupplierService;

@RestController
@RequestMapping(path = {"/formTaskProcess"})
public class FormTaskProcessController {
    private final FormTaskProcessService service;
    private final SupplierService supplierService;
    private final EmployeeService empService;
    private final DepartmentService depService;
    private final FormTaskService formTaskservice;
    public FormTaskProcessController(FormTaskProcessService service, FormTaskService formTaskservice, SupplierService supplierService, EmployeeService empService, DepartmentService depService) {
        this.service = service;
        this.supplierService = supplierService;
        this.empService = empService;
        this.depService = depService;
        this.formTaskservice = formTaskservice;
    }

    @Autowired
	JwtUtils jwtUtils;

    @PostMapping("/addScopeDescription")
    public ResponseEntity<?> create_scope(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String scopeDescription = data.get("scopeDescription");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        p.setEngineeringScope(scopeDescription);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addRuleDescription")
    public ResponseEntity<?> create_rule(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String ruleDescription = data.get("ruleDescription");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        p.setEngineeringSpec(ruleDescription);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }
    
    @PostMapping("/addIllustration")
    public ResponseEntity<?> create_illustration(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String illustration = data.get("illustration");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        p.setIllustration(illustration);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addCalculation")
    public ResponseEntity<?> create_Calculation(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String calculation = data.get("calculation");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        p.setCalculation(calculation);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addRemark")
    public ResponseEntity<?> create_Remark(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String remark = data.get("remark");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        p.setRemark(remark);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addPccesData/{formTaskId}")
    public ResponseEntity<?> create_PccesData(@PathVariable String formTaskId, @RequestBody PccesData data) throws URISyntaxException {

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<PccesData> l = p.getPccesDataList();
        if(l == null){
            l = new ArrayList<PccesData>();
        }
        l.add(data);
        p.setPccesDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/deletePccesData")
    public ResponseEntity<?> delete_PccesData(@RequestBody Map<String, String> data) throws URISyntaxException {

        String formTaskId = data.get("formTaskId");
        String id = data.get("id");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<PccesData> l = p.getPccesDataList();
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(id)){
                l.remove(i);
                break;
            }
        }
        p.setPccesDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @GetMapping("/getPccesCodeData/{value}")
    public ResponseEntity<?> getPccesCodeData(@PathVariable String value){
        List<PccesCode> data = service.findPccesCode(value);            
        return ResponseEntity
                    .ok()
                    .body(data);
    }

    @PostMapping("/updatePccesDataQuantity")
    public ResponseEntity<?> updatePccesDataQuantity(@RequestBody Map<String, String> data) throws URISyntaxException {

        String formTaskId = data.get("formTaskId");
        String id = data.get("id");
        String quantity = data.get("quantity");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<PccesData> l = p.getPccesDataList();
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(id)){
                l.get(i).setQuantity(Double.parseDouble(quantity));
                break;
            }
        }
        p.setPccesDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/editPurchaseForm/{formTaskId}")
    public ResponseEntity<?> editPurchaseForm(@PathVariable String formTaskId, @RequestBody Map<String, String> data) throws URISyntaxException, ParseException {

        String workTypeId = data.get("workTypeId");
        String emergencyTypeId = data.get("emergencyTypeId");
        String purchaseDeadLine = data.get("purchaseDeadLine");
        String workBeginTime = data.get("workBeginTime");
        String workEndTime = data.get("workEndTime");
        String recommendSupplierId = data.get("recommendSupplierId");
        String workers = data.get("workers");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        exist.setEmergencyTypeId(emergencyTypeId);

        p.setWorkTypeId(workTypeId);
        p.setPurchaseDeadLine(new SimpleDateFormat("yyyy-mm-dd").parse(purchaseDeadLine));
        p.setWorkBeginTime(new SimpleDateFormat("yyyy-mm-dd").parse(workBeginTime));
        p.setWorkEndTime(new SimpleDateFormat("yyyy-mm-dd").parse(workEndTime));
        p.setRecommendSupplierId(recommendSupplierId);
        p.setWorkers(Integer.parseInt(workers));
        
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addInquiryData/{formTaskId}/{supplierId}")
    public ResponseEntity<?> create_InquiryData(@PathVariable String formTaskId, @PathVariable String supplierId, @RequestBody Supplier data) throws URISyntaxException {

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<InquiryData> l = p.getInquiryDataList();

        HashMap<String, Object> body = new HashMap<String, Object>();

        boolean isNew = false;
        if(supplierId.equals("no")){
            isNew = true;
            data.setCodeName(data.getSupplierName().substring(0,1));
            Supplier saved = supplierService.save(data);
            supplierId = saved.get_id();
        }

        if(l == null){
            l = new ArrayList<InquiryData>();
        }

        for(int i=0; i<l.size(); i++){
            if(l.get(i).getSupplierId().equals(supplierId)){
                body.put("status", "already");
                body.put("data", exist);
                return ResponseEntity.ok().body(body);        
            }
        }

        InquiryData iData = new InquiryData();
        iData.setSupplierId(supplierId);
        iData.setNew(isNew);
        l.add(iData);
        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        body.put("status", "ok");
        body.put("data", exist);
        return ResponseEntity.ok().body(body);
    }

    @PostMapping("/addInquiryDetailData/{formTaskId}/{inquiryDataId}")
    public ResponseEntity<?> create_InquiryDetailData(@PathVariable String formTaskId, @PathVariable String inquiryDataId, @RequestBody Map<String, String> data) throws URISyntaxException {
        String prepayMoney = data.get("prepayMoney");
        String subtotal = data.get("subtotal");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<InquiryData> l = p.getInquiryDataList();
        
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(inquiryDataId)){
                l.get(i).setPrepayMoney(Double.parseDouble(prepayMoney));
                l.get(i).setSubtotal(Double.parseDouble(subtotal));

                break;
            }
        }
        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }


    @PostMapping("/addInquiryDetailPriceData/{formTaskId}/{inquiryDataId}/{inquiryDetailDataId}/{price}")
    public ResponseEntity<?> create_InquiryDetailData(@PathVariable String formTaskId, @PathVariable String inquiryDataId, @PathVariable String inquiryDetailDataId, @PathVariable String price) throws URISyntaxException {

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<InquiryData> l = p.getInquiryDataList();
        
        List<InquiryDetail> m = null;
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(inquiryDataId)){
                m = l.get(i).getInquiryDetailList();
        
                if(m == null){
                    m = new ArrayList<InquiryDetail>();
                    InquiryDetail inq = new InquiryDetail();
                    inq.setPccesId(inquiryDetailDataId);
                    inq.setPrice(Double.parseDouble(price));
                    m.add(inq);
                    l.get(i).setInquiryDetailList(m);
                }else{
                    int k=0;
                    for (int j=0; j<m.size(); j++) {
                        if(m.get(j).getPccesId().equals(inquiryDetailDataId)){
                            m.get(j).setPrice(Double.parseDouble(price));
                            l.get(i).setInquiryDetailList(m);
                            k++;
                            break;
                        }
                    }
                    if(k == 0){
                        InquiryDetail inq = new InquiryDetail();
                        inq.setPccesId(inquiryDetailDataId);
                        inq.setPrice(Double.parseDouble(price));
                        m.add(inq);
                        l.get(i).setInquiryDetailList(m);
                    }
                }
                
                break;
            }
        }

        
        

        

        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }


    @PostMapping("/deleteInquiryData")
    public ResponseEntity<?> delete_InquiryData(@RequestBody Map<String, String> data) throws URISyntaxException {

        String formTaskId = data.get("formTaskId");
        String id = data.get("id");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<InquiryData> l = p.getInquiryDataList();
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(id)){
                l.remove(i);
                break;
            }
        }
        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/addInquiryQuotation")
    public ResponseEntity<?> create_addInquiryQuotation(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String inquiryDataId = data.get("inquiryDataId");
        String quotation = data.get("quotation");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        
        List<InquiryData> l = p.getInquiryDataList();

        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(inquiryDataId)){
                l.get(i).setQuotation(quotation);
                break;
            }
        }
        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @PostMapping("/updateInquiryDataPriority")
    public ResponseEntity<?> updateInquiryDataPriority(@RequestBody Map<String, String> data) throws URISyntaxException {

        String formTaskId = data.get("formTaskId");
        String id = data.get("inquiryDataId");
        String priority = data.get("priority");

        FormTask exist = service.findById(formTaskId).get();
        PurchaseForm p = ((FormTaskContent)exist.getFormTaskContent()).getPurchaseForm();
        List<InquiryData> l = p.getInquiryDataList();
        for (int i=0; i<l.size(); i++) {
            if(l.get(i).get_id().equals(id)){
                l.get(i).setChoosePriority(Integer.parseInt(priority));
                break;
            }
        }
        p.setInquiryDataList(l);
        exist.getFormTaskContent().setPurchaseForm(p);
        this.service.save(exist);
        return ResponseEntity.ok().body(exist);
    }

    @GetMapping("/alreadySigners/{formTaskId}/{creatorId}")
    public ResponseEntity<?> getAlreadySigners(@PathVariable String formTaskId, @PathVariable String creatorId){

        List<AlreadySigner> send_data = new ArrayList<AlreadySigner>();
        Employee creator_emp = empService.findPickMan(creatorId).get(0);
        AlreadySigner creator = new AlreadySigner();
        creator.set_id(creator_emp.get_id());
        creator.setEmpName(creator_emp.getEmpName());
        creator.setDepartmentName(creator_emp.getDepartmentName());
        creator.setFormFlowId("");
        send_data.add(creator);
        
        List<AlreadySigner> data = this.formTaskservice.findAlreadySigners(formTaskId);
        for (AlreadySigner alreadySigner : data) {
            if(!alreadySigner.get_id().equals(creator.get_id()))
                send_data.add(alreadySigner);
        }
        
        return ResponseEntity
                .ok()
                .body(send_data);
    }

    @GetMapping("/nextSigners/{formTaskId}/{flowType}/{targetId}/{creatorId}")
    public ResponseEntity<?> getNextSigners(@PathVariable String formTaskId, @PathVariable String flowType, @PathVariable String targetId, @PathVariable String creatorId, HttpServletRequest request){

        List<Employee> data = null;
        String loginId = jwtUtils.getLoginId(request);

        if(flowType.equals("9")){
            data = formTaskservice.findSignerId(formTaskId, "purchaseManagerId");
        }else if(flowType.equals("8")){
            data = formTaskservice.findSignerId(formTaskId, "projectManagerId");
        }else if(flowType.equals("7")){
            data = formTaskservice.findSignerId(formTaskId, "engineerManagerId");
        }else if(flowType.equals("6")){
            data = formTaskservice.findCreator(formTaskId);
        }else if(flowType.equals("5")){
            data = this.empService.findPickMan(targetId);
        }else if(flowType.equals("4")){
            data = this.empService.findPickJob(targetId);
        }else if(flowType.equals("3")){
            data = this.empService.findPickDepartment(targetId);
        }else if(flowType.equals("2")){
            Employee emp = empService.findById(loginId).get();
            String depId = emp.getDepartmentId();
            data = this.getDirectSupervisor(depId);
        }else if(flowType.equals("1")){
            Employee emp = empService.findById(creatorId).get();
            String depId = emp.getDepartmentId();
            data = this.getDirectSupervisor(depId);
        }

        return ResponseEntity
                    .ok()
                    .body(data);
    }

    public String getParentDepartId(String depId){
        Department dep = depService.findById(depId).get();
        return dep.getRootDepartmentId();
    }

    public List<Employee> getDirectSupervisor(String depId){
        List<Employee> emp = this.empService.findDirectSupervisor(depId);
        if(emp == null || emp.size() == 0){
            String rootDepartmentId = this.getParentDepartId(depId);
            if(rootDepartmentId.equals(""))
                return emp;
            emp = getDirectSupervisor(rootDepartmentId);
        }else{
            return emp;
        }
        return emp;
    }

    @PostMapping("/formAction")
    public ResponseEntity<?> formAction(@RequestBody Map<String, String> data) throws URISyntaxException {
        String formTaskId = data.get("formTaskId");
        String previousFormTaskSiteId = data.get("previousFormTaskSiteId");
        String nextFormFlowId = data.get("nextFormFlowId");
        String comment = data.get("comment");
        String nextSigner = data.get("nextSigner");
        String action = data.get("action");

        FormTask exist = service.findById(formTaskId).get();
        List<FormTaskSite> p = exist.getTaskSiteList();

        if(p==null){
            p = new ArrayList<FormTaskSite>();
        }

        if(action.equals("send")){

            for(int i=0; i<p.size(); i++){
                if(p.get(i).get_id().equals(previousFormTaskSiteId)){
                    FormTaskSite n = p.get(i);
                    n.setFinishTime(new Date());
                    n.setResultComment(comment);
                    n.setCurrent(false);
                    n.setSiteResultId("result1"); 
                }
            }

            FormTaskSite n = new FormTaskSite();
            n.setFormFlowId(nextFormFlowId);
            n.setSigner(nextSigner);
            n.setStartTime(new Date());
            n.setCurrent(true);
            p.add(n);
            exist.setComment(comment);
            exist.setTaskStatusId("2022102718360002");

        }else if(action.equals("agree")){
            for(int i=0; i<p.size(); i++){
                if(p.get(i).get_id().equals(previousFormTaskSiteId)){
                    FormTaskSite n = p.get(i);
                    n.setFinishTime(new Date());
                    n.setResultComment(comment);
                    n.setCurrent(false);
                    n.setSiteResultId("result1"); 
                }
            }

            if(!nextSigner.equals("")){
                FormTaskSite n = new FormTaskSite();
                n.setFormFlowId(nextFormFlowId);
                n.setSigner(nextSigner);
                n.setStartTime(new Date());
                n.setCurrent(true);
                p.add(n);
                exist.setTaskStatusId("2022102718360002");
            }else{
                exist.setTaskStatusId("2022102718360004");  
                exist.setEndTime(new Date());
            }

        }else if(action.equals("reject")){
            for(int i=0; i<p.size(); i++){
                if(p.get(i).get_id().equals(previousFormTaskSiteId)){
                    FormTaskSite n = p.get(i);
                    n.setFinishTime(new Date());
                    n.setResultComment(comment);
                    n.setCurrent(false);
                    n.setSiteResultId("result2");
                }
            }

            exist.setTaskStatusId("2022102718360004");
            exist.setEndTime(new Date());
        }else if(action.equals("back")){
            for(int i=0; i<p.size(); i++){
                if(p.get(i).get_id().equals(previousFormTaskSiteId)){
                    FormTaskSite n = p.get(i);
                    n.setFinishTime(new Date());
                    n.setResultComment(comment);
                    n.setCurrent(false);
                    n.setSiteResultId("result3"); 
                }
            }

            FormTaskSite n = new FormTaskSite();
            n.setFormFlowId(nextFormFlowId);
            n.setSigner(nextSigner);
            n.setStartTime(new Date());
            n.setCurrent(true);
            p.add(n);
            exist.setTaskStatusId("2022102718360003");
        }

        exist.setTaskSiteList(p);
        this.service.save(exist);

        return ResponseEntity.ok().body(exist);
    }

}
