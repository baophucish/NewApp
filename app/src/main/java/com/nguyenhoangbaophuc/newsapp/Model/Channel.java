package com.nguyenhoangbaophuc.newsapp.Model;

import java.util.ArrayList;

public class Channel {
    private String title, description, pubDate, link;
    private ArrayList<Item> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public ArrayList<Item> getList() {
        return list;
    }

    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    public Channel(String title, String description, String pubDate, String link, ArrayList<Item> list) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.link = link;
        this.list = list;
    }
}
