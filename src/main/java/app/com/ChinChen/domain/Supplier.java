package app.com.ChinChen.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Data
@Document(collection = "supplier")
public class Supplier {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String supplierName; // 廠商名稱
    String businessNo; // 統編
    String codeName; // 合約代稱(一個字)
    String contactName; // 聯絡人姓名
    String contactPhone; // 聯絡人姓名
    boolean badRecord; // 不良紀錄
    String email; // Email
    String supplierTypeId; // 合作模式
    private String supplierTypeName; // 合作模式名稱
    int workersNum;// 工班人數
    LocationItem locationItem = new LocationItem();// 縣市、地區
    String address; // 地址
    List<String> workTypeIdList = new ArrayList<String>();// 負責工項
    List<String> workTypeNameList = new ArrayList<String>();// 負責工項名稱
    List<LocationArea> locationAreaList = new ArrayList<LocationArea>();// 業務範圍縣市 地區
    String employeeId; // 負責採發人員
    private String employeeName; // 負責採發人員名稱
    String remark; // 備註
    SupplierBankData supplierBankData = new SupplierBankData(); // 銀行資料
    private List<String> bankName; // 銀行名稱
    Date disableDay; // 停用日期
    String disableRemark; // 停用備註
    List<SupplierCare> supplierCareList = new ArrayList<SupplierCare>();// 廠商關懷
    boolean enable; // 是否啟用
    private Date contractDate; //最近配合日
    private List<String> purchaseFormId;
    private int cooperation = 0;//已合作過
    private int implement = 0;//正在執行
}

@Getter
@Setter
class LocationArea {
    String locationName; // 縣市
    List<String> areaNameList = new ArrayList<String>(); // 地區
}
