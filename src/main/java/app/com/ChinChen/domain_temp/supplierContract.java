package app.com.ChinChen.domain_temp;

import lombok.Data;

import java.util.Date;

@Data
public class supplierContract {
    String _id;
    String contractTypeName;
    String contractNo;
    String projectName;
    String projectShortName;
    String workTypeName;
    Date workBeginTime;
    Date workEndTime;
    Date contractDate;
    String purchaseManagerName;
    String bidAmount;
}
