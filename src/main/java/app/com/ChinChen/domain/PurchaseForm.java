package app.com.ChinChen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseForm {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String projectId; //專案ID
    String workTypeId; //工項ID
    String purchaseNo; //請採編號
    Date workBeginTime; //工項預計開始日
    Date workEndTime; //工項預計結束日
    Date purchaseDeadLine; //請採期限
    String recommendSupplierId; //推薦廠商
    int supplierAmount; //廠商推薦金額
    int workers; //應出工數

    List<PccesData> pccesDataList = new ArrayList<>(); //物料資料清單
    List<InquiryData> inquiryDataList = new ArrayList<>(); //報價清單

    String engineeringScope; //工程範圍檔案路徑
    String engineeringSpec; //工程規範檔案路徑
    String illustration; //採購範圍圖說檔案路徑
    String calculation; //採購數量計算式檔案路徑
    String remark; //備註
}