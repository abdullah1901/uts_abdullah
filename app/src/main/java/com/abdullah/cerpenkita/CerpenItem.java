package com.abdullah.cerpenkita;

public class CerpenItem {
    private String title;
    private String content;

    public CerpenItem(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return title;
    }
}