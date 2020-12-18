package com.company;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.nytimes.com/section/technology").get();
        Elements articles = doc.select("li.css-ye6x8s");
        for (Element article : articles) {
            System.out.println("---------------------------------------------");
            System.out.println("Title: " + article.select("h2.css-1j9dxys ").text());
            System.out.println("Support text: " + article.select("h2.css-1echdzn").text());
            System.out.println("Author: " + article.select("span.css-1n7hynb").text());
            System.out.println("Date: " + article.select("div.css-1lc2l26.span").text());
            System.out.println("---------------------------------------------");
        }
    }
}


