package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "form")
public class Form {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String formName; //表單名稱    
    String formCode; //表單代號
    String projectStepId; //專案階段Id
    boolean enable; //是否啟用
    List<FormSection> formSectionList = new ArrayList<>(); //表單區塊列表
    List<FormFlow> formFlowList = new ArrayList<>(); //表單流程列表
    ProjectStep projectStep;
}
