package mafoe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandView {

    private String name;

    public String getName() {
        return name;
    }
}
