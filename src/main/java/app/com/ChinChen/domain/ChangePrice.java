package app.com.ChinChen.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.Id;

@Data
public class ChangePrice {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    Date changeDate; //變更日期
    String changeReason; //變更原因
    int changePrice; //金額
}
