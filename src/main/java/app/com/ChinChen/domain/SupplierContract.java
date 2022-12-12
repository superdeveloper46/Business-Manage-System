package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class SupplierContract {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String supplierId; //廠商ID
    String workTypeId; //工項ID
    String workers; //出工人數
    String workStatus; //出工狀況
}
