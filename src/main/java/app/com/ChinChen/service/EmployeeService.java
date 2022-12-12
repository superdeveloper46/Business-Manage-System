package app.com.ChinChen.service;

import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.library.AES;
import app.com.ChinChen.library.GetPassword;
import app.com.ChinChen.library.Mail;
import app.com.ChinChen.library.PageUtil;
import app.com.ChinChen.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Value("${server.port}")
    String port;
    @Autowired
    private final EmployeeRepository repository;
    @Autowired
    JavaMailSender mailSender;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee findByAccount(Employee employee) {
        return repository.findByAccount(employee.getAccount());
    }

    public Employee save(Employee employee) {
        String password = "";
        boolean isNewPassword = false;
        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            password = GetPassword.getRandomString(5);
            isNewPassword = true;
        }
        // 密碼加密
        AES aes = new AES();
        if (isNewPassword) {
            employee.setPassword(aes.encode(password));
        } else {
            employee.setPassword(aes.encode(employee.getPassword()));
        }
        Employee data = repository.save(employee);
        if (isNewPassword) {
            //寄信
            SendMailRun myRun = new SendMailRun(employee, password);
            Thread t = new Thread(myRun);
            t.start();
        }
        return data;
    }

    class SendMailRun implements Runnable {
        private Employee employee;
        private String password;

        public SendMailRun(Employee employee, String password) {
            this.employee = employee;
            this.password = password;
        }

        @Override
        public void run() {
            String text = employee.getEmpName() + " 您好：\n" +
                    "歡迎加入欽成營造這個大家庭，當您收到這封信件時，人資部已經將您的個人資料建置完成。並請您至以下連結進入管理系統\n" +
                    "http://59.125.142.83:8083/\n" +
                    "登入時，請您輸入以下資訊：\n" +
                    "帳號(員工帳號)：" + employee.getAccount() + "\n" +
                    "系統預設密碼：" + password + "\n\n" +
                    "登入系統後，請於右上角大頭點入『個人資料』，編輯您的密碼或完善其它相關資訊(大頭照、緊急連絡人、相關證照..)，以利後續公司的作業流程，欽成營造人資部關心您，謝謝!";
            List<String> mailList = new ArrayList<String>();
            mailList.add(employee.getEmail());
            new Mail().SendMail("欽成營造人資部", "欽成營造系統", text, mailList, mailSender);
        }
    }

    public Optional<Employee> findById(String id) {
        // 密碼解密
        Optional<Employee> employee = repository.findById(id);
        if (employee.isPresent()) {
            AES aes = new AES();
            employee.get().setPassword(aes.decode(employee.get().getPassword()));
        }
        return employee;
    }

    public Page<Employee> findAll(Pageable pageable, String licenseTypeId) {
        List<Employee> EmployeesList = null;
        if (licenseTypeId.isEmpty()) {
            EmployeesList = repository.findAll2();
        } else {
            EmployeesList = repository.findAll2(licenseTypeId);
        }
        Page<Employee> Employees = PageUtil.getPage(EmployeesList, pageable);
        // 密碼解密
        AES aes = new AES();
        Employees.forEach((k) -> {
            k.setPassword(aes.decode(k.getPassword()));
        });
        return Employees;
    }

    public Page<Employee> findAll2(Pageable pageable) {
        List<Employee> EmployeesList = repository.findAll2();
        Page<Employee> Employees = PageUtil.getPage(EmployeesList, pageable);
        // 密碼解密
        AES aes = new AES();
        Employees.forEach((k) -> {
            k.setPassword(aes.decode(k.getPassword()));
        });
        return Employees;
    }

    public void delete(String id) {
        List<String> ids = new ArrayList<String>();
        ids.add(id);
        repository.deleteBy_idIn(ids);
    }

    public List<Employee> findAllByDepartment(String dep_id) {
        return repository.findAllByDepartment(dep_id);
    }

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public List<Employee> findPickMan(String employeeId) {
        return repository.findPickMan(employeeId);
    }

    public List<Employee> findPickJob(String jobId) {
        String[] value = {jobId};
        return repository.findPickJob(value);
    }

    public List<Employee> findPickDepartment(String depId) {
        return repository.findPickDepartment(depId);
    }

    public List<Employee> findDirectSupervisor(String depId) {
        return repository.findDirectSupervisor(depId);
    }
}
