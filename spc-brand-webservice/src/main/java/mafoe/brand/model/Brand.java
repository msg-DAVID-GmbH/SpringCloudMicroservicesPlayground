package mafoe.brand.model;

import java.io.Serializable;

public class Brand implements Serializable {

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
