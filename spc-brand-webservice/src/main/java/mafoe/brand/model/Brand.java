package mafoe.brand.model;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Brand implements Serializable {

    @Id
    private String name;
    private boolean isPublic;

    public Brand(String name, boolean isPublic) {
        this.name = name;
        this.isPublic = isPublic;
    }

    public String getName() {
        return name;
    }

    public boolean isPublic() {
        return isPublic;
    }
}
