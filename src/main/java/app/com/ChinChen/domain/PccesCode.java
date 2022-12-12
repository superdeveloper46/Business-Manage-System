package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "pccesCode") //PCCES物料編碼
public class PccesCode {
    @Id
    String _id = "";//編碼
    String itemKind;//編碼類型
    String description;//物料名稱
    String unit;//單位
}
