package com.prouman.model;

/**
 * Created by jcs on 9/5/2016.
 */
public class CategoryModel {
    String id;
    String categeoryName;

    public CategoryModel(String id, String categeoryName) {
        this.id = id;
        this.categeoryName = categeoryName;
    }

    public CategoryModel() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategeoryName() {
        return categeoryName;
    }

    public void setCategeoryName(String categeoryName) {
        this.categeoryName = categeoryName;
    }
}
