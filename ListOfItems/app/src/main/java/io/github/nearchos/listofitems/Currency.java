package io.github.nearchos.listofitems;

import java.io.Serializable;

public class Currency implements Serializable {

    private String code;
    private String name;
    private boolean favorite;

    public Currency(String code, String name, boolean favorite) {
        this.code = code;
        this.name = name;
        this.favorite = favorite;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public String toString() {
        return name;
    }
}