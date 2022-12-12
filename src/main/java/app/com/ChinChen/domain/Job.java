package app.com.ChinChen.domain;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "job")
public class Job {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String jobName;//職稱名稱
    String jobCode;//職稱代號
    int rank;//職稱層級
}
