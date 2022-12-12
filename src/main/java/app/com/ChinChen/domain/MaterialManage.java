package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class MaterialManage {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String itemName; //項目名稱
    String unit; //單位
    String quantity; //契約數量
    String todayUse; //本日使用數量
    String cumulativeuse; //累計使用數量
    String remark; //備註
}
