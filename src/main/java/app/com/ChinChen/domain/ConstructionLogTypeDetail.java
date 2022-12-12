package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ConstructionLogTypeDetail {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String constructionLogTypeId; //施工日誌工別與機具ID
    int todayCount; //每日人數
    int cumulativeCount; //累計人數
}
