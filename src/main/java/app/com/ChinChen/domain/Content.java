package app.com.ChinChen.domain;

import lombok.Data;

@Data
public class Content {
    int _id;//章节号
    String section;//章节
    String content;//章节内容
    String audioPath;//MP3路径
    int duration;
    long size;
}
