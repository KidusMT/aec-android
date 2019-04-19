package com.aait.aec.data.db.model;

public class Category {

    public int thumb;
    public String title;
    public String subTitle;

    public Category(String title, String subTitle, int thumb) {
        this.thumb = thumb;
        this.title = title;
        this.subTitle = subTitle;
    }
}
