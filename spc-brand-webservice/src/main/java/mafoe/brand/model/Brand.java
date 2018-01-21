package mafoe.brand.model;

import java.io.Serializable;

public class Brand implements Serializable {

    private String name;

    public Brand(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
