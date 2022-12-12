package app.com.ChinChen.domain;

import lombok.Data;

@Data
public class Permission {
    String jobId;
    String jobName;
    String moduleId;
    String moduleName;
    boolean insert;
    boolean update;
    boolean delete;
}
