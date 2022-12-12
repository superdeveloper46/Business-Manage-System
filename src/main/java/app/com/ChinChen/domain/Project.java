package app.com.ChinChen.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "project")
public class Project {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String projectName; //專案名稱
    String projectShortName; //專案簡稱
    String projectNo; //專案編號
    String projectStepId; //專案階段ID
    List<ProjectStepRecord> projectStepList = new ArrayList<>(); //專案階段紀錄清單
    String projectGovNo; //政府採購編號
    locationAreaList locationAreaList = new locationAreaList();//專案範圍縣市 地區
    String projectManagerId; //專案主管ID(employee)
    String engineerManagerId; //工程主管(employee)
    String purchaseManagerId; //執行採發ID(employee)
    int bidAmount; //得標金額
    int changeAmount; //變更後契約金額
    List<ChangePrice> changePriceList = new ArrayList<>();//變更契約金額列表
    Date beginDate; //預計起始日
    Date endDate; //預定完成日
    List<EmployDetail> employeeList = new ArrayList<>(); //其他人員組
    String tenderImportant; //投標人員提醒本案重點
    String bossImportant; //上級主管提醒本案重點
    String constructionImg; //組織圖
    boolean isEnable = true; //是否啟用
    String companyId;
}

@Getter
@Setter
class locationAreaList {
    String locationName; // 縣市
    List<String> areaNameList = new ArrayList<String>(); // 地區
}
