package com.company;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    public static String createDateString(String[] splited_url){
        return splited_url[3] + "." + splited_url[2] + "." + splited_url[1]; // DD/MM/YYYY format
    }

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.nytimes.com/section/technology").get();
        Elements articles = doc.select("div.css-1cp3ece");
        for (Element article : articles) {
            String date = createDateString(article.select("div.css-1l4spti").select("a").attr("href").split("/"));
            System.out.println("Title: " + article.select("h2.css-1j9dxys ").text());
            System.out.println("Support text: " + article.select("p.css-1echdzn").text());
            System.out.println("Author: " + article.select("span.css-1n7hynb").text());
            System.out.println("Date: " + date);
            System.out.println("URL: " + article.select("div.css-1l4spti").select("a").attr("href"));;
            System.out.println("---------------------------------------------");
        }
    }
}


