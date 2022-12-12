package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "contract")
public class Contract {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String supplierId;//廠商ID
    String purchaseFormId;
    String contractNo;
    String inquiryDataId;
    String contractFile;
    String contractTypeId;
    Date contractDate;
    List<ContractDetail> contractDetailList = new ArrayList<>();
}
