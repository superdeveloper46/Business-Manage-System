package app.com.ChinChen.domain_temp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Menu2 {
    String _id;
    List<SubMneu> data;
}

@Getter
@Setter
class SubMneu {
    String _id;
    String parent_id;
    String name;
    String path;
    String iconClasses = "";
}


