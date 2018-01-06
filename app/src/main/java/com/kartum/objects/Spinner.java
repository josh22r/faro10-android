package com.kartum.objects;

public class Spinner {
    public Spinner(String ID, String title) {
        this.ID = ID;
        this.title = title;
    }

    public Spinner(String ID, String title, String desc) {
        this.ID = ID;
        this.title = title;
        this.desc = desc;
    }

    public Spinner(String title) {
        this.title = title;
    }

    public String ID;
    public String title;
    public long duration;
    public String desc;

    public boolean isSelected;

    public Spinner setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }
}
