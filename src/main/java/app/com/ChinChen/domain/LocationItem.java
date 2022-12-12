package app.com.ChinChen.domain;

import lombok.Data;

@Data
// @Document(collection = "locationItem")
public class LocationItem {
    // @Id
    // String _id =
    // LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    String locationName; // 地點名稱
    String areaName; // 行政區名稱
}
