package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "supplierBankData")
public class SupplierBankData {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String bankId; //銀行ID
    String branchName; //分行名稱
    String accountNo; //帳號
    String userName; //戶名
    String bookUrl; //存摺封面檔案路徑
}
