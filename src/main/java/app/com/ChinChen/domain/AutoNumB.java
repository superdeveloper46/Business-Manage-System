package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "AutoNumB")
public class AutoNumB {
    @Id
    String _id;
    String ChapCode;
    String Code;
    String CodeSection;
    int MinRow;
    int MaxRow;
    int SelfRow;
    String Content;
    String resType;
    String SelfDefine;
    String IsCustom;
    String Version;
    long RowID;
}
