package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "jobModules")
public class JobModules {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String jobId;// 職稱Id
    private String jobName = "";
    String moduleId;// 功能模組Id(FunctionModule)
    private String moduleName = "";// 功能模組Id名稱
    private String[] rootFunctionName;// 父模組Id名稱
    boolean insert;// 新增權限
    boolean update;// 修改權限
    boolean delete;// 刪除權限
    boolean showInMenu; // 顯示於選單中
    int sort; // 排序
}
