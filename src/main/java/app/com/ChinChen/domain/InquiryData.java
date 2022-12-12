package app.com.ChinChen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InquiryData {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String supplierId; //廠商ID
    boolean isNew; //既有廠商?
    double prepayMoney; //預付款
    List<InquiryDetail> inquiryDetailList = new ArrayList<>(); //報價內容明細
    String quotation; //報價單檔案路徑
    double subtotal; //總金額
    double choosePriority;//優先選擇
    Supplier supplier;
    Contract contract;
}
