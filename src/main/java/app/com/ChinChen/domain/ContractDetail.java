package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class ContractDetail {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String pccesId;
    int price;
    String diagramNo;
    String spec;
    int quantity;
    int summary;
}
