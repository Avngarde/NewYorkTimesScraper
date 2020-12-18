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
            System.out.println(articles.select("h2.css-1j9dxys.text"));
        }
    }
}


