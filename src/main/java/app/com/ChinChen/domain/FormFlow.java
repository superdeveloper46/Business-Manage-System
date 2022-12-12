package app.com.ChinChen.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "formFlow")
public class FormFlow {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String formFlowNo; //表單流程編號
    String formId; //表單Id
    String flowType; //流程類型
    String targetId; //依據FlowType判斷關聯至哪個collection
    List<FormSectionRight> sectionRightList = new ArrayList<>(); // 表單區塊權限設定
    int sort; //表單流程順序
}
