package com.example.tony.news.bean;

/*
 * Created by Tony on 2016/11/23.
 */

public class BannerBean {
    private String[] img_url;
    private String[] title;
    private String[] toUrl;

    public String[] getImg_url() {
        return img_url;
    }

    public void setImg_url(String[] img_url) {
        this.img_url = img_url;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getToUrl() {
        return toUrl;
    }

    public void setToUrl(String[] toUrl) {
        this.toUrl = toUrl;
    }

    public BannerBean() {

    }
}
