package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "material")
public class Material {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String projectId; //專案ID
    String materialItem; //項次名稱
    String materialName; //材料/設備名稱
    String materialQuantity; //契約數量
    String materialExpectDate; //預計送審日期
    String materialActualDate; //實際送審日期
    String materialCheckDate; //廠驗日期
    boolean isGetSampleTest; //是否取樣試驗
    boolean isHaveSample; //是否有樣品
    String supplierFile; //協力廠商資料
    String coverFile; //型錄
    String testReport; //相關試驗報告
    String materialRemark; //備註
    String materialReviewDate; //審查日
    boolean isPass; //審查結果
    String materialOthers; //其他

}
