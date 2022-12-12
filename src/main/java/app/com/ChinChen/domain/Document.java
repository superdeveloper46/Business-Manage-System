package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Document {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    boolean docType; //收文或發文
    String docTitle; //主旨
    String docPerson; //收/發文者
    String docNumber; //文號
    String docDescription; //辦理方式
}
