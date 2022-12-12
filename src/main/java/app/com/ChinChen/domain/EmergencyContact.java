package app.com.ChinChen.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Getter
@Setter
@Document(collection = "emergencyContact")
public class EmergencyContact {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String contactName;// 聯絡人名稱
    String relationship;// 關係
    String contactPhone;// 連絡電話
}
