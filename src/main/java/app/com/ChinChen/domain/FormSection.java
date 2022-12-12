package app.com.ChinChen.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "formSection")
public class FormSection {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String formSectionNo; //表單區塊編號
    String formSectionName;//表單區塊名稱
    String formId; //表單Id
    int sort; //區塊順序
}
