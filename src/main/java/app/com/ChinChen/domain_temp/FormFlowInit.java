package app.com.ChinChen.domain_temp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import app.com.ChinChen.domain.Department;
import app.com.ChinChen.domain.Employee;
import app.com.ChinChen.domain.FormFlow;
import app.com.ChinChen.domain.FormSection;
import app.com.ChinChen.domain.Job;

@Getter
@Setter
public class FormFlowInit {
    List<Employee> emps;
    List<Department> deps;
    List<Job> jobs;
    List<FormSection> formSections;
    List<FormFlow> formFlows;
}


