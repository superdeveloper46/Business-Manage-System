package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Document(collection = "functionModule")
//系統功能模組
public class FunctionModule {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String moduleName; //功能模組名稱
    String controllerName; //控制器名稱
    List<String> parameter; //參數
    String rootFunctionId; //父模組ID
    private String rootFunctionName = ""; //父模組ID名稱
    int level; //功能層級
    String menuClass; //選單Class
    int sort; //排序
}
