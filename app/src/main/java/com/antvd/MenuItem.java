package com.antvd;

public class MenuItem {
    private String title;
    private int imageResource;

    public MenuItem(String title, int imageResource) {
        this.title = title;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResource() {
        return imageResource;
    }
}
