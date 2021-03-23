package com.company;

import java.sql.*;
import java.util.ArrayList;

public class NewYorkScraperSQLConnector {
    private Connection connection;

    public NewYorkScraperSQLConnector() throws SQLException{
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/newyorknews",
                "root",
                ""
        );
        connection = conn;
    }

    public void saveToDB(ArrayList<TimesNewYorkerArticle> articles) throws SQLException {
        Statement stmt = connection.createStatement();
        for (TimesNewYorkerArticle article : articles) {
            String sql = String.format("INSERT INTO news(title, support_text, author, date, url) VALUES (%s, %s, %s, %s, %s)",
                    article.getTitle(),
                    article.getSupportText(),
                    article.getAuthor(),
                    article.getDate(),
                    article.getUrl());
            stmt.executeUpdate(sql);
        }
    }
}
