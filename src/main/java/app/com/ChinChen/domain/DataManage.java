package app.com.ChinChen.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "datamanage")
public class DataManage {
    @Id
    String _id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    Manufacture_information manufacture_information;
    Manufacture_type manufacture_type;
    Contact_person contact_person;
    Edit edit;
}

@Getter
@Setter
class Manufacture_information{
    String businessNo;
    String supplierName;
    List<String> location = new ArrayList<>();
}

@Getter
@Setter
class Manufacture_type{
    List<String> supplierDetailItem = new ArrayList<>();
    List<String> workTypes = new ArrayList<>();
}

@Getter
@Setter
class Contact_person{
    String contactName;
    String contactPhone;
}

@Getter
@Setter
class Edit{
    Boolean status;
    String badge;
    String value;
}

