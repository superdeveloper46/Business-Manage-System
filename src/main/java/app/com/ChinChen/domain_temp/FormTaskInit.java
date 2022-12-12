package app.com.ChinChen.domain_temp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import app.com.ChinChen.domain.ContractType;
import app.com.ChinChen.domain.EmergencyType;
import app.com.ChinChen.domain.RuleDescription;
import app.com.ChinChen.domain.ScopeDescription;
import app.com.ChinChen.domain.SiteResult;
import app.com.ChinChen.domain.Supplier;
import app.com.ChinChen.domain.TaskStatus;
import app.com.ChinChen.domain.WorkType;

@Getter
@Setter
public class FormTaskInit {
    List<WorkType> workTypes;
    List<EmergencyType> emergencyTypes;
    List<Supplier> suppliers;
    List<TaskStatus> taskStatus;
    FormAndProjectData formAndProjectData;
    List<ScopeDescription> scope;
    List<RuleDescription> rule;
    List<ContractType> contractTypes;
    List<SiteResult> siteResult;
}


