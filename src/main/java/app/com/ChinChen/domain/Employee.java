package app.com.ChinChen.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@Document(collection = "employee")
public class Employee {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String empName;// 員工姓名
    boolean isBoss = false;// 是否為主管
    String empGUID;// 員工key值
    String account;// 帳號
    String password;// 密碼
    String email;// 信箱
    Date createDate;// 建立日期(新增時自動寫入)
    boolean isLock = false;// 是否鎖定(編輯畫面)
    Date lockDate = null;// 鎖定日期(被鎖定時寫入日期時間)
    Date expireDate = null;// 到期日期(編輯畫面)
    String departmentId;// 部門ID
    private String departmentName;// 部門ID
    Department department;
    List<String> jobList = new ArrayList<>();// 職務List
    List<String> jobListName = new ArrayList<>();// 職務List
    boolean gender; // 性別(0-女 1-男)
    String personalNo; // 身分證字號
    Date birthday; // 生日
    Date onBoardDate; // 到職日
    String phone;// 連絡電話
    String address;// 聯絡地址
    String expertise;// 專長
    String interest;// 興趣
    List<EmergencyContact> contactList = new ArrayList<>(); //聯絡人清單(直接放物件進來)
    List<License> licenseList = new ArrayList<>(); //證照清單(直接放物件進來)
    String employeePic;// 員工照片路徑
    String employeeSeal;// 員工印章圖片路徑
}
