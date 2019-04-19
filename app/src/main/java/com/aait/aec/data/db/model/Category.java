package com.aait.aec.data.db.model;

public class Category {

    private int thumb;
    private String title;
    private String subTitle;

    public Category(String title, String subTitle, int thumb) {
        this.thumb = thumb;
        this.title = title;
        this.subTitle = subTitle;
    }

    public int getThumb() {
        return thumb;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

}
