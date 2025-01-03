//Joshua Taye Alemayehu

package com.bookstore.step8to11;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.servlet.http.HttpServlet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Setter;

@RequestMapping("/searchBooks")
public class SearchBooksServlet extends HttpServlet {
    private static final String query = "select * from Books where title like ?";

    @Setter
    private DBConnectionManager db;

    @GetMapping
    @ResponseBody
    public String searchBooks(@RequestParam("title") String title) {
        StringBuilder htmlResponse = new StringBuilder();
        db.openConnection();
        Connection connection = db.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            ArrayList<Book> books = new ArrayList<Book>();
            while (rs.next()) {
                books.add(new Book(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getFloat(4)));
            }

            htmlResponse.append("<html><head> </head><body>");
            htmlResponse.append("<h2>Showing search results for: '").append(title).append("'</h2>");
            htmlResponse.append("<table style='border: 2px solid black; border-spacing: 10px;'>");
            htmlResponse.append("<tr>");
            htmlResponse.append("<th>Book ID</th>");
            htmlResponse.append("<th>Description</th>");
            htmlResponse.append("<th>Status</th>");
            htmlResponse.append("<th>Due Date</th>");
            htmlResponse.append("</tr>");
            for (Book book : books) {
                htmlResponse.append("<tr>");
                htmlResponse.append("<td>").append(book.id).append("</td>");
                htmlResponse.append("<td>").append(book.title).append("</td>");
                htmlResponse.append("<td>").append(book.author).append("</td>");
                htmlResponse.append("<td>").append(book.price).append("</td>");
                htmlResponse.append("</tr>");
            }
            htmlResponse.append("</table>");
            htmlResponse.append("</body></html>");
        } catch (SQLException se) {
            se.printStackTrace();
            htmlResponse.append("<h1>").append(se.getMessage()).append("</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            htmlResponse.append("<h1>").append(e.getMessage()).append("</h1>");
        }

        htmlResponse.append("</body></html>");
        db.closeConnection();
        return htmlResponse.toString();
    }
}
