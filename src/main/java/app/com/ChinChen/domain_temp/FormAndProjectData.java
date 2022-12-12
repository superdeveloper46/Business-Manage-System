package app.com.ChinChen.domain_temp;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormAndProjectData{
    String formId;
    String projectId;
    String projectNo;
    String projectName;
    Date purchaseDeadLine;
    String depChineseCode;
}


