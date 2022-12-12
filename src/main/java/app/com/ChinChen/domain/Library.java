package app.com.ChinChen.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "library")
public class Library {
    @Id
    String _id;//userId + bookId + type
    int type;//0:saved, 1:downloaded
    boolean visible;
}
