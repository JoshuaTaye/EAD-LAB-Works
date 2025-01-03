//Joshua Taye Alemayehu
package com.bookstore.step1to7;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/displayBooks")
public class DisplayBooksServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver class for modern MySQL versions
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            return;
        }
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstoredb", "root", "0521")) {
            String query = "SELECT * FROM Books";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            PrintWriter out = response.getWriter();
            out.println("<html><body><table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>" + rs.getString("title") +
                        "</td><td>" + rs.getString("author") + "</td><td>" + rs.getFloat("price") + "</td></tr>");
            }
            out.println("</table></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
