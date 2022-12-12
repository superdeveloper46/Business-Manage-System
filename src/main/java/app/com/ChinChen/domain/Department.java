package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "department")
public class Department {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String depName; // 部門名稱
    int level; // 部門層級(公司層級為0)
    String depCode; // 部門名稱
    String depChineseCode; //合約代號
    String rootDepartmentId; // 上層部門ID
    private String rootDepartmentName = ""; // 上層部門ID名稱
    int sort;
}
