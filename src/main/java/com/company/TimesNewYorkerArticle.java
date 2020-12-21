package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimesNewYorkerArticle {
    private String title;
    private String support_text;
    private String author;
    private Date date;
    private String url;

    TimesNewYorkerArticle(String title, String support_text, String author, String date, String url) throws java.text.ParseException {
        this.title = title;
        this.support_text = support_text;
        this.author = author;
        this.date = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        this.url = url;
    }

    public Date getDate() {
        return date;
    }

    public String getSupportText() {
        return support_text;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
