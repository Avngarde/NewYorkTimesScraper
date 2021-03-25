package com.company;

import java.sql.*;
import java.util.ArrayList;

public class NewYorkScraperSQLConnector {
    private Connection connection;

    public NewYorkScraperSQLConnector() throws SQLException{
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/newyorknews",
                "root",
                ""
        );
    }

    public void saveToDB(ArrayList<TimesNewYorkerArticle> articles) throws SQLException {
        Statement stmt = connection.createStatement();
        for (TimesNewYorkerArticle article : articles) {
            String sql = String.format("INSERT INTO news(title, support_text, author, date, url) VALUES ('%s', '%s', '%s', '%s', '%s')",
                    article.getTitle(),
                    article.getSupportText(),
                    article.getAuthor(),
                    article.getDate(),
                    article.getUrl());
            stmt.executeUpdate(sql);
        }
    }

    public void readFromDB() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM news";
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            System.out.println(rs.getString("title"));
            System.out.println(rs.getString("support_text"));
            System.out.println(rs.getString("author"));
            System.out.println(rs.getString("date"));
            System.out.println(rs.getString("url"));
            System.out.println("---------------------------------------------");
        }
    }
}
