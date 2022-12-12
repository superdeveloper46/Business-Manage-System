package app.com.ChinChen.domain;

import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "formTask")
public class FormTask {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String formId; //表單ID
    String formTaskNo; //表單編號
    String emergencyTypeId; // 速別Id
    String creatorId; //建立者員工id
    Date beginTime; //起始時間
    Date endTime; //結案時間
    String taskStatusId; //表單狀態
    List<FormTaskSite> taskSiteList = new ArrayList<>();//簽核站點紀錄
    FormTaskContent formTaskContent; //表單欄位內容
    String comment; //起單者說明欄

    EmergencyType emergencyType;
    Department department;
    TaskStatus taskStatus;
    Employee employee;
    WorkType workType;
    Supplier supplier;
    Form form;
}

