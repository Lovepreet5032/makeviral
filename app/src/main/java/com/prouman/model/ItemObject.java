package com.prouman.model;

public class ItemObject {
    private int name;
    private int photo;

    public ItemObject(int name, int photo) {
        this.name = name;
        this.photo = photo;
    }





    public int getPhoto() {
        return this.photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
}
