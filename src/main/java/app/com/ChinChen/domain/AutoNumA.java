package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "AutoNumA")
public class AutoNumA {
    @Id
    String _id;
    String itemCode;
    String levelNo;
    String cName;
    String IsShow;
    String parent;
    String WinFormFlag;
    String AltUnit;
    String commonName;
    String Ext;
}
