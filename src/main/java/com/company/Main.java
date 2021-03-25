package com.company;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;


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

    public static void readArticlesFromDB() {
        long begin_time = System.currentTimeMillis();
        long end_time = System.currentTimeMillis();

        try {
            NewYorkScraperSQLConnector connector = new NewYorkScraperSQLConnector();
            connector.readFromDB();
        } catch (SQLException err) {
            err.printStackTrace();
        }

        long exec_time_nanoseconds = (end_time - begin_time);
        System.out.println("Data parsed in: "+exec_time_nanoseconds+" nanoseconds");
    }

    public static void saveArticlesToDB(ArrayList<TimesNewYorkerArticle> articles) throws SQLException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        long begin_time = System.currentTimeMillis();
        try {
            NewYorkScraperSQLConnector connector = new NewYorkScraperSQLConnector();
            connector.saveToDB(articles);
        } catch (SQLException err) {
            throw err;
        }
        long end_time = System.currentTimeMillis();
        long exec_time_nanoseconds = (end_time - begin_time);
        System.out.println("Data added in: "+exec_time_nanoseconds+" nanoseconds");
    }

    public static void main(String[] args) throws IOException, ParseException, SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Odczytaj dane z bazy");
        System.out.println("2. Scrapuj dane do bazy");
        System.out.print("Twój wybór:");
        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            readArticlesFromDB();
            System.out.println("Poprawnie odczytano artykuły z bazy danych!");
        }

        else if (choice.equals("2")) {
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
                    if (!isArticleInArticles(articles, new_article)) {
                        articles.add(new_article);
                    }
                } catch (ParseException e) {
                    continue;
                }
            }

            saveArticlesToDB(articles);
            System.out.println("Poprawnie dodano pobrane artykuły do bazy danych!");
        }

        else {
            System.out.println(choice);
            System.out.println("Podano niepoprawny wybór");
        }
    }
}