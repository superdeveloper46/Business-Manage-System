package app.com.ChinChen.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@Getter
@Setter
@Document(collection = "license")
public class License {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String licenseName;// 證照名稱
    String licenseTypeId;// 證照類別
    boolean approveUse;// 同意公司使用
    boolean needTrain;// 需要回訓?
    Date nextTrainDate;//下次回訓日期
}
