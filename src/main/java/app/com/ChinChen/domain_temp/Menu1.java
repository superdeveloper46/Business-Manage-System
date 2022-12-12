package app.com.ChinChen.domain_temp;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Menu1 {
    String _id;
    String name;
    String path;  
    String iconClasses;
    List<Children> children;
    
}

@Getter
@Setter
class Children {
    String name;
    List<String> path;
    String iconClasses = "";
    List<Children> children = new ArrayList<>();
}
