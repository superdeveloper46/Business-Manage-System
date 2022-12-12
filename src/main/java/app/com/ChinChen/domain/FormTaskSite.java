package app.com.ChinChen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormTaskSite {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String formFlowId; //表單流程Id
    Date startTime; // 起始時間
    Date finishTime; //完成時間
    String siteResultId; //站點簽核結果Id
    boolean isCurrent; //是否為目前站點
    String resultComment; //簽核結果說明
    String signer; //簽核者emp id
}