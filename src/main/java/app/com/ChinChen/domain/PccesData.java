package app.com.ChinChen.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PccesData{
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String pccesId; //編碼
    String diagramNo; //編碼
    String spec; //規格
    double quantity; //數量
    String itemKind; //編碼類型
    String description; //物料名稱
    String unit; //單位
}
