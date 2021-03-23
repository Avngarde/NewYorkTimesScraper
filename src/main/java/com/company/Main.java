package com.company;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class Main {
    public static String createDateStringFromUrl(String[] splited_url){
        return splited_url[3] + "." + splited_url[2] + "." + splited_url[1]; // DD/MM/YYYY format
    }

    public static boolean isArticleInArticles(ArrayList<TimesNewYorkerArticle> articles, TimesNewYorkerArticle searched_article){
        for (TimesNewYorkerArticle article : articles) {
            if (article.getUrl() == searched_article.getUrl()) {
                return true;
            }
        }

        return false;
    }

    public static void saveArticlesToDB(ArrayList<TimesNewYorkerArticle> articles) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        try {
            NewYorkScraperSQLConnector connector = new NewYorkScraperSQLConnector();
            connector.saveToDB(articles);
        } catch (SQLException err) {
            throw err;
        }
    }

    public static void main(String[] args) throws IOException, ParseException, SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        ArrayList<TimesNewYorkerArticle> articles = new ArrayList<TimesNewYorkerArticle>();
        Document doc = Jsoup.connect("https://www.nytimes.com/section/technology").get();
        Elements scrapped_articles = doc.select("div.css-1cp3ece");
        for (Element article : scrapped_articles) {
            try {
                String title = article.select("h2.css-1j9dxys ").text();
                String support_text = article.select("p.css-1echdzn").text();
                String author = article.select("span.css-1n7hynb").text();
                String date = createDateStringFromUrl(article.select("div.css-1l4spti").select("a").attr("href").split("/"));
                String url = article.select("div.css-1l4spti").select("a").attr("href");
                TimesNewYorkerArticle new_article = new TimesNewYorkerArticle(
                        title = title,
                        support_text = support_text,
                        author = author,
                        date = date,
                        url = url
                );
                if (isArticleInArticles(articles, new_article) == false) {
                    articles.add(new_article);
                }
            } catch (ParseException e) {
                continue;
            }
        }

        saveArticlesToDB(articles);
        System.out.println("Articles scraped properly!");
    }
}