package app.com.ChinChen.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
// @Document(collection = "supplierCare")
public class SupplierCare {
    // @Id
    // String _id =
    // LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    Date contentDate;
    String content;
    String employeeId;
    private List<String> empName = new ArrayList<String>();
}
