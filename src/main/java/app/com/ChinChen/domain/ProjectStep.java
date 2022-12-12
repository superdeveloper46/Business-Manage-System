package app.com.ChinChen.domain;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "projectStep")
//專案階段
public class ProjectStep {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    int stepNo;//階段號碼(順序)
    String stepName; //階段名稱
    String stepClass; //階段icon css class
}
