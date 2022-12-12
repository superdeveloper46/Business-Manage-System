package app.com.ChinChen.domain;

import app.com.ChinChen.domain_temp.Project_Data;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "dailyReport")
public class DailyReport {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String projectId; //專案ID
    Project_Data project;
    Date dailyDate; //今日日期
    String morningWeather; //上午天氣
    String afternoonWeather; //下午天氣
    String company; //承攬廠商
    String contractChange; //契約變更次數
    String extendDays; //展延天數
    String expectComplete; //當日預定完成%
    String actualComplete; //當日實際完成%
    String dailyConcept; //依施工計畫書執行按圖施工概況
    String sampleTestRecord; //施工取樣試驗紀錄
    String tellSupplier; //通知協力廠商辦理事項
    String importantReport; //重要事項紀錄
    boolean isProfessional; //本日施工項目符合「營造業專業工程..
    boolean isEducation; //施工前有實施勤前教育
    boolean isInsurance; //施工前有確認新進勞工是否提報勞工保險
    boolean isProtect; //施工前有檢查勞工個人防護具
    boolean isDayNightCheck; //交通維持設施完成日間及夜間巡查
    List<ConstructionLogTypeDetail> logTypeDetailList = new ArrayList<>();
    ; //工別機具紀錄清單
    String attendSituation; //工務的出勤狀況
    String todayItem; //本日施做工項
    String tomorrowItem; //明日施做工項
    String bossSuggest; //上級提醒注意事項
    String integrationReview; //介面整合檢討事項
    String waitToSlove; //待解決問題
    boolean isNewTeam; //本日新工班進場
    boolean isRead; //工地導讀合約
    boolean isChange; //本日異動內容
    boolean isForSupplier; //主辦人員將異動圖面提供廠商
    boolean isMeeting; //工具箱會議
    boolean isMeetingRecord; //協議組織會議紀錄
    boolean isDanger; //危害因素告知單
    boolean isCheck; //工地勞安巡檢
    boolean isAdjust; //安衛聯繫調整巡視
    List<SupplierContract> supplierContractList; //廠商出工狀態清單
    List<SpecialConstraction> specialConstractionList; //營造業專業工程特定施工清單
    List<MaterialManage> materialManageList; //工地材料管理概況清單
    List<app.com.ChinChen.domain.Document> documentList; //收發文資料清單

   String endDate_beginDate;
   String dailyDate_beginDate;
   String endDate_dailyDate;
   int changeAmount;

}
