package app.com.ChinChen.domain;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@Document(collection = "workItem")
//PCCES物料編碼
public class WorkItem {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    @Indexed(unique = true)
    String itemCode; //工具編號
    String itemKind; //工具類別
    String description; //描述
    String unit; //單位
}
